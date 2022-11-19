package engine;

import cardtypes.Card;
import cardtypes.Environment;
import cardtypes.Minion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import player.Deck;
import player.Player;

import java.util.ArrayList;
import java.util.List;

import static utils.Constants.GET_CARDS_IN_HAND;
import static utils.Constants.GET_CARDS_ON_TABLE;
import static utils.Constants.GET_CARD_AT_POSITION;
import static utils.Constants.GET_ENVIRONMENT_CARDS_IN_HAND;
import static utils.Constants.GET_FROZEN_CARDS_ON_TABLE;
import static utils.Constants.GET_PLAYER_DECK;
import static utils.Constants.GET_PLAYER_HERO;
import static utils.Constants.GET_PLAYER_MANA;
import static utils.Constants.GET_PLAYER_ONE_WINS;
import static utils.Constants.GET_PLAYER_TURN;
import static utils.Constants.GET_PLAYER_TWO_WINS;
import static utils.Constants.GET_TOTAL_GAMES_PLAYED;

public final class Debug {
  private Debug() { }

  /**
   * Put all cards in the player's hand in the output ArrayNode.
   */
  public static void getCardsInHand() {
    // get the player with the given playerIdx
    int playerIdx = GameActions.getCurrentAction().getPlayerIdx();
    Player player = getPlayer(playerIdx);

    ObjectMapper mapper = new ObjectMapper();
    ArrayNode cards = mapper.createArrayNode();

    // create an ObjectNode for each card in the player's hand and add it to the cards ArrayNode
    ArrayList<Card> playerHand = player.getCardsInHand();
    for (Card card : playerHand) {
      ObjectNode cardObjectNode = createCardOutput(mapper, card);
      cards.add(cardObjectNode);
    }

    // create the command ObjectNode and add necessary fields
    ObjectNode commandObjectNode = mapper.createObjectNode();
    commandObjectNode.put("command", GET_CARDS_IN_HAND);
    commandObjectNode.put("playerIdx", playerIdx);
    commandObjectNode.set("output", cards);

    // update the output ArrayNode
    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(commandObjectNode));
  }

  /**
   * Put all cards in the player's deck in the output ArrayNode.
   */
  public static void getPlayerDeck() {
    // get the player with the given playerIdx
    int playerIdx = GameActions.getCurrentAction().getPlayerIdx();
    Player player = getPlayer(playerIdx);


    ObjectMapper mapper = new ObjectMapper();
    ArrayNode cards = mapper.createArrayNode();

    // create an ObjectNode for each card in the player's deck and add it to the cards ArrayNode
    Deck playerCurrentDeck = player.getCurrentDeck();
    for (Card card : playerCurrentDeck.getCards()) {
      ObjectNode cardObjectNode = createCardOutput(mapper, card);
      cards.add(cardObjectNode);
    }

    // create the command ObjectNode and add necessary fields
    ObjectNode commandObjectNode = mapper.createObjectNode();
    commandObjectNode.put("command", GET_PLAYER_DECK);
    commandObjectNode.put("playerIdx", playerIdx);
    commandObjectNode.set("output", cards);

    // update the output ArrayNode
    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(commandObjectNode));
  }

  /**
   * Put all cards on the board in the output ArrayNode.
   */
  public static void getCardsOnTable() {
    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();

    ObjectMapper mapper = new ObjectMapper();
    ArrayNode rows = mapper.createArrayNode();

    for (ArrayList<Minion> row : board) {
      ArrayNode cards = mapper.createArrayNode();

      for (Minion card : row) {
        // create an ObjectNode for each card on the board and add it to the cards ArrayNode
        ObjectNode cardObjectNode = createCardOutput(mapper, card);
        cards.add(cardObjectNode);
      }

      // add the cards to the rows ArrayNode
      rows.add(cards);
    }

    // create the command ObjectNode and add necessary fields
    ObjectNode commandObjectNode = mapper.createObjectNode();
    commandObjectNode.put("command", GET_CARDS_ON_TABLE);
    commandObjectNode.set("output", rows);

    // update the output ArrayNode
    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(commandObjectNode));
  }

  /**
   * Put the current player's turn in the output ArrayNode.
   */
  public static void getPlayerTurn() {
    ObjectMapper mapper = new ObjectMapper();

    // create the command ObjectNode and add necessary fields
    ObjectNode commandObjectNode = mapper.createObjectNode();
    commandObjectNode.put("command", GET_PLAYER_TURN);
    commandObjectNode.put("output", GameEngine.getEngine().getPlayerTurn());

    // update the output ArrayNode
    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(commandObjectNode));
  }

  /**
   * Put the player's hero card in the output ArrayNode.
   */
  public static void getPlayerHero() {
    // get the player with the given playerIdx
    int playerIdx = GameActions.getCurrentAction().getPlayerIdx();
    Player player = getPlayer(playerIdx);

    ObjectMapper mapper = new ObjectMapper();

    // generate the output for the command
    ObjectNode outputObjectNode = createCardOutput(mapper, player.getHero());

    // create the command ObjectNode and add necessary fields
    ObjectNode commandObjectNode = mapper.createObjectNode();
    commandObjectNode.put("command", GET_PLAYER_HERO);
    commandObjectNode.put("playerIdx", playerIdx);
    commandObjectNode.set("output", outputObjectNode);

    // update the output ArrayNode
    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(commandObjectNode));
  }

  /**
   * Put the card at a given position on the board in the output ArrayNode.
   */
  public static void getCardAtPosition() {
    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();

    // get the position of the card on the board
    ActionsInput action = GameActions.getCurrentAction();
    int x = action.getX();
    int y = action.getY();

    ObjectMapper mapper = new ObjectMapper();

    // create the command ObjectNode and add necessary fields
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

    // update the output ArrayNode
    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(commandObjectNode));
  }

  /**
   * Put the player's mana in the output ArrayNode
   */
  public static void getPlayerMana() {
    // get the player with the given playerIdx
    int playerIdx = GameActions.getCurrentAction().getPlayerIdx();
    Player player = getPlayer(playerIdx);

    ObjectMapper mapper = new ObjectMapper();

    // create the command ObjectNode and add necessary fields
    ObjectNode commandObjectNode = mapper.createObjectNode();
    commandObjectNode.put("command", GET_PLAYER_MANA);
    commandObjectNode.put("playerIdx", playerIdx);
    commandObjectNode.put("output", player.getMana());

    // update the output ArrayNode
    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(commandObjectNode));
  }

  /**
   * Put player's environment cards in hand in the output ArrayNode.
   */
  public static void getEnvironmentCardsInHand() {
    // get the player with the given playerIdx
    int playerIdx = GameActions.getCurrentAction().getPlayerIdx();
    Player player = getPlayer(playerIdx);

    ObjectMapper mapper = new ObjectMapper();
    ArrayNode cards = mapper.createArrayNode();

    // create an ObjectNode for each environment card in the player's hand and add it to the cards
    // ArrayNode
    ArrayList<Card> playerHand = player.getCardsInHand();
    for (Card card : playerHand) {
      if (card instanceof Environment) {
        ObjectNode cardObjectNode = createCardOutput(mapper, card);
        cards.add(cardObjectNode);
      }
    }

    // create the command ObjectNode and add necessary fields
    ObjectNode commandObjectNode = mapper.createObjectNode();
    commandObjectNode.put("command", GET_ENVIRONMENT_CARDS_IN_HAND);
    commandObjectNode.put("playerIdx", playerIdx);
    commandObjectNode.set("output", cards);

    // update the output ArrayNode
    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(commandObjectNode));
  }

  /**
   * Put frozen cards on board in the output ArrayNode.
   */
  public static void getFrozenCardsOnTable() {
    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();

    ObjectMapper mapper = new ObjectMapper();
    ArrayNode cards = mapper.createArrayNode();

    // create an ObjectNode for each frozen card on the board and add it to the cards ArrayNode
    for (ArrayList<Minion> row : board) {
      for (Minion card : row) {
        if (card.isFrozen()) {
          ObjectNode cardObjectNode = createCardOutput(mapper, card);
          cards.add(cardObjectNode);
        }
      }
    }

    // create the command ObjectNode and add necessary fields
    ObjectNode commandObjectNode = mapper.createObjectNode();
    commandObjectNode.put("command", GET_FROZEN_CARDS_ON_TABLE);
    commandObjectNode.set("output", cards);

    // update the output ArrayNode
    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(commandObjectNode));
  }

  /**
   * Put the total number of games played in the output ArrayNode.
   */
  public static void getTotalGamesPlayed() {
    ObjectMapper mapper = new ObjectMapper();

    // create the command ObjectNode and add necessary fields
    ObjectNode commandObjectNode = mapper.createObjectNode();
    commandObjectNode.put("command", GET_TOTAL_GAMES_PLAYED);
    commandObjectNode.put("output",
        GameEngine.getEngine().getPlayerOneWins() + GameEngine.getEngine().getPlayerTwoWins());

    // update the output ArrayNode
    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(commandObjectNode));
  }

  /**
   * Put the number of player one wins in the output arrayNode.
   */
  public static void getPlayerOneWins() {
    ObjectMapper mapper = new ObjectMapper();

    // create the command ObjectNode and add necessary fields
    ObjectNode commandObjectNode = mapper.createObjectNode();
    commandObjectNode.put("command", GET_PLAYER_ONE_WINS);
    commandObjectNode.put("output", GameEngine.getEngine().getPlayerOneWins());

    // update the output ArrayNode
    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(commandObjectNode));
  }

  /**
   * Put the number of player two wins in the output arrayNode.
   */
  public static void getPlayerTwoWins() {
    ObjectMapper mapper = new ObjectMapper();

    // create the command ObjectNode and add necessary fields
    ObjectNode commandObjectNode = mapper.createObjectNode();
    commandObjectNode.put("command", GET_PLAYER_TWO_WINS);
    commandObjectNode.put("output", GameEngine.getEngine().getPlayerTwoWins());

    // update the output ArrayNode
    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(commandObjectNode));
  }


  /**
   * @param mapper the ObjectMapper that will create the ObjectNode
   * @param card the card that will be mapped into an ObjectNode
   * @return the ObjectNode containing the card's necessary fields
   */
  private static ObjectNode createCardOutput(final ObjectMapper mapper, final Card card) {
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
    if (card.getAttackDamage() >= 0) {
      outputObjectNode.put("attackDamage", card.getAttackDamage());
    }
    if (card.getHealth() >= 0) {
      outputObjectNode.put("health", card.getHealth());
    }
    return outputObjectNode;
  }


  /**
   * @param playerIdx the player's index
   * @return the player with the given index
   */
  private static Player getPlayer(final int playerIdx) {
    Player player;
    if (playerIdx == 1) {
      player = GameEngine.getEngine().getPlayerOne();
    } else {
      player = GameEngine.getEngine().getPlayerTwo();
    }
    return player;
  }
}
