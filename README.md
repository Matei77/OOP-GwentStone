**Name: Ionescu Matei-Ștefan**  
**Group: 323CAb**

# OOP Homework #1 - GwentStone

## Summary:

**GwentStone** implements the core mechanics of board games like Hearthstone 
and Gwent using OOP concepts. The application receives a list of command 
from a file in _json_ format and generates a new _json_ file with the 
required output for each command.

## Implementation:

The core of the application is represented by the `GameEngine` class. 
This  class has been designed using the _Singleton Pattern_ and acts as a 
database to hold information about the state of the game, the board and 
the players. Each of the two player instances in the class will hold 
information about the cards in hand, their decks, their mana and their hero.

When the `runEngine` method is called the players will be prepared for the 
new games by populating their decks data with the ones from input, and then 
executing the commands from the input _json_ file.

The following commands can be executed:

### Gameplay commands:
These commands are implemented in the `engine.GameActions` class.

1. **startGame**

   _This command starts a new game._

    When the _startGame_ command is given the `startGame()` method will be 
   called, and it will set the deck and the hero used in this particular game 
   for each player. This will also set the shuffleSeed for the decks, create 
   a new board and reset the cards in each player's hand.


2. **endPlayerTurn**

   _This command ends the current player's turn._
   
   When the _endPlayerTurn_ command is given the `endPlayerTurn()` method 
   will be called, and it will change the current player turn in the 
   `GameEngine` and  start a new round if both players took a turn. The 
   `startRound()` method increases the mana of both players based on the 
   current round number and adds a card from each player's deck to his hand. 
   The frozen status of minions will also be removed if they were frozen for 
   one turn.


3. **placeCard**

    _This command places a minion from the player's hand on the board._

   When the _placeCard_ command is given, the `GameActions.placeCard()` method 
   will be called, and it will use the card's `placeCard()` method on the 
   player's selected card from hand. This method will behave differently 
   based on the card that is selected. Minions will be placed on the board, 
   while selecting an environment card will result in an error.


4. **cardUsesAttack**

    _This command makes a friendly minion to attack an enemy one._

   When the _cardUsesAttack_ command is given, 
   the `GameActions.cardUsesAttack()` method will be called, and it will use 
   the `useAttack()` method of the minion to make the selected minion from the 
   board attack another minion. Dead minions will be removed from the board.


5. **cardUsesAbility**
   
   _This command makes a friendly minion use its special ability._

   When the _cardUsesAbility_ command is given, the `cardUsesAbility()` method 
   will be called, and it will use the minion's `useAbility()` method. This 
   method will have a different effect based on the type of minion using the 
   ability.


6. **useAttackHero**

   _This command makes a friendly minion attack the enemy hero._

   When the _useAttackHero_ command is given, the `useAttackHero()` method will 
   be called, and it will use the `attackHero()` method on the selected minion.
   If the minion kills the enemy minion the game ends.


7. **useHeroAbility**

   _This command makes the player's hero use its special ability._

   When the _useHeroAbility_ command is given, the `useHeroAbility()` method 
   will be called on the current player's hero. This method will behave 
   differently based on the type of hero that the player possesses.


8. useEnvironmentCard

   This command lets a player use an environment card from its hand.

   When the _useEnvironmentCard_ command is given, the `useEnvironment()` method 
   will be called on the selected card from the player's hand. This will 
   behave differently based on the type of the card selected. Selecting a 
   minion card will result in an error, while using it on an environment 
   card will result in a different effect base on its type.


Errors in the input data are handled using the `throwError(String message)` 
method from the 
`ErrorHandler` class.


### Debug commands:
These commands are implemented in the `engine.Debug` class. Each of these 
commands will create a commandObjectNode with the required data, that will be 
added to the output ArrayNode.

1. getCardsInHand
2. getPlayerDeck
3. getCardsOnTable
4. getPlayerTurn
5. getPlayerHero
6. getCardAtPosition
7. getPlayerMana
8. getEnvironmentCardsInHand
9. getFrozenCardsOnTable

### Statistics commands:
These commands are implemented in the `engine.Debug` class. Each of these
commands will create a commandObjectNode with the required data, that will be 
added to the output ArrayNode.

1. getTotalGamesPlayed
2. getPlayerOneWins
3. getPlayerTwoWins


## Cards structure

There are three major types of cards that implement classes with the same 
name, each one of them extending the abstract class `Card`:

1. `Minion`

    This represents a minion card. This class can be instantiated for a minion 
   with no ability to be created. Special minions in the game extend this 
   class and have the ability to use their special powers:
   1. **Disciple**
   2. **Miraj**
   3. **TheCursedOne**
   4. **TheRipper**


2. `Environment`

    This represents an environment card. This class is abstract so an 
   environment card can only be instantiated using one of the derived 
   classes, each one of them possessing a different effect:
   1. **Firestorm**
   2. **HeartHound**
   3. **Winterfell**


3. `Hero`

    This represents a hero card. Each player has one and when the hero dies the 
   game ends. This class is abstract so a hero card can only be instantiated 
   using one of the derived classes, each one of them having a different 
   special ability:
   1. **EmpressThorina**
   2. **GeneralKocioraw**
   3. **KingMudface**
   4. **LordRoyce**

