package game_engine;

import card_types.Card;
import card_types.Minion;
import fileio.ActionsInput;
import fileio.GameInput;
import fileio.Input;
import player.Player;
import utils.Utils;

import java.util.ArrayList;

import static utils.Constants.*;

public class GameActions {
  private static ActionsInput currentAction;

  public static void preparePlayers() {
    Player playerOne = GameEngine.getEngine().getPlayerOne();
    Player playerTwo = GameEngine.getEngine().getPlayerTwo();
    Input inputData = GameEngine.getEngine().getInputData();

    Utils.setPlayerDecksData(playerOne, inputData.getPlayerOneDecks());
    Utils.setPlayerDecksData(playerTwo, inputData.getPlayerTwoDecks());

    GameEngine.getEngine().setPlayerOneWins(0);
    GameEngine.getEngine().setPlayerTwoWins(0);
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
    ArrayList<Card> cardsInHand = new ArrayList<>();
    playerOne.setCardsInHand(cardsInHand);
    cardsInHand = new ArrayList<>();
    playerTwo.setCardsInHand(cardsInHand);
  }

  public static void startRound() {
    Player playerOne = GameEngine.getEngine().getPlayerOne();
    Player playerTwo = GameEngine.getEngine().getPlayerTwo();
    int currentRoundNr = GameEngine.getEngine().getCurrentRoundNr() + 1;
    GameEngine.getEngine().setCurrentRoundNr(currentRoundNr);

    // update hand and deck
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

    // TODO reset all cards attacked status
    Utils.resetActionAvailableStatus();
  }

  private static void endPlayerTurn() {
    if (GameEngine.getEngine().getPlayerTurn() == PLAYER_ONE_TURN) {
      // TODO unfreeze frozen minions for player1
      Utils.unfreezeFrozenMinions(GameEngine.getEngine().getPlayerOne());
      GameEngine.getEngine().setPlayerTurn(PLAYER_TWO_TURN);
    } else {
      // TODO unfreeze frozen minions for player2
      Utils.unfreezeFrozenMinions(GameEngine.getEngine().getPlayerTwo());
      GameEngine.getEngine().setPlayerTurn(PLAYER_ONE_TURN);
    }
    // start next round
    if (GameEngine.getEngine().getPlayerTurn() == GameEngine.getEngine().getStartingPlayer()) {
      startRound();
    }
  }

  private static void placeCard() {
    int handIndex = currentAction.getHandIdx();
    Player player = GameEngine.getCurrentPlayer();
    player.getCardsInHand().get(handIndex).placeCard();
  }

  private static void cardUsesAttack() {
    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();
    int cardAttackerX = currentAction.getCardAttacker().getX();
    int cardAttackerY = currentAction.getCardAttacker().getY();
    board.get(cardAttackerX).get(cardAttackerY).useAttack();
    Utils.discardDeadMinions();
  }

  private static void cardUsesAbility() {
    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();
    int cardAttackerX = currentAction.getCardAttacker().getX();
    int cardAttackerY = currentAction.getCardAttacker().getY();
    board.get(cardAttackerX).get(cardAttackerY).useAbility();
    Utils.discardDeadMinions();
  }

  private static void useAttackHero() {
    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();
    int cardAttackerX = currentAction.getCardAttacker().getX();
    int cardAttackerY = currentAction.getCardAttacker().getY();
    board.get(cardAttackerX).get(cardAttackerY).attackHero();
  }

  private static void useHeroAbility() {
    Player player = GameEngine.getCurrentPlayer();
    player.getHero().useHeroAbility();
  }

  private static void useEnvironmentCard() {
    int handIndex = currentAction.getHandIdx();
    Player player = GameEngine.getCurrentPlayer();
    player.getCardsInHand().get(handIndex).useEnvironment();
  }

  public static void executeActions(ArrayList<ActionsInput> actions) {
    for (ActionsInput action : actions) {
      currentAction = action;
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
            Debug.getCardsInHand();
            break;
          case GET_PLAYER_DECK:
            Debug.getPlayerDeck();
            break;
          case GET_CARDS_ON_TABLE:
            Debug.getCardsOnTable();
            break;
          case GET_PLAYER_TURN:
            Debug.getPlayerTurn();
            break;
          case GET_PLAYER_HERO:
            Debug.getPlayerHero();
            break;
          case GET_CARD_AT_POSITION:
            Debug.getCardAtPosition();
            break;
          case GET_PLAYER_MANA:
            Debug.getPlayerMana();
            break;
          case GET_ENVIRONMENT_CARDS_IN_HAND:
            Debug.getEnvironmentCardsInHand();
            break;
          case GET_FROZEN_CARDS_ON_TABLE:
            Debug.getFrozenCardsOnTable();
            break;
          case GET_TOTAL_GAMES_PLAYED:
            Debug.getTotalGamesPlayed();
            break;
          case GET_PLAYER_ONE_WINS:
            Debug.getPlayerOneWins();
            break;
          case GET_PLAYER_TWO_WINS:
            Debug.getPlayerTwoWins();
            break;
          default:
            break;
        }
    }
  }

  public static ActionsInput getCurrentAction() {
    return currentAction;
  }
}
