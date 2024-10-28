package cs3500.solored.model.hw02;

/**
 * The class for a PlayCard, a type of card that can be played
 * with the game RedSeven.
 **/
public final class PlayCard implements ICard {
  private final Color color;
  private final int value;

  /**
   * The Constructor for a PlayCard.
   *
   * @param color the enumerated color of this card
   * @param value the value of this card
   * @throws IllegalArgumentException for values
   *     not between 1-7 inclusive
   **/
  public PlayCard(Color color, int value) {
    if (value < 1 || value > 7) {
      throw new IllegalArgumentException("Values of cards must be included between 1-7");
    }
    this.color = color;
    this.value = value;
  }

  private PlayCard() {
    this.color = Color.R;
    this.value = -1;
  }

  /**
   * Creates the initial red card for the Canvas.
   * @return an ICard with the Color.Red and value of -1.
   */
  public static PlayCard makeInitialCanvasCard() {
    return new PlayCard();
  }

  @Override
  public PlayCard copy() {
    return new PlayCard(this.color, this.value);
  }

  @Override
  public Color getColor() {
    return color;
  }

  @Override
  public int getValue() {
    return value;
  }

  @Override
  public boolean isHigherThan(ICard other) {
    if (value > other.getValue()) {
      return true;
    } else if (value == other.getValue()) {
      if (color.getWeight() > other.getColor().getWeight()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return color.getColorAsString() + value;
  }

  /**
   * The equals method that returns true iff the given
   * object is a playCard with equal value and
   * color to the parameter, false otherwise.
   * @param o the object to compare to
   * @return true iff this PlayCard is equal
   *     the object given, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof PlayCard) {
      return value == ((PlayCard) o).getValue() && color.equals(((PlayCard) o).getColor());
    } else {
      return false;
    }
  }

  /**
   * The hashcode method for a PlayCard.
   * Returns a unique hashCode for this PlayCard.
   * Equal objects will have equal hashcode.
   * @return unique hashCode for this PlayCard
   */
  @Override
  public int hashCode() {
    return (int) Math.pow(value, color.getWeight());
  }

}
