package card_types;

import fileio.ActionsInput;
import player.Player;

import java.util.ArrayList;

abstract public class Card {
  private int manaCost;
  private String description;
  private ArrayList<String> colors;
  private String name;


  public Card(int manaCost, String description, ArrayList<String> colors, String name) {
    this.manaCost = manaCost;
    this.description = description;
    this.colors = colors;
    this.name = name;
  }

  public void placeCard() {}

  public void useEnvironment() {}

  public int getHealth() {
    return -1;
  }

  public void setHealth(int health) {}

  public int getAttackDamage() {
    return -1;
  }

  public void setAttackDamage(int attackDamage) {}

  public int getManaCost() {
    return manaCost;
  }

  public void setManaCost(int manaCost) {
    this.manaCost = manaCost;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ArrayList<String> getColors() {
    return colors;
  }

  public void setColors(ArrayList<String> colors) {
    this.colors = colors;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
