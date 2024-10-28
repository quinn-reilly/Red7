package cs3500.solored.controller;

import java.util.List;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;

/**
 * Controller for a game of RedSeven.
 *
 * <p>The goal of the game is to use all the cards in the deck
 * while ensuring exactly one palette is winning each round.
 *
 * <p>The Controllers job is to get input from a readable
 * object, direct a model class to handle data and a view class
 * to display data
 */
public interface RedGameController {
  /**
   * Play a new game of RedSeven.
   * NOTE: Palette and hand indices are 1s based.
   *
   * @param <C>         must extend type Card, and will be the
   *                    type of card that the game is played with
   * @param model       a model of the game RedSeven that is to be
   *                    played with
   * @param deck        a list of type C, which is the list of cards
   *                    that will be played with.
   *                    The deck must have no duplicate or null cards.
   * @param shuffle     true if the given deck should be shuffled,
   *                    false otherwise
   * @param numPalettes the number of palettes that will be
   *                    created and played with,  cannot be < 2
   * @param handSize    the max size of the hand for gameplay,
   *                    cannot be < 1
   * @throws IllegalArgumentException if model is null
   * @throws IllegalStateException    if controller is unable to successfully
   *                                  receive input or transmit output
   * @throws IllegalArgumentException if game cannot be started
   */
  <C extends Card> void playGame(RedGameModel<C> model,
                                 List<C> deck,
                                 boolean shuffle,
                                 int numPalettes,
                                 int handSize);

}
