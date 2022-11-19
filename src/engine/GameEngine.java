package engine;

import cardtypes.Minion;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.GameInput;
import fileio.Input;
import player.Player;

import java.util.ArrayList;

import static utils.Constants.PLAYER_ONE_BACK_ROW;
import static utils.Constants.PLAYER_ONE_FRONT_ROW;
import static utils.Constants.PLAYER_ONE_TURN;
import static utils.Constants.PLAYER_TWO_BACK_ROW;
import static utils.Constants.PLAYER_TWO_FRONT_ROW;

public final class GameEngine {
  private static GameEngine instance = null;
  private GameEngine() { }

  /**
   * @return the GameEngine instance
   */
  public static GameEngine getEngine() {
    if (instance == null) {
      instance = new GameEngine();
    }
    return instance;
  }

  private int currentRoundNr;
  private int startingPlayer;
  private int playerTurn;
  private int playerOneWins;
  private int playerTwoWins;
  private int shuffleSeed;
  private Input inputData;
  private ArrayNode output;

  private final Player playerOne = new Player(PLAYER_ONE_FRONT_ROW, PLAYER_ONE_BACK_ROW);
  private final Player playerTwo = new Player(PLAYER_TWO_FRONT_ROW, PLAYER_TWO_BACK_ROW);

  private ArrayList<ArrayList<Minion>> board;

  /**
   * Play all games from input.
   */
  public void runEngine() {
    GameActions.preparePlayers();
    ArrayList<GameInput> games = inputData.getGames();
    for (GameInput game : games) {
      GameActions.startGame(game);
      GameActions.startRound();
      GameActions.executeActions(game.getActions());
    }
  }


  /**
   * @return the player whose turn is active
   */
  public static Player getCurrentPlayer() {
    if (getEngine().getPlayerTurn() == PLAYER_ONE_TURN) {
      return getEngine().getPlayerOne();
    } else {
      return getEngine().getPlayerTwo();
    }
  }


  public int getCurrentRoundNr() {
    return currentRoundNr;
  }

  public void setCurrentRoundNr(final int currentRoundNr) {
    this.currentRoundNr = currentRoundNr;
  }

  public int getStartingPlayer() {
    return startingPlayer;
  }

  public void setStartingPlayer(final int startingPlayer) {
    this.startingPlayer = startingPlayer;
  }

  public int getPlayerTurn() {
    return playerTurn;
  }

  public void setPlayerTurn(final int playerTurn) {
    this.playerTurn = playerTurn;
  }

  public int getShuffleSeed() {
    return shuffleSeed;
  }

  public void setShuffleSeed(final int shuffleSeed) {
    this.shuffleSeed = shuffleSeed;
  }

  public Input getInputData() {
    return inputData;
  }

  public void setInputData(final Input inputData) {
    this.inputData = inputData;
  }

  public ArrayNode getOutput() {
    return output;
  }

  public void setOutput(final ArrayNode output) {
    this.output = output;
  }

  public Player getPlayerOne() {
    return playerOne;
  }

  public Player getPlayerTwo() {
    return playerTwo;
  }

  public ArrayList<ArrayList<Minion>> getBoard() {
    return board;
  }

  public void setBoard(final ArrayList<ArrayList<Minion>> board) {
    this.board = board;
  }

  public int getPlayerOneWins() {
    return playerOneWins;
  }

  public void setPlayerOneWins(final int playerOneWins) {
    this.playerOneWins = playerOneWins;
  }

  public int getPlayerTwoWins() {
    return playerTwoWins;
  }

  public void setPlayerTwoWins(final int playerTwoWins) {
    this.playerTwoWins = playerTwoWins;
  }
}
