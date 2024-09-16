# Live Football World Cup Score Board

## Introduction
The library allows to show all the ongoing matches and their scores (take a look at 
[Scoreboard](src/main/java/com/sportradar/scoreboard/Scoreboard.java) class).

Functionalities:
- Start a new match.
- Update the score.
- End the match.
- Getting summary of current matches (sorted by total score and most recent match if scores are equal).

## Usage
The library provides a default factory [ScoreboardFactory](src/main/java/com/sportradar/scoreboard/ScoreboardFactory.java)
to initialise the scoreboard with in-memory db.

## Build
The project uses [Apache Maven](https://maven.apache.org/) as a build automation tool.

Command for build:
`mvn clean install`

### Requirements
- Java 21
- Apache Maven

## TODOs
- add more tests about thread-safe
- introduce match & team ids
- add javadoc

## Comments
- First of all, I had a lot of fun with implementing the solution 😊.
- Thank you for the example output for the summary. It made it easier to implement acceptance tests with it.
- Both (Team & Match) entities could have IDs in my opinion.
  - If the scoreboard is used for more events than WorldCup and also keeps the historical matches, it could be 
  beneficial to have something more than just team name as a team ID. Because maybe there could be more than
  one team with the same name. I started the implementation with UUID as the Team and Match ID, but then decided to remove 
  it for simplicity. The requirements for business IDs (for team: name + something more, for match: home & away 
  team and maybe time?) should be discussed with the product owner/BA. Anyway it's good to also have a technical ID 
  (like UUID). I can explain why it's good on request.
- I have provided [CrudRepository](src/main/java/com/sportradar/scoreboard/repository/CrudRepository.java) interface with 
in-memory implementation. But there might be other (SQL/NoSQL/etc.) implementations. I started testing with mocking the repository,
but as soon as I implemented [InMemoryCrudRepository](src/main/java/com/sportradar/scoreboard/repository/InMemoryCrudRepository.java)
I used it in Scoreboard unit tests.
- The repository is assumed to be concurrently safe.
- There were no requirements regarding data validation and exceptions, but I decided to implement as much as I 
could, based on my common-sense. Of course, all of this should be discussed with the product owner/BA.
- Lastly: I have been coding more in Kotlin in the last few years and I miss the feature of `internal` visibility.
More to read [here](https://kotlinlang.org/docs/visibility-modifiers.html#modules). Thanks to this classes like 
[MatchFactory](src/main/java/com/sportradar/scoreboard/match/MatchFactory.java) or 
[InMemoryCrudRepository](src/main/java/com/sportradar/scoreboard/repository/InMemoryCrudRepository.java)
don't have to be public and only `API` classes can be public.