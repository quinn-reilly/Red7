package cs3500.solored.view.hw02;

import java.io.IOException;

import cs3500.solored.model.hw02.RedGameModel;

/**
 * The class for a Text view of a solo game of RedSeven.
 */
public class SoloRedGameTextView implements RedGameView {
  private final RedGameModel<?> model;
  Appendable appendable;

  /**
   * A constructor for a model that does not take in an Appendable
   * object, only a model that should be displayed.
   *
   * @param model the model that has values that will be displayed
   */
  public SoloRedGameTextView(RedGameModel<?> model) {
    this(model, System.out);
  }

  /**
   * A constructor for a model that takes in an Appendable
   * object and  a model that should be displayed.
   *
   * @param model      the model that has values that will be displayed
   * @param appendable an object that implements the Appendable Interface,
   *                   to where the results of methods in this class will be sent.
   * @throws IllegalArgumentException when appendable is null
   */
  public SoloRedGameTextView(RedGameModel<?> model, Appendable appendable) {
    this.model = model;
    if (appendable == null) {
      throw new IllegalArgumentException("Appendable cannot be null");
    }
    this.appendable = appendable;
  }

  /**
   * Creates a String with state of the game.
   * This rendering includes
   * <ul>
   *   <li>The color of the card on the Canvas</li>
   *   <li>Each palette from P1 to Pn, where n is the number of palettes, where each palette
   *   has all of its card printed with one space between them</li>
   *   <li>A greater than symbol indicating the winning palette</li>
   *   <li>The hand, where all cards are printed with one space between them</li>
   * </ul>
   * An example below for a 4-palette, 7-hand game in-progress
   * Canvas: R
   * P1: R6 B1
   * > P2: R7
   * P3: V1
   * P4: I2
   * Hand: V2 I3 R1 O2 G6 R5 O1
   *
   * @return String in the format listed above
   */
  @Override
  public String toString() {
    StringBuilder view = new StringBuilder();
    view.append("Canvas: ").append(model.getCanvas().toString().charAt(0));
    view.append("\n");

    for (int pal = 0; pal < model.numPalettes(); pal++) {
      if (pal == model.winningPaletteIndex()) {
        view.append("> ");
      }
      view.append("P").append(pal + 1).append(": ");
      if (!model.getPalette(pal).isEmpty()) {
        for (int card = 0; card < model.getPalette(pal).size() - 1; card++) {
          view.append(model.getPalette(pal).get(card).toString()).append(" ");
        }
        view.append(model.getPalette(pal).get(model.getPalette(pal).size() - 1).toString());
      }
      view.append("\n");
    }

    view.append("Hand: ");
    if (!model.getHand().isEmpty()) {
      view.append(model.getHand().get(0));
    }
    if (model.getHand().size() > 1) {
      for (int hand = 1; hand < model.getHand().size(); hand++) {
        view.append(" ").append(model.getHand().get(hand));
      }

    }
    return view.toString();
  }

  @Override
  public void render() throws IOException {
    appendable.append(this.toString());

  }

}
