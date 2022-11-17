package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game_engine.GameEngine;

import java.util.List;

import static utils.Constants.PLACE_CARD;

public class ErrorHandler {
  public static void ThrowError(ActionsInput action, String message) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode errorObjectNode = mapper.createObjectNode();
    errorObjectNode.put("command", action.getCommand());
    switch (action.getCommand()) {
      case PLACE_CARD:
        errorObjectNode.put("handIdx", 0);
        errorObjectNode.put("error", message);
        break;
    }
    ArrayNode output = GameEngine.getEngine().getOutput();
    output.addAll(List.of(errorObjectNode));
  }
}
