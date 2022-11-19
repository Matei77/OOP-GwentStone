package utils;

import cardtypes.Card;
import cardtypes.Hero;
import cardtypes.Minion;
import cardtypes.environment_cards.Firestorm;
import cardtypes.environment_cards.HeartHound;
import cardtypes.environment_cards.Winterfell;
import fileio.CardInput;
import fileio.DecksInput;
import engine.GameEngine;
import player.Deck;
import player.Player;
import cardtypes.heroes.EmpressThorina;
import cardtypes.heroes.GeneralKocioraw;
import cardtypes.heroes.KingMudface;
import cardtypes.heroes.LordRoyce;
import cardtypes.special_minions.Disciple;
import cardtypes.special_minions.Miraj;
import cardtypes.special_minions.TheCursedOne;
import cardtypes.special_minions.TheRipper;

import java.util.ArrayList;

import static utils.Constants.ACTION_AVAILABLE;
import static utils.Constants.BACK_ROW;
import static utils.Constants.BERSERKER;
import static utils.Constants.DISCIPLE;
import static utils.Constants.EMPRESS_THORINA;
import static utils.Constants.FIRESTORM;
import static utils.Constants.FRONT_ROW;
import static utils.Constants.GENERAL_KOCIORAW;
import static utils.Constants.GOLIATH;
import static utils.Constants.HEART_HOUND;
import static utils.Constants.KING_MUDFACE;
import static utils.Constants.LORD_ROYCE;
import static utils.Constants.MAX_CARDS_PER_ROW;
import static utils.Constants.MAX_ROW_INDEX;
import static utils.Constants.MIRAJ;
import static utils.Constants.NOT_FROZEN;
import static utils.Constants.NOT_TANK;
import static utils.Constants.SENTINEL;
import static utils.Constants.TANK;
import static utils.Constants.THE_CURSED_ONE;
import static utils.Constants.THE_RIPPER;
import static utils.Constants.WARDEN;
import static utils.Constants.WINTERFELL;

public final class Utils {
  private Utils() {
  }

  /**
   * Create a new board.
   */
  public static void prepareBoard() {
    ArrayList<ArrayList<Minion>> board = new ArrayList<>(MAX_ROW_INDEX + 1);
    ArrayList<Minion> row0 = new ArrayList<>(MAX_CARDS_PER_ROW);
    ArrayList<Minion> row1 = new ArrayList<>(MAX_CARDS_PER_ROW);
    ArrayList<Minion> row2 = new ArrayList<>(MAX_CARDS_PER_ROW);
    ArrayList<Minion> row3 = new ArrayList<>(MAX_CARDS_PER_ROW);
    board.add(row0);
    board.add(row1);
    board.add(row2);
    board.add(row3);
    GameEngine.getEngine().setBoard(board);
  }

  /**
   * Creates the list of decks for the given player
   *
   * @param player     the player for which the deck data is set
   * @param decksInput the decks input data
   */
  public static void setPlayerDecksData(final Player player, final DecksInput decksInput) {
    ArrayList<Deck> listOfDecks = new ArrayList<>();

    // get each deck from input
    for (ArrayList<CardInput> inputDeck : decksInput.getDecks()) {
      Deck deck = new Deck();

      // create the list of cards for each deck
      ArrayList<Card> listOfCards = new ArrayList<>();
      for (CardInput inputCard : inputDeck) {
        // construct each card based on its type
        switch (inputCard.getName()) {
          case SENTINEL, BERSERKER -> {
            Card card = new Minion(inputCard.getMana(), inputCard.getAttackDamage(),
                inputCard.getHealth(), inputCard.getDescription(), inputCard.getColors(),
                inputCard.getName(), BACK_ROW, NOT_TANK);
            listOfCards.add(card);
          }

          case GOLIATH, WARDEN -> {
            Card card = new Minion(inputCard.getMana(), inputCard.getAttackDamage(),
                inputCard.getHealth(), inputCard.getDescription(), inputCard.getColors(),
                inputCard.getName(), FRONT_ROW, TANK);
            listOfCards.add(card);
          }

          case THE_RIPPER -> {
            Card card = new TheRipper(inputCard.getMana(), inputCard.getAttackDamage(),
                inputCard.getHealth(), inputCard.getDescription(), inputCard.getColors(),
                inputCard.getName(), FRONT_ROW, NOT_TANK);
            listOfCards.add(card);
          }

          case MIRAJ -> {
            Card card = new Miraj(inputCard.getMana(), inputCard.getAttackDamage(),
                inputCard.getHealth(), inputCard.getDescription(), inputCard.getColors(),
                inputCard.getName(), FRONT_ROW, NOT_TANK);
            listOfCards.add(card);
          }

          case THE_CURSED_ONE -> {
            Card card = new TheCursedOne(inputCard.getMana(), inputCard.getAttackDamage(),
                    inputCard.getHealth(), inputCard.getDescription(), inputCard.getColors(),
                    inputCard.getName(), BACK_ROW, NOT_TANK);
            listOfCards.add(card);
          }

          case DISCIPLE -> {
            Card card = new Disciple(inputCard.getMana(), inputCard.getAttackDamage(),
                inputCard.getHealth(), inputCard.getDescription(), inputCard.getColors(),
                inputCard.getName(), BACK_ROW, NOT_TANK);
            listOfCards.add(card);
          }

          case FIRESTORM -> {
            Card card = new Firestorm(inputCard.getMana(), inputCard.getDescription(),
                inputCard.getColors(), inputCard.getName());
            listOfCards.add(card);
          }

          case WINTERFELL -> {
            Card card = new Winterfell(inputCard.getMana(), inputCard.getDescription(),
                inputCard.getColors(), inputCard.getName());
            listOfCards.add(card);
          }

          case HEART_HOUND -> {
            Card card = new HeartHound(inputCard.getMana(), inputCard.getDescription(),
                inputCard.getColors(), inputCard.getName());
            listOfCards.add(card);
          }

          default -> { }
        }
      }
      deck.setCards(listOfCards);
      listOfDecks.add(deck);
    }

    // set the player's list of decks
    player.setDecks(listOfDecks);
  }

