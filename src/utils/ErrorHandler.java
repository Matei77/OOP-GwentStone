package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game_engine.GameActions;
import game_engine.GameEngine;

import java.util.List;

import static utils.Constants.PLACE_CARD;
import static utils.Constants.USE_ENVIRONMENT_CARD;

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
    }
    errorObjectNode.put("error", message);

    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(errorObjectNode));
  }
}
