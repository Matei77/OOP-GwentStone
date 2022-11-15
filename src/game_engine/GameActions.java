package game_engine;

import card_types.Card;
import fileio.ActionsInput;
import fileio.GameInput;
import fileio.Input;
import player.Player;
import utils.Utils;

import java.util.ArrayList;

import static utils.Constants.*;

public class GameActions {
  public static void preparePlayers() {
    Player playerOne = GameEngine.getEngine().getPlayerOne();
    Player playerTwo = GameEngine.getEngine().getPlayerTwo();
    Input inputData = GameEngine.getEngine().getInputData();

    Utils.setPlayerDecksData(playerOne, inputData.getPlayerOneDecks());
    Utils.setPlayerDecksData(playerTwo, inputData.getPlayerTwoDecks());
  }

  public static void startGame(GameInput game) {
    // get players
    Player playerOne = GameEngine.getEngine().getPlayerOne();
    Player playerTwo = GameEngine.getEngine().getPlayerTwo();

    // set players decks
    int chosenDeckIndex = game.getStartGame().getPlayerOneDeckIdx();
    playerOne.setCurrentDeck(chosenDeckIndex);

    chosenDeckIndex = game.getStartGame().getPlayerTwoDeckIdx();
    playerTwo.setCurrentDeck(chosenDeckIndex);

    // set shuffleSeed
    int shuffleSeed = game.getStartGame().getShuffleSeed();
    GameEngine.getEngine().setShuffleSeed(shuffleSeed);


    // set players card_types.heroes
    Utils.SetPlayerHero(playerOne, game.getStartGame().getPlayerOneHero());
    Utils.SetPlayerHero(playerTwo, game.getStartGame().getPlayerTwoHero());

    // shuffle decks
    playerOne.getCurrentDeck().shuffleCards(shuffleSeed);
    playerTwo.getCurrentDeck().shuffleCards(shuffleSeed);

    // set starting player
    int playerTurn = game.getStartGame().getStartingPlayer();
    GameEngine.getEngine().setPlayerTurn(playerTurn);
  }

  public static void startRound() {
    Player playerOne = GameEngine.getEngine().getPlayerOne();
    Player playerTwo = GameEngine.getEngine().getPlayerTwo();
    int currentRoundNr = GameEngine.getEngine().getCurrentRoundNr();

    // update hand and deck
    ArrayList<Card> hand;
    hand = playerOne.getCardsInHand();
    hand.add(playerOne.getCurrentDeck().getCards().get(0));
    playerOne.getCurrentDeck().getCards().remove(0);

    hand = playerTwo.getCardsInHand();
    hand.add(playerTwo.getCurrentDeck().getCards().get(0));
    playerTwo.getCurrentDeck().getCards().remove(0);

    // update mana
    playerOne.setMana(playerOne.getMana() + currentRoundNr);
    playerTwo.setMana(playerTwo.getMana() + currentRoundNr);
  }

  public static void endPlayerTurn() {
    GameEngine.getEngine().setCurrentRoundNr(GameEngine.getEngine().getCurrentRoundNr() + 1);
  }

  public static void placeCard() {

  }

  public static void cardUsesAttack() {

  }

  public static void cardUsesAbility() {

  }

  public static void useAttackHero() {

  }

  public static void useHeroAbility() {

  }

  public static void useEnvironmentCard() {

  }

  public static void executeActions(ArrayList<ActionsInput> actions) {
    for (ActionsInput action : actions) {
      String command = action.getCommand();
      switch (command) {
        case END_PLAYER_TURN:
          endPlayerTurn();
          break;
        case PLACE_CARD:
          placeCard();
          break;
        case CARD_USES_ATTACK:
          cardUsesAttack();
          break;
        case CARD_USES_ABILITY:
          cardUsesAbility();
          break;
        case USE_ATTACK_HERO:
          useAttackHero();
          break;
        case USE_HERO_ABILITY:
          useHeroAbility();
          break;
        case USE_ENVIRONMENT_CARD:
          useEnvironmentCard();
          break;
        case GET_CARDS_IN_HAND:
          Debug.getCardsInHand(action, GameEngine.getEngine());
          break;
        case GET_PLAYER_DECK:
          Debug.getPlayerDeck(action, GameEngine.getEngine());
          break;
        case GET_CARDS_ON_TABLE:
          break;
        case GET_PLAYER_TURN:
          Debug.getPlayerTurn(GameEngine.getEngine());
          break;
        case GET_PLAYER_HERO:
          Debug.getPlayerHero(action, GameEngine.getEngine());
          break;
        case GET_CARD_AT_POSITION:
          break;
        case GET_PLAYER_MANA:
          Debug.getPlayerMana(action, GameEngine.getEngine());
          break;
        case GET_ENVIRONMENT_CARDS_IN_HAND:
          Debug.getEnvironmentCardsInHand(action, GameEngine.getEngine());
          break;
        case GET_FROZEN_CARDS_ON_TABLE:
          break;
        case GET_TOTAL_GAMES_PLAYED:
          break;
        case GET_PLAYER_ONE_WINS:
          break;
        case GET_PLAYER_TWO_WINS:
          break;
        default:
          System.out.println("Command not found");
          break;
      }
    }
  }
}
