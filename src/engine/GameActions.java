/* Copyright Ionescu Matei-Stefan - 323CAb - 2022-2023 */

package engine;

import cardtypes.Card;
import cardtypes.Minion;
import fileio.ActionsInput;
import fileio.GameInput;
import fileio.Input;
import player.Player;
import utils.Utils;

import java.util.ArrayList;

import static utils.Constants.CARD_USES_ABILITY;
import static utils.Constants.CARD_USES_ATTACK;
import static utils.Constants.END_PLAYER_TURN;
import static utils.Constants.GET_CARDS_IN_HAND;
import static utils.Constants.GET_CARDS_ON_TABLE;
import static utils.Constants.GET_CARD_AT_POSITION;
import static utils.Constants.GET_ENVIRONMENT_CARDS_IN_HAND;
import static utils.Constants.GET_FROZEN_CARDS_ON_TABLE;
import static utils.Constants.GET_PLAYER_DECK;
import static utils.Constants.GET_PLAYER_HERO;
import static utils.Constants.GET_PLAYER_MANA;
import static utils.Constants.GET_PLAYER_ONE_WINS;
import static utils.Constants.GET_PLAYER_TURN;
import static utils.Constants.GET_PLAYER_TWO_WINS;
import static utils.Constants.GET_TOTAL_GAMES_PLAYED;
import static utils.Constants.INITIAL_PLAYER_MANA;
import static utils.Constants.INITIAL_PLAYER_WINS;
import static utils.Constants.INITIAL_ROUND_NUMBER;
import static utils.Constants.MAX_MANA_PER_ROUND;
import static utils.Constants.PLACE_CARD;
import static utils.Constants.PLAYER_ONE_TURN;
import static utils.Constants.PLAYER_TWO_TURN;
import static utils.Constants.USE_ATTACK_HERO;
import static utils.Constants.USE_ENVIRONMENT_CARD;
import static utils.Constants.USE_HERO_ABILITY;

/**
 * Incorporates all actions that can be performed during games.
 */
public final class GameActions {
  private GameActions() {
  }
  private static ActionsInput currentAction;

  /**
   * Prepares players for games. Sets initial number of wins and player decks for each of them.
   */
  public static void preparePlayers() {
    Player playerOne = GameEngine.getEngine().getPlayerOne();
    Player playerTwo = GameEngine.getEngine().getPlayerTwo();
    Input inputData = GameEngine.getEngine().getInputData();

    // set players decks
    Utils.setPlayerDecksData(playerOne, inputData.getPlayerOneDecks());
    Utils.setPlayerDecksData(playerTwo, inputData.getPlayerTwoDecks());

    // set players initial number of wins
    GameEngine.getEngine().setPlayerOneWins(INITIAL_PLAYER_WINS);
    GameEngine.getEngine().setPlayerTwoWins(INITIAL_PLAYER_WINS);
  }

  /**
   * Prepare for a new game to start.
   * @param game the game that will be started
   */
  public static void startGame(final GameInput game) {
    Player playerOne = GameEngine.getEngine().getPlayerOne();
    Player playerTwo = GameEngine.getEngine().getPlayerTwo();

    // set the chosen deck for each player
    int chosenDeckIndex = game.getStartGame().getPlayerOneDeckIdx();
    playerOne.setCurrentDeck(chosenDeckIndex);

    chosenDeckIndex = game.getStartGame().getPlayerTwoDeckIdx();
    playerTwo.setCurrentDeck(chosenDeckIndex);

    // set shuffleSeed
    int shuffleSeed = game.getStartGame().getShuffleSeed();
    GameEngine.getEngine().setShuffleSeed(shuffleSeed);


    // set the hero for each player
    Utils.setPlayerHero(playerOne, game.getStartGame().getPlayerOneHero());
    Utils.setPlayerHero(playerTwo, game.getStartGame().getPlayerTwoHero());

    // shuffle the chosen decks
    playerOne.getCurrentDeck().shuffleCards(shuffleSeed);
    playerTwo.getCurrentDeck().shuffleCards(shuffleSeed);

    // set starting player
    int startingPlayer = game.getStartGame().getStartingPlayer();
    GameEngine.getEngine().setStartingPlayer(startingPlayer);
    GameEngine.getEngine().setPlayerTurn(startingPlayer);

    // reset card in hand
    ArrayList<Card> cardsInHand = new ArrayList<>();
    playerOne.setCardsInHand(cardsInHand);
    cardsInHand = new ArrayList<>();
    playerTwo.setCardsInHand(cardsInHand);

    // set new board
    Utils.prepareBoard();

    // set initial mana for both players
    playerOne.setMana(INITIAL_PLAYER_MANA);
    playerTwo.setMana(INITIAL_PLAYER_MANA);

    // set the current round number
    GameEngine.getEngine().setCurrentRoundNr(INITIAL_ROUND_NUMBER);
  }

