package com.sportradar.scoreboard;

import com.sportradar.scoreboard.model.Match;
import com.sportradar.scoreboard.model.Score;
import com.sportradar.scoreboard.model.Team;
import com.sportradar.scoreboard.repository.MatchRepository;
import com.sportradar.scoreboard.match.MatchFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

import static java.util.Comparator.comparing;
import static java.util.stream.StreamSupport.stream;

public class Scoreboard {

    private static final Logger LOG = LogManager.getLogger();

    private final MatchFactory matchFactory;
    private final MatchRepository repository;

    public Scoreboard(MatchFactory matchFactory, MatchRepository repository) {
        this.matchFactory = matchFactory;
        this.repository = repository;
    }

    public Match startMatch(Team home, Team away) {
        LOG.info(() -> "Starting match between " + home + " and " + away + ".");
        Match match = matchFactory.create(home, away);
        repository.save(match);
        return match;
    }

    public void updateScore(UUID matchId, Score score) {
        LOG.info(() -> "Updating score: " + score + " for match with id: " + matchId + ".");
        Match match = repository.findById(matchId);
        Match updated = match.updateScore(score);
        repository.save(updated);
    }

    public void finishMatch(UUID matchId) {
        LOG.info(() -> "Finishing match with id: " + matchId + ".");
        repository.delete(matchId);
    }

    public Iterable<Match> getSummary() {
        LOG.debug("Getting summary.");
        return () -> stream(repository.findAll().spliterator(), false)
                .sorted(comparing(Match::getTotalScore).thenComparing(Match::startTime).reversed())
                .iterator();
    }
}
