package cs3500.solored.model.hw02;

import java.util.Random;

import cs3500.solored.model.hw04.SoloModel;


/**
 * The class for a model of a solo game of RedSeven.
 *
 * <p>The Model's job is to handle data given to it,
 * and prevent the possibility of Illegal Game States
 * or Arguments.
 */
public class SoloRedGameModel extends SoloModel {
  /**
   * Convenience constructor for a SoloRedGameModel,
   * that does not take any arguments.
   */
  public SoloRedGameModel() {
    this(new Random());
  }

  /**
   * Constructor for a SoloRedGameModel
   * that takes in a random object for testing
   * random behaviours.
   *
   * @param rand a random object
   * @throws IllegalArgumentException if the random is null
   */
  public SoloRedGameModel(Random rand) {
    if (rand == null) {
      throw new IllegalArgumentException("Random cannot be null");
    }
    this.rand = rand;
    gameStartedHuh = false;
    canvas = PlayCard.makeInitialCanvasCard();
    previousWinningPaletteIndex = -1;
    curWinningPaletteIndex = -2;
  }


  @Override
  public void drawForHand() {
    if (!gameStartedHuh || isGameOver()) {
      throw new IllegalStateException("Cannot play when game is not active");
    } else {
      while (!deck.isEmpty() && hand.size() < maxHandSize) {
        PlayCard toHand = deck.remove(0);
        hand.add(toHand);
      }
    }
    changedCanvasThisTurnHuh = false;
  }

}
