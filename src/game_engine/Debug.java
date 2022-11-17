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

import java.util.ArrayList;
import java.util.List;

import static utils.Constants.*;

public class Debug {
  public static void getCardsInHand(ActionsInput action) {
    int playerIdx = action.getPlayerIdx();
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

  public static void getPlayerDeck(ActionsInput action) {
    int playerIdx = action.getPlayerIdx();
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
      for (Card card : row) {
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

  public static void getPlayerHero(ActionsInput action) {
    // get the player's hero
    int playerIdx = action.getPlayerIdx();
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

  public static void getCardAtPosition(ArrayList<ArrayList<Minion>> board) {}

  public static void getPlayerMana(ActionsInput action) {
    int playerIdx = action.getPlayerIdx();
    Player player = getPlayer(playerIdx);

    ObjectMapper mapper = new ObjectMapper();
    ObjectNode commandObjectNode = mapper.createObjectNode();
    commandObjectNode.put("command", GET_PLAYER_MANA);
    commandObjectNode.put("playerIdx", playerIdx);
    commandObjectNode.put("output", player.getMana());

    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(commandObjectNode));
  }

  public static void getEnvironmentCardsInHand(ActionsInput action) {
    int playerIdx = action.getPlayerIdx();
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

  public static void getFrozenCardsOnTable(ArrayList<ArrayList<Card>> board) {}

  public static void getTotalGamesPlayed(GameEngine gameEngine) {}

  public static void getPlayerOneWins(Player player) {}

  public static void getPlayerTwoWins(Player player) {}

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
