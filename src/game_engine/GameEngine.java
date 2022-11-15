package game_engine;

import card_types.Minion;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.GameInput;
import fileio.Input;
import player.Player;

import java.util.ArrayList;

import static utils.Constants.*;

public class GameEngine {
  private static GameEngine instance = null;
  private GameEngine() {}
  public static GameEngine getEngine() {
    if (instance == null) {
      instance = new GameEngine();
    }
    return instance;
  }

  private int currentGameNr = 0;
  private int currentRoundNr = 0;
  private int startingPlayer;
  private int playerTurn;
  private int shuffleSeed;
  private Input inputData;
  private ArrayNode output;

  private Player playerOne = new Player();
  private Player playerTwo = new Player();

  private ArrayList<ArrayList<Minion>> table = new ArrayList<ArrayList<Minion>>(ROWS_MAX_INDEX + 1);


  public void runEngine() {
    GameActions.preparePlayers();
    ArrayList<GameInput> games = inputData.getGames();
    for (GameInput game : games) {
      GameActions.startGame(game);
      GameActions.startRound();
      GameActions.executeActions(game.getActions());
      currentGameNr++;
    }
  }


  public int getCurrentGameNr() {
    return currentGameNr;
  }

  public void setCurrentGameNr(int currentGameNr) {
    this.currentGameNr = currentGameNr;
  }

  public int getCurrentRoundNr() {
    return currentRoundNr;
  }

  public void setCurrentRoundNr(int currentRoundNr) {
    this.currentRoundNr = currentRoundNr;
  }

  public int getStartingPlayer() {
    return startingPlayer;
  }

  public void setStartingPlayer(int startingPlayer) {
    this.startingPlayer = startingPlayer;
  }

  public int getPlayerTurn() {
    return playerTurn;
  }

  public void setPlayerTurn(int playerTurn) {
    this.playerTurn = playerTurn;
  }

  public int getShuffleSeed() {
    return shuffleSeed;
  }

  public void setShuffleSeed(int shuffleSeed) {
    this.shuffleSeed = shuffleSeed;
  }

  public Input getInputData() {
    return inputData;
  }

  public void setInputData(Input inputData) {
    this.inputData = inputData;
  }

  public ArrayNode getOutput() {
    return output;
  }

  public void setOutput(ArrayNode output) {
    this.output = output;
  }

  public Player getPlayerOne() {
    return playerOne;
  }

  public void setPlayerOne(Player playerOne) {
    this.playerOne = playerOne;
  }

  public Player getPlayerTwo() {
    return playerTwo;
  }

  public void setPlayerTwo(Player playerTwo) {
    this.playerTwo = playerTwo;
  }

  public ArrayList<ArrayList<Minion>> getTable() {
    return table;
  }

  public void setTable(ArrayList<ArrayList<Minion>> table) {
    this.table = table;
  }
}
