package cardtypes;

import java.util.ArrayList;

/**
 * Represents a card.
 *
 * @author Ionescu Matei-Stefan
 * @group 323CAb
 * @year 2022-2023
 * @project GwentStone
 */
public abstract class Card {
  private final int manaCost;
  private final String description;
  private final ArrayList<String> colors;
  private final String name;


  /**
   * Constructor for new Card.
   */
  public Card(final int manaCost, final String description, final ArrayList<String> colors,
              final String name) {
    this.manaCost = manaCost;
    this.description = description;
    this.colors = colors;
    this.name = name;
  }


  /**
   * Place this card on the board.
   * This will behave differently if the card is a minion or an environment card.
   *
   * @see Minion#placeCard()
   * @see Environment#placeCard()
   */
  public void placeCard() { }

  /**
   * Use this environment card.
   * This will behave differently if the card is a minion or an environment card.
   *
   * @see Minion#useEnvironment()
   * @see Environment#useEnvironment()
   */
  public void useEnvironment() { }

  /**
   * Make a copy of this card.
   */
  public Card makeCopy() {
    return null;
  }


  /**
   * Get the health of this card.
   *
   * @return the health of this card or -1 if the card does not have a health field.
   */
  public int getHealth() {
    return -1;
  }


  /**
   * Set the health of this card. This will have no effect on environment cards.
   *
   * @param health the new health of this card
   */
  public void setHealth(final int health) { }

  /**
   * Get the attack of this card.
   *
   * @return the attack of this card or -1 if the card does not have an attack field.
   */
  public int getAttackDamage() {
    return -1;
  }

  /**
   * Set the attack of this card. This will have no effect on environment cards.
   *
   * @param attackDamage the new attack of this card
   */
  public void setAttackDamage(final int attackDamage) { }

  /**
   * Get the mana cost of this card.
   */
  public int getManaCost() {
    return manaCost;
  }


  /**
   * Get the description of this card.
   */
  public String getDescription() {
    return description;
  }


  /**
   * Get the colors of this card.
   */
  public ArrayList<String> getColors() {
    return colors;
  }

  /**
   * Get the name of this card.
   */
  public String getName() {
    return name;
  }
}
