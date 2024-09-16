package com.sportradar.scoreboard;

import com.sportradar.scoreboard.match.exception.MatchAlreadyStartedException;
import com.sportradar.scoreboard.match.exception.MatchNotFoundException;
import com.sportradar.scoreboard.model.Match;
import com.sportradar.scoreboard.model.MatchId;
import com.sportradar.scoreboard.model.Score;
import com.sportradar.scoreboard.model.Team;
import com.sportradar.scoreboard.repository.CrudRepository;
import com.sportradar.scoreboard.repository.InMemoryCrudRepository;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

import static java.time.Clock.fixed;
import static java.time.Instant.ofEpochMilli;
import static java.time.ZoneOffset.UTC;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.List.of;
import static java.util.stream.StreamSupport.stream;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreboardTest {

    private final Clock fixedClock = fixed(FIXED_START_TIME, UTC);
    private final CrudRepository<MatchId, Match> repository = new InMemoryCrudRepository<>();

    private static final Instant FIXED_START_TIME = ofEpochMilli(1);

    private final Scoreboard scoreboard = new Scoreboard(fixedClock, repository);

    @Test
    public void shouldStartNewMatch() {
        // given
        assertRepositoryIsEmpty();

        // when
        Match actual = scoreboard.startMatch(ARGENTINA, AUSTRALIA);

        // then
        Match expected = new Match(ARGENTINA, AUSTRALIA, new Score(0, 0), FIXED_START_TIME);
        assertEquals(expected, actual);
        assertEquals(of(expected), iterableAsList(repository.findAll()));
    }

    @Test
    public void shouldUpdateScore() {
        // given
        Score oldScore = new Score(1, 2);
        Score newScore = new Score(2, 3);
        Match match = new Match(ARGENTINA, AUSTRALIA, oldScore, FIXED_START_TIME);
        assertDoesNotThrow(() -> repository.insert(match.getMatchId(), match));

        // when
        assertDoesNotThrow(() -> scoreboard.updateScore(match.getMatchId(), newScore));

        // then
        Match expected = new Match(ARGENTINA, AUSTRALIA, newScore, FIXED_START_TIME);
        assertEquals(of(expected), iterableAsList(repository.findAll()));
    }

    @Test
    public void shouldFinishMatch() {
        // given
        Match match = new Match(ARGENTINA, AUSTRALIA, new Score(0, 0), FIXED_START_TIME);
        assertDoesNotThrow(() -> repository.insert(match.getMatchId(), match));

        // when
        assertDoesNotThrow(() -> scoreboard.finishMatch(match.getMatchId()));

        // then
        assertRepositoryIsEmpty();
    }

    @Test
    public void shouldGetSummary() {
        // given
        Match mexicoCanada = new Match(MEXICO, CANADA, new Score(0, 5), ofEpochMilli(1));
        Match spainBrazil = new Match(SPAIN, BRAZIL, new Score(10, 2), ofEpochMilli(2));
        Match germanyFrance = new Match(GERMANY, FRANCE, new Score(2, 2), ofEpochMilli(3));
        Match uruguayItaly = new Match(URUGUAY, ITALY, new Score(6, 6), ofEpochMilli(4));
        Match argentinaAustralia = new Match(ARGENTINA, AUSTRALIA, new Score(3, 1), ofEpochMilli(5));

        assertDoesNotThrow(() -> {
                    repository.insert(mexicoCanada.getMatchId(), mexicoCanada);
                    repository.insert(spainBrazil.getMatchId(), spainBrazil);
                    repository.insert(germanyFrance.getMatchId(), germanyFrance);
                    repository.insert(uruguayItaly.getMatchId(), uruguayItaly);
                    repository.insert(argentinaAustralia.getMatchId(), argentinaAustralia);
                });

        // when
        Iterable<Match> actual = scoreboard.getSummary();

        // then
        List<Match> expected = asList(uruguayItaly, spainBrazil, mexicoCanada, argentinaAustralia, germanyFrance);
        assertEquals(expected, iterableAsList(actual));
    }

    @Test
    public void shouldReturnEmptySummaryWhenNoMatches() {
        // when
        Iterable<Match> actual = scoreboard.getSummary();

        // then
        assertEquals(emptyList(), iterableAsList(actual));
    }

    @Test
    public void shouldFailWhenStartingAlreadyStartedMatch() {
        // given
        Match match = new Match(ARGENTINA, AUSTRALIA, new Score(0, 0), ofEpochMilli(1));
        assertDoesNotThrow(() -> repository.insert(match.getMatchId(), match));

        // when
        MatchAlreadyStartedException thrown = assertThrows(MatchAlreadyStartedException.class, () -> scoreboard.startMatch(ARGENTINA, AUSTRALIA));

        // then
        MatchAlreadyStartedException expected = new MatchAlreadyStartedException(match.getMatchId());
        assertEquals(expected.getMessage(), thrown.getMessage());
    }

    @Test
    public void shouldFailWhenUpdatingScoreForNonExistingMatch() {
        // given
        MatchId matchId = new MatchId(ARGENTINA, AUSTRALIA);
        Score newSCore = new Score(1,0);

        // when
        MatchNotFoundException thrown = assertThrows(MatchNotFoundException.class, () -> scoreboard.updateScore(matchId, newSCore));

        // then
        MatchNotFoundException expected = new MatchNotFoundException(matchId);
        assertEquals(expected.getMessage(), thrown.getMessage());
    }

    @Test
    public void shouldFailWhenFinishingMatchForNonExistingMatch() {
        // given
        MatchId matchId = new MatchId(ARGENTINA, AUSTRALIA);

        // when
        MatchNotFoundException thrown = assertThrows(MatchNotFoundException.class, () -> scoreboard.finishMatch(matchId));

        // then
        MatchNotFoundException expected = new MatchNotFoundException(matchId);
        assertEquals(expected.getMessage(), thrown.getMessage());
    }

    private void assertRepositoryIsEmpty() {
        assertEquals(emptyList(), iterableAsList(repository.findAll()));
    }

    private <T> List<T> iterableAsList(Iterable<T> iterable) {
        return stream(iterable.spliterator(), false).toList();
    }

    private static final Team ARGENTINA = new Team("Argentina");
    private static final Team AUSTRALIA = new Team("Australia");
    private static final Team BRAZIL = new Team("Brazil");
    private static final Team CANADA = new Team("Canada");
    private static final Team GERMANY = new Team("Germany");
    private static final Team FRANCE = new Team("France");
    private static final Team ITALY = new Team("Italy");
    private static final Team MEXICO = new Team("Mexico");
    private static final Team SPAIN = new Team("Spain");
    private static final Team URUGUAY = new Team("Uruguay");
}
