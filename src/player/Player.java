package player;

import card_types.Card;
import card_types.Hero;

import java.util.ArrayList;

public class Player {
  private ArrayList<Deck> decks;
  private int decksNr;
  private int nrCardsInDeck;
  private Deck currentDeck;
  private Hero hero;
  private int mana = 0;
  private ArrayList<Card> cardsInHand = new ArrayList<>();


  public int getMana() {
    return mana;
  }

  public void setMana(int mana) {
    this.mana = mana;
  }

  public void setCurrentDeck(int chosenDeckIndex) {
    currentDeck = new Deck();
    ArrayList<Card> deckCopy = new ArrayList<>(decks.get(chosenDeckIndex).getCards());
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
}
