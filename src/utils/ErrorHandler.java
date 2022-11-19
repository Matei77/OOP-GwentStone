package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game_engine.GameActions;
import game_engine.GameEngine;

import java.util.List;

import static utils.Constants.*;

public class ErrorHandler {
  public static void ThrowError(String message) {
    ActionsInput action = GameActions.getCurrentAction();

    ObjectMapper mapper = new ObjectMapper();
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
    }
    errorObjectNode.put("error", message);

    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(errorObjectNode));
  }
}
