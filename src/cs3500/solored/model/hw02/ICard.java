package cs3500.solored.model.hw02;

/**
 * Additional Behaviors for a Card in the Game of RedSeven.
 */
public interface ICard extends Card {

  /**
   * Returns a copy of this ICard, in which
   * modification of the copy will not modify this card.
   *
   * @return a deep copy of this ICard
   */
  ICard copy();

  /**
   * Returns the Color of this card, which can be
   * any enumeration of the type Color.
   *
   * @return the Color of this card
   */
  Color getColor();

  /**
   * Returns the value of this card, from 1-7 inclusive.
   *
   * @return the value of this card
   */
  int getValue();

  /**
   * Returns true iff this card has a higher value that
   * the given card according to the Red Canvas Rules,
   * false otherwise.
   *
   * @param otherICard the ICard to be compared to this card
   * @return true iff this ICard is higher in value than the given, false otherwise
   */
  boolean isHigherThan(ICard otherICard);

}
