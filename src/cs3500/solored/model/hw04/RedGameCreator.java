package cs3500.solored.model.hw04;

import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * The abstract class for a model of a solo game of RedSeven.
 *
 * <p>The Model's job is to handle data given to it,
 * and prevent the possibility of Illegal Game States
 * or Arguments.
 */
public class RedGameCreator {

  /**
   * Creates and returns a new RedGameModel of the appropriate
   * type given in the parameters.
   * @param gt a GameType, which refers to the rule set for the game.
   * @return a new RedGameModel of the appropriate type given in the parameters.
   */
  public static RedGameModel createGame(GameType gt) {
    if (gt.equals(GameType.BASIC)) {
      return new SoloRedGameModel();
    } else {
      return new AdvancedSoloRedGameModel();
    }

  }

  /**
   * An enumeration for the types of SoloRedSeven
   * games that can be created. Basic and advanced
   * refer to the type of rules that the game
   * will be played with.
   */
  public enum GameType {
    BASIC, ADVANCED
  }
}
