package cs3500.solored.model.hw02;

/**
 * An enum for types of colors on cards from the game RedSeven.
 * Each color has an associated string format and weight
 */

public enum Color {
  R("R", 4),
  O("O", 3),
  B("B", 2),
  I("I", 1),
  V("V", 0);

  private final int weight;
  private final String colorAsString;

  Color(String c, int w) {
    weight = w;
    colorAsString = c;
  }

  /**
   * Returns the weight associated with a given color
   * in the game RedSeven.
   * @return weight of this color
   */
  public int getWeight() {
    return weight;
  }

  /**
   * Returns the given color as a String of the first
   * letter of the associated color in upperCase.
   * @return uppercase letter of the beginning of this color
   */
  public String getColorAsString() {
    return colorAsString;
  }
}