  /**
   * Starts a new round. Each player draws a card and their mana is increased.
   */
  public static void startRound() {
    Player playerOne = GameEngine.getEngine().getPlayerOne();
    Player playerTwo = GameEngine.getEngine().getPlayerTwo();

    // update the current round number
    int currentRoundNr = GameEngine.getEngine().getCurrentRoundNr() + 1;
    GameEngine.getEngine().setCurrentRoundNr(currentRoundNr);

    // update hand and deck for each player
    ArrayList<Card> hand;
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

    // reset the attacked status for each card on the board
    Utils.resetActionAvailableStatus();
  }

  /**
   * Executes game actions.
   * @param actions the ArrayList that contains the actions that need to be executed
   */
  public static void executeActions(final ArrayList<ActionsInput> actions) {
    for (ActionsInput action : actions) {
      currentAction = action;
      String command = action.getCommand();
      switch (command) {
        case END_PLAYER_TURN -> endPlayerTurn();
        case PLACE_CARD -> placeCard();
        case CARD_USES_ATTACK -> cardUsesAttack();
        case CARD_USES_ABILITY -> cardUsesAbility();
        case USE_ATTACK_HERO -> useAttackHero();
        case USE_HERO_ABILITY -> useHeroAbility();
        case USE_ENVIRONMENT_CARD -> useEnvironmentCard();
        case GET_CARDS_IN_HAND -> Debug.getCardsInHand();
        case GET_PLAYER_DECK -> Debug.getPlayerDeck();
        case GET_CARDS_ON_TABLE -> Debug.getCardsOnTable();
        case GET_PLAYER_TURN -> Debug.getPlayerTurn();
        case GET_PLAYER_HERO -> Debug.getPlayerHero();
        case GET_CARD_AT_POSITION -> Debug.getCardAtPosition();
        case GET_PLAYER_MANA -> Debug.getPlayerMana();
        case GET_ENVIRONMENT_CARDS_IN_HAND -> Debug.getEnvironmentCardsInHand();
        case GET_FROZEN_CARDS_ON_TABLE -> Debug.getFrozenCardsOnTable();
        case GET_TOTAL_GAMES_PLAYED -> Debug.getTotalGamesPlayed();
        case GET_PLAYER_ONE_WINS -> Debug.getPlayerOneWins();
        case GET_PLAYER_TWO_WINS -> Debug.getPlayerTwoWins();
        default -> { }
      }
    }
  }


  /**
   * End the player's turn.
   */
  private static void endPlayerTurn() {
    if (GameEngine.getEngine().getPlayerTurn() == PLAYER_ONE_TURN) {
      // unfreeze frozen minions for player one
      Utils.unfreezeFrozenMinions(GameEngine.getEngine().getPlayerOne());

      // change the player's turn
      GameEngine.getEngine().setPlayerTurn(PLAYER_TWO_TURN);
    } else {
      // unfreeze frozen minions for player two
      Utils.unfreezeFrozenMinions(GameEngine.getEngine().getPlayerTwo());

      // change the player's turn
      GameEngine.getEngine().setPlayerTurn(PLAYER_ONE_TURN);
    }
    // if both players took their turn start next round
    if (GameEngine.getEngine().getPlayerTurn() == GameEngine.getEngine().getStartingPlayer()) {
      startRound();
    }
  }

  /**
   * Place a minion from the player's hand on the board.
   */
  private static void placeCard() {
    int handIndex = currentAction.getHandIdx();
    Player player = GameEngine.getCurrentPlayer();
    player.getCardsInHand().get(handIndex).placeCard();
  }

  /**
   * The player's minion attacks an enemy one.
   */
  private static void cardUsesAttack() {
    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();
    int cardAttackerX = currentAction.getCardAttacker().getX();
    int cardAttackerY = currentAction.getCardAttacker().getY();
    board.get(cardAttackerX).get(cardAttackerY).useAttack();
    Utils.discardDeadMinions();
  }

  /**
   * The player's minion uses his special ability.
   */
  private static void cardUsesAbility() {
    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();
    int cardAttackerX = currentAction.getCardAttacker().getX();
    int cardAttackerY = currentAction.getCardAttacker().getY();
    board.get(cardAttackerX).get(cardAttackerY).useMinionAbility();
    Utils.discardDeadMinions();
  }

  /**
   * The player's minion attack the enemy hero.
   */
  private static void useAttackHero() {
    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();
    int cardAttackerX = currentAction.getCardAttacker().getX();
    int cardAttackerY = currentAction.getCardAttacker().getY();
    board.get(cardAttackerX).get(cardAttackerY).attackHero();
  }

  /**
   * The player's hero uses his special ability.
   */
  private static void useHeroAbility() {
    Player player = GameEngine.getCurrentPlayer();
    player.getHero().useHeroAbility();
  }

  /**
   * The player uses an environment card from his hand.
   */
  private static void useEnvironmentCard() {
    int handIndex = currentAction.getHandIdx();
    Player player = GameEngine.getCurrentPlayer();
    player.getCardsInHand().get(handIndex).useEnvironment();
  }


  /**
   * @return the current game action
   */
  public static ActionsInput getCurrentAction() {
    return currentAction;
  }
}
