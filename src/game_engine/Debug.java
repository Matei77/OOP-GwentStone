package game_engine;

import card_types.Card;
import card_types.Environment;
import card_types.Hero;
import card_types.Minion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import player.Deck;
import player.Player;
import utils.ErrorHandler;

import java.util.ArrayList;
import java.util.List;

import static utils.Constants.*;

public class Debug {
  public static void getCardsInHand() {
    int playerIdx = GameActions.getCurrentAction().getPlayerIdx();
    Player player = getPlayer(playerIdx);

    ObjectMapper mapper = new ObjectMapper();
    ArrayNode cards = mapper.createArrayNode();

    ArrayList<Card> playerHand = player.getCardsInHand();
    for (Card card : playerHand) {
      ObjectNode cardObjectNode = createCardOutput(mapper, card);
      cards.add(cardObjectNode);
    }

    ObjectNode commandObjectNode = mapper.createObjectNode();
    commandObjectNode.put("command", GET_CARDS_IN_HAND);
    commandObjectNode.put("playerIdx", playerIdx);
    commandObjectNode.set("output", cards);

    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(commandObjectNode));
  }

  public static void getPlayerDeck() {
    int playerIdx = GameActions.getCurrentAction().getPlayerIdx();
    Player player = getPlayer(playerIdx);

    Deck playerCurrentDeck = player.getCurrentDeck();

    ObjectMapper mapper = new ObjectMapper();
    ArrayNode cards = mapper.createArrayNode();

    for (Card card : playerCurrentDeck.getCards()) {
      ObjectNode cardObjectNode = createCardOutput(mapper, card);
      cards.add(cardObjectNode);
    }

    ObjectNode commandObjectNode = mapper.createObjectNode();
    commandObjectNode.put("command", GET_PLAYER_DECK);
    commandObjectNode.put("playerIdx", playerIdx);
    commandObjectNode.set("output", cards);

    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(commandObjectNode));
  }

  public static void getCardsOnTable() {
    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();
    ObjectMapper mapper = new ObjectMapper();
    ArrayNode rows = mapper.createArrayNode();

    for (ArrayList<Minion> row : board) {
      ArrayNode cards = mapper.createArrayNode();
      for (Minion card : row) {
        ObjectNode cardObjectNode = createCardOutput(mapper, card);
        cards.add(cardObjectNode);
      }
      rows.add(cards);
    }


    ObjectNode commandObjectNode = mapper.createObjectNode();
    commandObjectNode.put("command", GET_CARDS_ON_TABLE);
    commandObjectNode.set("output", rows);

    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(commandObjectNode));
  }

  public static void getPlayerTurn() {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode commandObjectNode = mapper.createObjectNode();
    commandObjectNode.put("command", GET_PLAYER_TURN);
    commandObjectNode.put("output", GameEngine.getEngine().getPlayerTurn());

    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(commandObjectNode));
  }

  public static void getPlayerHero() {
    // get the player's hero
    int playerIdx = GameActions.getCurrentAction().getPlayerIdx();
    Player player = getPlayer(playerIdx);
    Hero hero = player.getHero();

    // generate the output for the command
    ObjectMapper mapper = new ObjectMapper();

    ObjectNode outputObjectNode = createCardOutput(mapper, player.getHero());

    ObjectNode commandObjectNode = mapper.createObjectNode();
    commandObjectNode.put("command", GET_PLAYER_HERO);
    commandObjectNode.put("playerIdx", playerIdx);
    commandObjectNode.set("output", outputObjectNode);

    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(commandObjectNode));
  }

  public static void getCardAtPosition() {
    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();
    ActionsInput action = GameActions.getCurrentAction();
    int x = action.getX();
    int y = action.getY();


    ObjectMapper mapper = new ObjectMapper();

    ObjectNode commandObjectNode = mapper.createObjectNode();
    commandObjectNode.put("command", GET_CARD_AT_POSITION);
    commandObjectNode.put("x", x);
    commandObjectNode.put("y", y);

    if (board.get(x).size() <= y) {
      commandObjectNode.put("output", "No card available at that position.");
    } else {
      ObjectNode card = createCardOutput(mapper, board.get(x).get(y));
      commandObjectNode.set("output", card);
    }

    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(commandObjectNode));
  }

  public static void getPlayerMana() {
    int playerIdx = GameActions.getCurrentAction().getPlayerIdx();
    Player player = getPlayer(playerIdx);

    ObjectMapper mapper = new ObjectMapper();
    ObjectNode commandObjectNode = mapper.createObjectNode();
    commandObjectNode.put("command", GET_PLAYER_MANA);
    commandObjectNode.put("playerIdx", playerIdx);
    commandObjectNode.put("output", player.getMana());

    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(commandObjectNode));
  }

  public static void getEnvironmentCardsInHand() {
    int playerIdx = GameActions.getCurrentAction().getPlayerIdx();
    Player player = getPlayer(playerIdx);

    ObjectMapper mapper = new ObjectMapper();
    ArrayNode cards = mapper.createArrayNode();

    ArrayList<Card> playerHand = player.getCardsInHand();
    for (Card card : playerHand) {
      if (card instanceof Environment) {
        ObjectNode cardObjectNode = createCardOutput(mapper, card);
        cards.add(cardObjectNode);
      }
    }

    ObjectNode commandObjectNode = mapper.createObjectNode();
    commandObjectNode.put("command", GET_ENVIRONMENT_CARDS_IN_HAND);
    commandObjectNode.put("playerIdx", playerIdx);
    commandObjectNode.set("output", cards);

    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(commandObjectNode));
  }

  public static void getFrozenCardsOnTable() {
    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();
    ObjectMapper mapper = new ObjectMapper();

    ArrayNode cards = mapper.createArrayNode();

    for (ArrayList<Minion> row : board) {
      for (Minion card : row) {
        if (card.isFrozen()) {
          ObjectNode cardObjectNode = createCardOutput(mapper, card);
          cards.add(cardObjectNode);
        }
      }
    }

    ObjectNode commandObjectNode = mapper.createObjectNode();
    commandObjectNode.put("command", GET_FROZEN_CARDS_ON_TABLE);
    commandObjectNode.set("output", cards);

    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(commandObjectNode));
  }

  public static void getTotalGamesPlayed() {}

  public static void getPlayerOneWins() {}

  public static void getPlayerTwoWins() {}

  private static ObjectNode createCardOutput(ObjectMapper mapper, Card card) {
    ObjectNode outputObjectNode = mapper.createObjectNode();
    outputObjectNode.put("mana", card.getManaCost());
    outputObjectNode.put("description", card.getDescription());

    ArrayNode colors = mapper.createArrayNode();
    ArrayList<String> heroColors = card.getColors();
    for (String color : heroColors) {
      colors.add(color);
    }
    outputObjectNode.set("colors", colors);

    outputObjectNode.put("name", card.getName());
    if (card.getAttackDamage() >= 0)
      outputObjectNode.put("attackDamage", card.getAttackDamage());
    if (card.getHealth() >= 0)
      outputObjectNode.put("health", card.getHealth());
    return outputObjectNode;
  }

  private static Player getPlayer(int playerIdx) {
    Player player = null;
    if (playerIdx == 1)
      player = GameEngine.getEngine().getPlayerOne();
    else
      player = GameEngine.getEngine().getPlayerTwo();
    return player;
  }
}
