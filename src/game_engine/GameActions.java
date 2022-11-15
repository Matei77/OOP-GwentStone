package game_engine;

import card_types.Card;
import fileio.ActionsInput;
import fileio.GameInput;
import fileio.Input;
import player.Player;
import utils.Utils;

import java.lang.reflect.Array;
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
    int startingPlayer = game.getStartGame().getStartingPlayer();
    GameEngine.getEngine().setStartingPlayer(startingPlayer);
    GameEngine.getEngine().setPlayerTurn(startingPlayer);

    // reset card in hand
    ArrayList<Card> cardsInHand = new ArrayList<Card>();
    playerOne.setCardsInHand(cardsInHand);
    cardsInHand = new ArrayList<Card>();
    playerTwo.setCardsInHand(cardsInHand);
  }

  public static void startRound() {
    Player playerOne = GameEngine.getEngine().getPlayerOne();
    Player playerTwo = GameEngine.getEngine().getPlayerTwo();
    int currentRoundNr = GameEngine.getEngine().getCurrentRoundNr() + 1;
    GameEngine.getEngine().setCurrentRoundNr(currentRoundNr);

    // update hand and deck
    ArrayList<Card> hand = null;
    if (!playerOne.getCurrentDeck().getCards().isEmpty()) {
      hand = playerOne.getCardsInHand();
      hand.add(playerOne.getCurrentDeck().getCards().get(0));
      playerOne.getCurrentDeck().getCards().remove(0);
    }

    if (!playerTwo.getCurrentDeck().getCards().isEmpty()) {
      hand = playerTwo.getCardsInHand();
      hand.add(playerTwo.getCurrentDeck().getCards().get(0));
      playerTwo.getCurrentDeck().getCards().remove(0);
    }

    // update mana
    playerOne.setMana(playerOne.getMana() + Math.min(currentRoundNr, MAX_MANA_PER_ROUND));
    playerTwo.setMana(playerTwo.getMana() + Math.min(currentRoundNr, MAX_MANA_PER_ROUND));

    // TODO reset all cards attacked status

  }

  public static void endPlayerTurn() {
    if (GameEngine.getEngine().getPlayerTurn() == PLAYER_ONE_TURN) {
      // TODO unfreeze frozen minions for player1
      GameEngine.getEngine().setPlayerTurn(PLAYER_TWO_TURN);
    } else {
      // TODO unfreeze frozen minions for player2
      GameEngine.getEngine().setPlayerTurn(PLAYER_ONE_TURN);
    }

    // start next round
    if (GameEngine.getEngine().getPlayerTurn() == GameEngine.getEngine().getStartingPlayer()) {
      startRound();
    }
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
          Debug.getCardsInHand(action);
          break;
        case GET_PLAYER_DECK:
          Debug.getPlayerDeck(action);
          break;
        case GET_CARDS_ON_TABLE:
          break;
        case GET_PLAYER_TURN:
          Debug.getPlayerTurn();
          break;
        case GET_PLAYER_HERO:
          Debug.getPlayerHero(action);
          break;
        case GET_CARD_AT_POSITION:
          break;
        case GET_PLAYER_MANA:
          Debug.getPlayerMana(action);
          break;
        case GET_ENVIRONMENT_CARDS_IN_HAND:
          Debug.getEnvironmentCardsInHand(action);
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
