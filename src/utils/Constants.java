package utils;

public final class Constants {
  private Constants() {}
  public static final int MAX_MANA_PER_ROUND = 10;
  public static final int ROWS_MAX_INDEX = 3;
  public static final int MAX_CARDS_PER_ROW = 5;
  public static final int FRONT_ROW = 1;
  public static final int BACK_ROW = 2;
  public static final int PLAYER_ONE_FRONT_ROW = 2;
  public static final int PLAYER_ONE_BACK_ROW = 3;
  public static final int PLAYER_TWO_FRONT_ROW = 1;
  public static final int PLAYER_TWO_BACK_ROW = 0;
  public static final boolean TANK = true;
  public static final boolean NOT_TANK = false;

  public static final int PLAYER_ONE_TURN = 1;
  public static final int PLAYER_TWO_TURN = 2;

  public static final String MINION = "Minion";
  public static final String ENVIRONMENT = "Environment";
  public static final String HERO = "Hero";
  public static final String SENTINEL = "Sentinel";
  public static final String BERSERKER = "Berserker";
  public static final String GOLIATH = "Goliath";
  public static final String WARDEN = "Warden";

  public static final String THE_RIPPER = "The Ripper";
  public static final String MIRAJ = "Miraj";
  public static final String THE_CURSED_ONE = "The Cursed One";
  public static final String DISCIPLE = "Disciple";

  public static final String FIRESTORM = "Firestorm";
  public static final String WINTERFELL = "Winterfell";
  public static final String HEART_HOUND = "Heart Hound";

  public static final String LORD_ROYCE = "Lord Royce";
  public static final String EMPRESS_THORINA = "Empress Thorina";
  public static final String KING_MUDFACE = "King Mudface";
  public static final String GENERAL_KOCIORAW = "General Kocioraw";


  public static final String END_PLAYER_TURN = "endPlayerTurn";
  public static final String PLACE_CARD = "placeCard";
  public static final String CARD_USES_ATTACK = "cardUsesAttack";
  public static final String CARD_USES_ABILITY = "cardUsesAbility";
  public static final String USE_ATTACK_HERO = "useAttackHero";
  public static final String USE_HERO_ABILITY = "useHeroAbility";
  public static final String USE_ENVIRONMENT_CARD = "useEnvironmentCard";

  public static final String GET_CARDS_IN_HAND = "getCardsInHand";
  public static final String GET_PLAYER_DECK = "getPlayerDeck";
  public static final String GET_CARDS_ON_TABLE = "getCardsOnTable";
  public static final String GET_PLAYER_TURN = "getPlayerTurn";
  public static final String GET_PLAYER_HERO = "getPlayerHero";
  public static final String GET_CARD_AT_POSITION = "getCardAtPosition";
  public static final String GET_PLAYER_MANA = "getPlayerMana";
  public static final String GET_ENVIRONMENT_CARDS_IN_HAND = "getEnvironmentCardsInHand";
  public static final String GET_FROZEN_CARDS_ON_TABLE = "getFrozenCardsOnTable";

  public static final String GET_TOTAL_GAMES_PLAYED = "getTotalGamesPlayed";
  public static final String GET_PLAYER_ONE_WINS = "getPlayerOneWins";
  public static final String GET_PLAYER_TWO_WINS = "getPlayerTwoWins";
}
