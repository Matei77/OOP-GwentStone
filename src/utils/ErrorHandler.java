package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import engine.GameActions;
import engine.GameEngine;

import java.util.List;

import static utils.Constants.CARD_USES_ABILITY;
import static utils.Constants.CARD_USES_ATTACK;
import static utils.Constants.PLACE_CARD;
import static utils.Constants.USE_ATTACK_HERO;
import static utils.Constants.USE_ENVIRONMENT_CARD;
import static utils.Constants.USE_HERO_ABILITY;

public final class ErrorHandler {
  private ErrorHandler() { }

  /**
   * Create error output for a command.
   * @param message the message shown in the error output
   */
  public static void throwError(final String message) {
    // get the current action
    ActionsInput action = GameActions.getCurrentAction();

    ObjectMapper mapper = new ObjectMapper();

    // create the error ObjectNode and add necessary fields
    ObjectNode errorObjectNode = mapper.createObjectNode();

    errorObjectNode.put("command", action.getCommand());

    switch (action.getCommand()) {
      case PLACE_CARD:
        errorObjectNode.put("handIdx", 0);
        break;
      case USE_ENVIRONMENT_CARD:
        errorObjectNode.put("handIdx", action.getHandIdx());
        errorObjectNode.put("affectedRow", action.getAffectedRow());
        break;
      case USE_HERO_ABILITY:
        errorObjectNode.put("affectedRow", action.getAffectedRow());
        break;
      case CARD_USES_ATTACK:
      case CARD_USES_ABILITY:
        ObjectNode cardAttacker = mapper.createObjectNode();
        cardAttacker.put("x", action.getCardAttacker().getX());
        cardAttacker.put("y", action.getCardAttacker().getY());
        ObjectNode cardAttacked = mapper.createObjectNode();
        cardAttacked.put("x", action.getCardAttacked().getX());
        cardAttacked.put("y", action.getCardAttacked().getY());
        errorObjectNode.set("cardAttacker", cardAttacker);
        errorObjectNode.set("cardAttacked", cardAttacked);
        break;
      case USE_ATTACK_HERO:
        cardAttacker = mapper.createObjectNode();
        cardAttacker.put("x", action.getCardAttacker().getX());
        cardAttacker.put("y", action.getCardAttacker().getY());
        errorObjectNode.set("cardAttacker", cardAttacker);
        break;
      default:
        break;
    }
    errorObjectNode.put("error", message);

    // update the output ArrayNode
    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(errorObjectNode));
  }
}
