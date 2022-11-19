package player;

import card_types.Card;
import card_types.Hero;

import java.util.ArrayList;

public class Player {
  private ArrayList<Deck> decks;
  private int decksNr;
  private int nrCardsInDeck;

  private Deck currentDeck;
  private ArrayList<Card> cardsInHand;
  private Hero hero;
  private int mana = 0;
  private final int frontRowBoardIndex;
  private final int backRowBoardIndex;

  public Player(int frontRowBoardIndex, int backRowBoardIndex) {
    this.frontRowBoardIndex = frontRowBoardIndex;
    this.backRowBoardIndex = backRowBoardIndex;
  }

  public int getMana() {
    return mana;
  }

  public void setMana(int mana) {
    this.mana = mana;
  }

  public void setCurrentDeck(int chosenDeckIndex) {
    currentDeck = new Deck();
    ArrayList<Card> deckCopy = new ArrayList<>();

    for (Card card : decks.get(chosenDeckIndex).getCards()) {
      Card copyCard = card.makeCopy();
      deckCopy.add(copyCard);
    }
    currentDeck.setCards(deckCopy);
  }

  public Deck getCurrentDeck() {
    return currentDeck;
  }

  public ArrayList<Card> getCardsInHand() {
    return cardsInHand;
  }

  public void setCardsInHand(ArrayList<Card> cardsInHand) {
    this.cardsInHand = cardsInHand;
  }

  public Hero getHero() {
    return hero;
  }

  public void setHero(Hero hero) {
    this.hero = hero;
  }

  public int getNrCardsInDeck() {
    return nrCardsInDeck;
  }

  public void setNrCardsInDeck(int nrCardsInDeck) {
    this.nrCardsInDeck = nrCardsInDeck;
  }

  public ArrayList<Deck> getDecks() {
    return decks;
  }

  public void setDecks(ArrayList<Deck> decks) {
    this.decks = decks;
  }

  public int getDecksNr() {
    return decksNr;
  }

  public void setDecksNr(int decksNr) {
    this.decksNr = decksNr;
  }

  public int getFrontRowBoardIndex() {
    return frontRowBoardIndex;
  }

  public int getBackRowBoardIndex() {
    return backRowBoardIndex;
  }
}
