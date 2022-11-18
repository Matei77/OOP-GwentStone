package utils;

import card_types.Card;
import card_types.Hero;
import card_types.Minion;
import card_types.environment_cards.Firestorm;
import card_types.environment_cards.HeartHound;
import card_types.environment_cards.Winterfell;
import fileio.CardInput;
import fileio.DecksInput;
import game_engine.GameEngine;
import player.Deck;
import player.Player;
import card_types.heroes.EmpressThorina;
import card_types.heroes.GeneralKocioraw;
import card_types.heroes.KingMudface;
import card_types.heroes.LordRoyce;
import card_types.special_minions.Disciple;
import card_types.special_minions.Miraj;
import card_types.special_minions.TheCursedOne;
import card_types.special_minions.TheRipper;

import java.util.ArrayList;

import static utils.Constants.*;

public class Utils {
  public static void setPlayerDecksData(Player player, DecksInput decksInput) {
    ArrayList<Deck> listOfDecks = new ArrayList<Deck>();
    for (ArrayList<CardInput> inputDeck : decksInput.getDecks()) {
      Deck deck = new Deck();
      ArrayList<Card> listOfCards = new ArrayList<Card>();
      for (CardInput inputCard : inputDeck) {
        Card card = null;
        switch(inputCard.getName()) {
          case SENTINEL:
          case BERSERKER:
            card = new Minion(inputCard.getMana(), inputCard.getAttackDamage(), inputCard.getHealth(),
                inputCard.getDescription(), inputCard.getColors(), inputCard.getName(), BACK_ROW,
                NOT_TANK);
            break;
          case GOLIATH:
          case WARDEN:
            card = new Minion(inputCard.getMana(), inputCard.getAttackDamage(), inputCard.getHealth(),
                inputCard.getDescription(), inputCard.getColors(), inputCard.getName(), FRONT_ROW
                , TANK);
            break;

          case THE_RIPPER:
            card = new TheRipper(inputCard.getMana(), inputCard.getAttackDamage(),
                inputCard.getHealth(), inputCard.getDescription(), inputCard.getColors(),
                inputCard.getName(), FRONT_ROW, NOT_TANK);
            break;

          case MIRAJ:
            card = new Miraj(inputCard.getMana(), inputCard.getAttackDamage(), inputCard.getHealth(),
                inputCard.getDescription(), inputCard.getColors(), inputCard.getName(), FRONT_ROW
                , NOT_TANK);
            break;

          case THE_CURSED_ONE:
            card = new TheCursedOne(inputCard.getMana(), inputCard.getAttackDamage(), inputCard.getHealth(),
                inputCard.getDescription(), inputCard.getColors(), inputCard.getName(), BACK_ROW,
                NOT_TANK);
            break;

          case DISCIPLE:
            card = new Disciple(inputCard.getMana(), inputCard.getAttackDamage(), inputCard.getHealth(),
                inputCard.getDescription(), inputCard.getColors(), inputCard.getName(), BACK_ROW,
                NOT_TANK);
            break;

          case FIRESTORM:
            card = new Firestorm(inputCard.getMana(), inputCard.getDescription(), inputCard.getColors(),
                inputCard.getName());
            break;

          case WINTERFELL:
            card = new Winterfell(inputCard.getMana(), inputCard.getDescription(), inputCard.getColors(),
                inputCard.getName());
            break;

          case HEART_HOUND:
            card = new HeartHound(inputCard.getMana(), inputCard.getDescription(), inputCard.getColors(),
                inputCard.getName());
            break;
        }
        listOfCards.add(card);
      }
      deck.setCards(listOfCards);
      listOfDecks.add(deck);
    }
    player.setDecks(listOfDecks);
    player.setDecksNr(decksInput.getNrDecks());
    player.setNrCardsInDeck(decksInput.getNrCardsInDeck());
  }

  public static void SetPlayerHero(Player player, CardInput inputHero) {
    Hero playerHero = null;
    switch (inputHero.getName()) {
      case LORD_ROYCE:
        playerHero = new LordRoyce(inputHero.getMana(), inputHero.getDescription(),
            inputHero.getColors(), inputHero.getName());
        break;

      case EMPRESS_THORINA:
        playerHero = new EmpressThorina(inputHero.getMana(), inputHero.getDescription(),
            inputHero.getColors(), inputHero.getName());
        break;

      case KING_MUDFACE:
        playerHero = new KingMudface(inputHero.getMana(), inputHero.getDescription(),
            inputHero.getColors(), inputHero.getName());
        break;

      case GENERAL_KOCIORAW:
        playerHero = new GeneralKocioraw(inputHero.getMana(), inputHero.getDescription(),
            inputHero.getColors(), inputHero.getName());
        break;
    }
    player.setHero(playerHero);
  }

  public static void discardDeadMinions() {
    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();
    for (ArrayList<Minion> row : board)
      row.removeIf(minion -> minion.getHealth() <= 0);
  }

  public static Minion getHighestHealthMinionOnRow(int affectedRow) {
    Minion highestHealthMinion = GameEngine.getEngine().getBoard().get(affectedRow).get(0);
    for (Minion minion : GameEngine.getEngine().getBoard().get(affectedRow)) {
      if (minion.getHealth() > highestHealthMinion.getHealth())
        highestHealthMinion = minion;
    }
    return highestHealthMinion;
  }

  public static Minion getHighestAttackMinionOnRow(int affectedRow) {
    Minion highestAttackMinion = GameEngine.getEngine().getBoard().get(affectedRow).get(0);
    for (Minion minion : GameEngine.getEngine().getBoard().get(affectedRow)) {
      if (minion.getHealth() > highestAttackMinion.getHealth())
        highestAttackMinion = minion;
    }
    return highestAttackMinion;
  }

  public static void unfreezeFrozenMinions(Player player) {
    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();
    for (Minion minion : board.get(player.getFrontRowBoardIndex())) {
      minion.setFrozen(NOT_FROZEN);
    }
    for (Minion minion: board.get(player.getBackRowBoardIndex())) {
      minion.setFrozen(NOT_FROZEN);
    }
  }
  public static void resetActionAvailableStatus() {
    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();
    for (ArrayList<Minion> row : board)
      for (Minion minion : row)
        minion.setActionAvailable(ACTION_AVAILABLE);

    GameEngine.getEngine().getPlayerOne().getHero().setActionAvailable(ACTION_AVAILABLE);
    GameEngine.getEngine().getPlayerTwo().getHero().setActionAvailable(ACTION_AVAILABLE);
  }

  public static boolean enemyHasTank() {
    Player player = GameEngine.getCurrentPlayer();
    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();
    for (Minion minion : board.get(MAX_ROW_INDEX - player.getFrontRowBoardIndex())) {
      if (minion.isTank())
        return true;
    }


    return false;
  }
}
