package com.sportradar.scoreboard;

import com.sportradar.scoreboard.model.Match;
import com.sportradar.scoreboard.model.Score;
import com.sportradar.scoreboard.model.Team;
import com.sportradar.scoreboard.repository.MatchRepository;
import com.sportradar.scoreboard.testutils.FixedMatchIdGenerator;
import com.sportradar.scoreboard.match.MatchFactory;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static java.time.Clock.fixed;
import static java.time.Instant.ofEpochMilli;
import static java.time.ZoneOffset.UTC;
import static java.util.Arrays.asList;
import static java.util.UUID.fromString;
import static java.util.stream.StreamSupport.stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ScoreboardTest {

    private final MatchFactory matchFactory = new MatchFactory(
            fixed(FIXED_START_TIME, UTC),
            new FixedMatchIdGenerator(FIXED_MATCH_ID)
    );
    private final MatchRepository mockRepository = mock();

    private static final UUID FIXED_MATCH_ID = UUID.fromString("91b874d5-d331-4a64-a127-932c323241bb");
    private static final Instant FIXED_START_TIME = ofEpochMilli(1);

    private final Scoreboard scoreboard = new Scoreboard(matchFactory, mockRepository);

    @Test
    public void shouldStartNewMatch() {
        // when
        Match actual = scoreboard.startMatch(ARGENTINA, AUSTRALIA);

        // then
        Match expected = new Match(FIXED_MATCH_ID, ARGENTINA, AUSTRALIA, new Score(0, 0), FIXED_START_TIME);

        assertEquals(expected, actual);
        verify(mockRepository).save(expected);
    }

    @Test
    public void shouldUpdateScoreScore() {
        // given
        Score oldScore = new Score(1, 2);
        Score newScore = new Score(2, 3);
        Match match = new Match(FIXED_MATCH_ID, ARGENTINA, AUSTRALIA, oldScore, FIXED_START_TIME);
        when(mockRepository.findById(FIXED_MATCH_ID)).thenReturn(match);

        // when
        scoreboard.updateScore(FIXED_MATCH_ID, newScore);

        // then
        Match expected = new Match(FIXED_MATCH_ID, ARGENTINA, AUSTRALIA, newScore, FIXED_START_TIME);
        verify(mockRepository).findById(FIXED_MATCH_ID);
        verify(mockRepository).save(expected);
        verifyNoMoreInteractions(mockRepository);
    }

    @Test
    public void shouldFinishMatch() {
        // when
        scoreboard.finishMatch(FIXED_MATCH_ID);

        // then
        verify(mockRepository).delete(FIXED_MATCH_ID);
        verifyNoMoreInteractions(mockRepository);
    }

    @Test
    public void shouldGetSummary() {
        // given
        Match mexicoCanada = new Match(FIXED_MATCH_ID, MEXICO, CANADA, new Score(0, 5), ofEpochMilli(1));
        Match spainBrazil = new Match(FIXED_MATCH_ID, SPAIN, BRAZIL, new Score(10, 2), ofEpochMilli(2));
        Match germanyFrance = new Match(FIXED_MATCH_ID, GERMANY, FRANCE, new Score(2, 2), ofEpochMilli(3));
        Match uruguayItaly = new Match(FIXED_MATCH_ID, URUGUAY, ITALY, new Score(6, 6), ofEpochMilli(4));
        Match argentinaAustralia = new Match(FIXED_MATCH_ID, ARGENTINA, AUSTRALIA, new Score(3, 1), ofEpochMilli(5));

        when(mockRepository.findAll()).thenReturn(asList(mexicoCanada, spainBrazil, germanyFrance, uruguayItaly, argentinaAustralia));

        // when
        Iterable<Match> actual = scoreboard.getSummary();

        // then
        List<Match> expected = asList(uruguayItaly, spainBrazil, mexicoCanada, argentinaAustralia, germanyFrance);
        assertEquals(expected, stream(actual.spliterator(), false).toList());
    }


    private static final Team ARGENTINA = new Team(fromString("307c86a5-1a5d-4685-a9f7-17ee3733aad1"), "Argentina");
    private static final Team AUSTRALIA = new Team(fromString("51736f6a-7a23-4a3b-b65d-d1f0232fbc84"), "Australia");
    private static final Team BRAZIL = new Team(fromString("e3e7fbde-9e9e-4489-ba58-74a444039edc"), "Brazil");
    private static final Team CANADA = new Team(fromString("07dcfab4-8ff5-4fa5-9491-0866bb3fee85"), "Canada");
    private static final Team GERMANY = new Team(fromString("74a79784-1a05-467e-a253-dc950921a5eb"), "Germany");
    private static final Team FRANCE = new Team(fromString("da5f5348-104a-403b-a60b-f26c5a9f59d3"), "France");
    private static final Team ITALY = new Team(fromString("6eb28b79-e538-42d5-a798-21081946c634"), "Italy");
    private static final Team MEXICO = new Team(fromString("86520b76-4495-406e-9b54-2608b070808e"), "Mexico");
    private static final Team SPAIN = new Team(fromString("bdd65d3b-cf3b-45c0-a3ab-10f58e84eaea"), "Spain");
    private static final Team URUGUAY = new Team(fromString("95e48740-0f91-4a32-b029-6939cd68f540"), "Uruguay");

}
