package com.sportradar.scoreboard;

import com.sportradar.scoreboard.match.exception.MatchAlreadyStartedException;
import com.sportradar.scoreboard.match.exception.MatchNotFoundException;
import com.sportradar.scoreboard.model.Match;
import com.sportradar.scoreboard.model.MatchId;
import com.sportradar.scoreboard.model.Score;
import com.sportradar.scoreboard.model.Team;
import com.sportradar.scoreboard.repository.CrudRepository;
import com.sportradar.scoreboard.match.MatchFactory;
import com.sportradar.scoreboard.repository.exception.EntityExistsException;
import com.sportradar.scoreboard.repository.exception.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Clock;

import static java.util.Comparator.comparing;
import static java.util.stream.StreamSupport.stream;

public class Scoreboard {

    private static final Logger LOG = LogManager.getLogger();

    private final MatchFactory matchFactory;
    private final CrudRepository<MatchId, Match> matchRepository;

    public Scoreboard(Clock clock, CrudRepository<MatchId, Match> matchRepository) {
        this.matchFactory = new MatchFactory(clock);
        this.matchRepository = matchRepository;
    }

    public Match startMatch(Team home, Team away) throws MatchAlreadyStartedException{
        LOG.info(() -> "Starting match between " + home + " and " + away + ".");
        Match match = matchFactory.create(home, away);
        try {
            matchRepository.insert(match.getMatchId(), match);
        } catch (EntityExistsException e) {
            throw new MatchAlreadyStartedException(match.getMatchId(), e);
        }
        return match;
    }

    public void updateScore(MatchId matchId, Score score) throws MatchNotFoundException {
        LOG.info(() -> "Updating score: " + score + " for match with id: " + matchId + ".");
        Match match = matchRepository.findById(matchId);
        if (match == null) {
            throw new MatchNotFoundException(matchId);
        }
        Match updated = match.updateScore(score);
        try {
            matchRepository.update(updated.getMatchId(), updated);
        } catch (EntityNotFoundException e) {
            throw new MatchNotFoundException(matchId, e);
        }
    }

    public void finishMatch(MatchId matchId) throws MatchNotFoundException {
        LOG.info(() -> "Finishing match with id: " + matchId + ".");
        try {
            matchRepository.delete(matchId);
        } catch (EntityNotFoundException e) {
            throw new MatchNotFoundException(matchId, e);
        }
    }

    public Iterable<Match> getSummary() {
        LOG.debug("Getting summary.");
        return () -> stream(matchRepository.findAll().spliterator(), false)
                .sorted(comparing(Match::getTotalScore).thenComparing(Match::startTime).reversed())
                .iterator();
    }
}
