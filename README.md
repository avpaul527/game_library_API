# Game Library API
This API was created to house game info, have a player profile to save games to player's library, and leave reviews on games! Here are the endpoints & samples of payloads required for use!

## Games
### Add a game/Edit game
- @POST
  - *http://localhost:8080/games*
- @PUT
  - *http://localhost:8080/games/{gameId}*
- `{`
-	`"title": "Mortal Kombat",`
-	`"year": "1992",`
-	`"category": "Fighting",`
-	`"publisher": "Midway"`
- `}`
### Get Game(s)
- @GET (All Games)
  - *http://localhost:8080/games*
- @GET (By Game Id)
  - *http://localhost:8080/games/{gameId}*
- @GET (By Game Title)
  - *http://localhost:8080/game?title=____*
### Search Games (By any word/characters)
- @GET (Title)
  - *http://localhost:8080/game/search?title=____*
- @GET (Category)
  - *http://localhost:8080/game/search?category=____*
- @GET (Publisher)
  - *http://localhost:8080/game/search?publisher=____*
- @GET (Year)
  - *http://localhost:8080/game/search?year=____*
### Delete Game
- @DELETE
  - *http://localhost:8080/games/{gameId}*
## Players
### Add a player/Edit player
- @POST
  - *http://localhost:8080/players*
- @PUT
  - *http://localhost:8080/players/{userId}*
- `{`
-	`"username": "iseeyou2",`
- `}`
### Add/Remove a game to Player's Saved Games
- @PUT (Save game by Title)
  - *http://localhost:8080/players/{userId}/games*
- @PUT (Remove game by Title)
  - *http://localhost:8080/players/{userId}/games/remove*
- `{`
-	`"title": "Mortal Kombat",`
- `}`
### Get Player(s)
- @GET (All Players)
  - *http://localhost:8080/players*
- @GET (By Player Id)
  - *http://localhost:8080/players/{userId}*
- @GET (By Username)
  - *http://localhost:8080/player/{username}*
### Delete Player
- @DELETE
  - *http://localhost:8080/players/{userId}*
## Reviews
- @POST
  - *http://localhost:8080/reviews/{gameId}*
- `{`
-	`"rating": "4.5",`
-	`"review": "This game is great!"`
- `}`
### Get Review(s)
- @GET (All Reviews)
  - *http://localhost:8080/reviews*
- @GET (By Review Id)
  - *http://localhost:8080/reviews/{reviewId}/games/{gameId}*
- @GET (By Game)
  - *http://localhost:8080/reviews/{gameId}/reviews*
### Delete Player
- @DELETE
  - *http://localhost:8080//reviews/{reviewId}/games/{gameId}*