  /**
   * Set the player's hero.
   *
   * @param player    the player for which the hero is set
   * @param inputHero the hero to set it to
   */
  public static void setPlayerHero(final Player player, final CardInput inputHero) {
    // construct the player's hero based on its type
    switch (inputHero.getName()) {
      case LORD_ROYCE -> {
        Hero playerHero = new LordRoyce(inputHero.getMana(), inputHero.getDescription(),
            inputHero.getColors(), inputHero.getName());
        player.setHero(playerHero);
      }

      case EMPRESS_THORINA -> {
        Hero playerHero = new EmpressThorina(inputHero.getMana(), inputHero.getDescription(),
            inputHero.getColors(), inputHero.getName());
        player.setHero(playerHero);
      }

      case KING_MUDFACE -> {
        Hero playerHero = new KingMudface(inputHero.getMana(), inputHero.getDescription(),
            inputHero.getColors(), inputHero.getName());
        player.setHero(playerHero);
      }

      case GENERAL_KOCIORAW -> {
        Hero playerHero = new GeneralKocioraw(inputHero.getMana(), inputHero.getDescription(),
            inputHero.getColors(), inputHero.getName());
        player.setHero(playerHero);
      }

      default -> { }
    }
  }

  /**
   * Remove dead minions from the board.
   */
  public static void discardDeadMinions() {
    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();

    for (ArrayList<Minion> row : board) {
      row.removeIf(minion -> minion.getHealth() <= 0);
    }
  }

  /**
   * Find the highest health minion on a row.
   *
   * @param affectedRow the row on which will be searched
   * @return the minion with the highest health
   */
  public static Minion getHighestHealthMinionOnRow(final int affectedRow) {
    Minion highestHealthMinion = GameEngine.getEngine().getBoard().get(affectedRow).get(0);

    for (Minion minion : GameEngine.getEngine().getBoard().get(affectedRow)) {
      if (minion.getHealth() > highestHealthMinion.getHealth()) {
        highestHealthMinion = minion;
      }
    }

    return highestHealthMinion;
  }

  /**
   * Find the highest attack minion on a row.
   *
   * @param affectedRow the row on which will be searched
   * @return the minion with the highest attack
   */
  public static Minion getHighestAttackMinionOnRow(final int affectedRow) {
    Minion highestAttackMinion = GameEngine.getEngine().getBoard().get(affectedRow).get(0);

    for (Minion minion : GameEngine.getEngine().getBoard().get(affectedRow)) {
      if (minion.getAttackDamage() > highestAttackMinion.getAttackDamage()) {
        highestAttackMinion = minion;
      }
    }

    return highestAttackMinion;
  }

  /**
   * Unfreeze the minions from a player's side.
   *
   * @param player the player for which the minions will be unfrozen
   */
  public static void unfreezeFrozenMinions(final Player player) {
    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();

    for (Minion minion : board.get(player.getFrontRowBoardIndex())) {
      minion.setFrozen(NOT_FROZEN);
    }

    for (Minion minion : board.get(player.getBackRowBoardIndex())) {
      minion.setFrozen(NOT_FROZEN);
    }
  }

  /**
   * Reset the action available status for all minions on the board and both heroes.
   */
  public static void resetActionAvailableStatus() {
    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();

    for (ArrayList<Minion> row : board) {
      for (Minion minion : row) {
        minion.setActionAvailable(ACTION_AVAILABLE);
      }
    }

    GameEngine.getEngine().getPlayerOne().getHero().setActionAvailable(ACTION_AVAILABLE);
    GameEngine.getEngine().getPlayerTwo().getHero().setActionAvailable(ACTION_AVAILABLE);
  }

  /**
   * Check whether the enemy has a tank minion on its side of the board.
   *
   * @return <b>true</b> if the enemy has a tank and <b>false</b> otherwise
   */
  public static boolean enemyHasTank() {
    Player player = GameEngine.getCurrentPlayer();

    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();

    for (Minion minion : board.get(MAX_ROW_INDEX - player.getFrontRowBoardIndex())) {
      if (minion.isTank()) {
        return true;
      }
    }
    return false;
  }
}
