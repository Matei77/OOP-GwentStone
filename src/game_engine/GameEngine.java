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

  private Player playerOne = new Player(PLAYER_ONE_FRONT_ROW, PLAYER_ONE_BACK_ROW);
  private Player playerTwo = new Player(PLAYER_TWO_FRONT_ROW, PLAYER_TWO_BACK_ROW);

  private ArrayList<ArrayList<Minion>> board;

  public void runEngine() {
    GameActions.preparePlayers();
    ArrayList<GameInput> games = inputData.getGames();
    for (GameInput game : games) {
      prepareBoard();
      GameActions.startGame(game);
      GameActions.startRound();
      GameActions.executeActions(game.getActions());
      currentGameNr++;
    }
  }

  private void prepareBoard() {
    ArrayList<ArrayList<Minion>> board = new ArrayList<ArrayList<Minion>>(MAX_ROW_INDEX + 1);
    ArrayList<Minion> row0 = new ArrayList<>(MAX_CARDS_PER_ROW);
    ArrayList<Minion> row1 = new ArrayList<>(MAX_CARDS_PER_ROW);
    ArrayList<Minion> row2 = new ArrayList<>(MAX_CARDS_PER_ROW);
    ArrayList<Minion> row3 = new ArrayList<>(MAX_CARDS_PER_ROW);
    board.add(row0);
    board.add(row1);
    board.add(row2);
    board.add(row3);
    GameEngine.getEngine().setBoard(board);
  }

  public static Player getCurrentPlayer() {
    if (getEngine().getPlayerTurn() == PLAYER_ONE_TURN)
      return getEngine().getPlayerOne();
    else
      return getEngine().getPlayerTwo();
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

  public ArrayList<ArrayList<Minion>> getBoard() {
    return board;
  }

  public void setBoard(ArrayList<ArrayList<Minion>> board) {
    this.board = board;
  }
}
