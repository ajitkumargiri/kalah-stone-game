# kalah-stone-game
Java RESTful Web Service  that runs a game of 6-stone Kalah.

Technologies used

Spring boot2

Java8

maven
 
 #### Kalah Game Rules
 Each of the two players has  six pits in front of him/her. To the right of the six pits, each player has a larger pit, his
 Kalah or house.
 At the start of the game, six stones are put in each pit.
 The player who begins picks up all the stones in any of their own pits, and sows the stones on to the right, one in
 each of the following pits, including his own Kalah. No stones are put in the opponent's' Kalah. If the players last
 stone lands in his own Kalah, he gets another turn. This can be repeated any number of times before it's the other
 player's turn.
 When the last stone lands in an own empty pit, the player captures this stone and all stones in the opposite pit (the
 other players' pit) and puts them in his own Kalah.
 The game is over as soon as one of the sides run out of stones. The player who still has stones in his/her pits keeps
 them and puts them in his/hers Kalah. The winner of the game is the player who has the most stones in his Kalah.

**Game Board of kalah**
 ```
                   |6 | 6 | 6 | 6 | 6 | 6  <---- Player2
| Player2 Kalah: 0|                      |0 :Player1 Kalah |
  Player1 --->      6 | 6 | 6 | 6 | 6 | 6|

```

## Quickstart

Running locally from command line

```
mvn clean install
mvn spring-boot:run
```
Swagger Doc url
```
http://localhost:8080/swagger-ui.html
```

api endpoint
```
curl --header "Content-Type: application/json" --request POST http://localhost:8080/games
```
This will return:

```json
{
    "id": "1725482955",
    "url": "http://localhost:8080/games/1725482955"
}
```
```
curl --header "Content-Type: application/json" --request POST http://localhost:8080/games/{gameId}/pits/{pitId}
```
This will return:

```json
{
    "id": "1725482955",
    "url": "http://localhost:8080/games/1725482955",
    "status": {
        "1": 1,
        "2": 0,
        "3": 8,
        "4": 8,
        "5": 8,
        "6": 8,
        "7": 2,
        "8": 0,
        "9": 8,
        "10": 7,
        "11": 7,
        "12": 7,
        "13": 7,
        "14": 1
    }
}
```
