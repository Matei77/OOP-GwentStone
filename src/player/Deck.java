/* Copyright Ionescu Matei-Stefan - 323CAb - 2022-2023 */

package player;

import cardtypes.Card;

import java.util.ArrayList;
import java.util.Random;

import static java.util.Collections.shuffle;

/**
 * Represents a deck of cards.
 */
public final class Deck {
  private ArrayList<Card> cards;

  /**
   * Shuffle the deck of cards.
   * @param shuffleSeed the shuffleSeed with which the deck will be shuffled
   */
  public void shuffleCards(final int shuffleSeed) {
    shuffle(cards, new Random(shuffleSeed));
  }

  public ArrayList<Card> getCards() {
    return cards;
  }

  public void setCards(final ArrayList<Card> cards) {
    this.cards = cards;
  }
}
