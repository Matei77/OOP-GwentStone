package player;

import card_types.Card;

import java.util.ArrayList;
import java.util.Random;

import static java.util.Collections.shuffle;

public class Deck {
  private ArrayList<Card> cards;

  public void shuffleCards(int shuffleSeed) {
    shuffle(cards, new Random(shuffleSeed));
  }

  public ArrayList<Card> getCards() {
    return cards;
  }

  public void setCards(ArrayList<Card> cards) {
    this.cards = cards;
  }
}
