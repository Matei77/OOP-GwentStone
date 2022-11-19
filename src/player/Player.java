package player;

import cardtypes.Card;
import cardtypes.Hero;

import java.util.ArrayList;

public final class Player {
  private ArrayList<Deck> decks;
  private Deck currentDeck;
  private ArrayList<Card> cardsInHand;
  private Hero hero;
  private int mana;
  private final int frontRowBoardIndex;
  private final int backRowBoardIndex;

  public Player(final int frontRowBoardIndex, final int backRowBoardIndex) {
    this.frontRowBoardIndex = frontRowBoardIndex;
    this.backRowBoardIndex = backRowBoardIndex;
  }

  /**
   * Set the currentDeck to be a copy of the deck at the chosen index.
   * @param chosenDeckIndex the index of the wanted deck from the array of decks
   */
  public void setCurrentDeck(final int chosenDeckIndex) {
    currentDeck = new Deck();
    ArrayList<Card> deckCopy = new ArrayList<>();

    for (Card card : decks.get(chosenDeckIndex).getCards()) {
      Card copyCard = card.makeCopy();
      deckCopy.add(copyCard);
    }
    currentDeck.setCards(deckCopy);
  }

  public int getMana() {
    return mana;
  }

  public void setMana(final int mana) {
    this.mana = mana;
  }

  public Deck getCurrentDeck() {
    return currentDeck;
  }

  public ArrayList<Card> getCardsInHand() {
    return cardsInHand;
  }

  public void setCardsInHand(final ArrayList<Card> cardsInHand) {
    this.cardsInHand = cardsInHand;
  }

  public Hero getHero() {
    return hero;
  }

  public void setHero(final Hero hero) {
    this.hero = hero;
  }

  public ArrayList<Deck> getDecks() {
    return decks;
  }

  public void setDecks(final ArrayList<Deck> decks) {
    this.decks = decks;
  }

  public int getFrontRowBoardIndex() {
    return frontRowBoardIndex;
  }

  public int getBackRowBoardIndex() {
    return backRowBoardIndex;
  }
}
