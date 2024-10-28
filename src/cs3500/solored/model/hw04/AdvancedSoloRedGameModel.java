package cs3500.solored.model.hw04;


import java.util.Random;

import cs3500.solored.model.hw02.PlayCard;

/**
 * The class for a model of an advanced, solo game of RedSeven.
 *
 * <p>The Model's job is to handle data given to it,
 * and prevent the possibility of Illegal Game States
 * or Arguments.
 * An Advanced game has an altered set of rule than a basic game, which
 * are as follows:
 * <ul>
 *   <li>A player may only draw one card after playing to a palette.</li>
 *   <li>If player plays to canvas, and the canvas card's number is strictly
 * greater than the length of the current winning palette, the player may draw two
 * cards after playing to a palette.</li>
 *   <li>The allowed number of cards to be drawn resets to one after that turn.</li>
 * </ul>
 */
public class AdvancedSoloRedGameModel extends SoloModel {
  private boolean canDrawTwo;
  private boolean firstDraw;
  private int palettesSinceCanvas;

  /**
   * Convenience constructor for a SoloRedGameModel,
   * that does not take any arguments.
   **/
  public AdvancedSoloRedGameModel() {
    this(new Random());
  }

  /**
   * Constructor for a SoloRedGameModel
   * that takes in a random object for testing
   * random behaviours.
   *
   * @param rand a random object
   * @throws IllegalArgumentException if the random is null
   **/
  public AdvancedSoloRedGameModel(Random rand) {
    if (rand == null) {
      throw new IllegalArgumentException("Random cannot be null");
    }
    this.rand = rand;
    gameStartedHuh = false;
    canvas = PlayCard.makeInitialCanvasCard();
    previousWinningPaletteIndex = -1;
    curWinningPaletteIndex = -2;
    canDrawTwo = false;
    firstDraw = true;
    palettesSinceCanvas = -1;
  }

  /**
   * Draws one card after player plays to palette.
   * If player plays to canvas, and canvas number is strictly
   * greater than the number of cards in the current winning palette,
   * the player may draw two cards after playing to a palette.
   * The number of cards able to be drawn resets to one on the
   * next turn.
   */
  @Override
  public void drawForHand() {
    if (!gameStartedHuh || isGameOver()) {
      throw new IllegalStateException("Cannot play when game is not active");
    } else if (firstDraw) {
      while (!deck.isEmpty() && hand.size() < maxHandSize) {
        PlayCard toHand = deck.remove(0);
        hand.add(toHand);
      }
      firstDraw = false;
    } else {
      if (canDrawTwo && changedCanvasThisTurnHuh && palettesSinceCanvas == 1) {
        if (!deck.isEmpty()) {
          PlayCard toHand = deck.remove(0);
          hand.add(toHand);
        }
        if (!deck.isEmpty()) {
          PlayCard toHand = deck.remove(0);
          hand.add(toHand);
        }
      } else {
        PlayCard toHand = deck.remove(0);
        hand.add(toHand);
      }
    }
    changedCanvasThisTurnHuh = false;
    canDrawTwo = false;
  }

  @Override
  public void playToCanvas(int cardIdxInHand) {
    canvasExceptions(cardIdxInHand);
    canvas = hand.remove(cardIdxInHand);
    changedCanvasThisTurnHuh = true;
    if (palettes.get(winningPaletteIndex()).size() < canvas.getValue()) {
      canDrawTwo = true;
    }
    palettesSinceCanvas = 0;
  }

  @Override
  public void playToPalette(int paletteIdx, int cardIdxInHand) {
    super.playToPalette(paletteIdx, cardIdxInHand);
    palettesSinceCanvas += 1;
  }
}

