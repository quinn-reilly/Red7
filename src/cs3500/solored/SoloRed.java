package cs3500.solored;

import java.io.InputStreamReader;

import cs3500.solored.controller.RedGameController;
import cs3500.solored.controller.SoloRedTextController;
import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw04.RedGameCreator;

/**
 * The class to run a game of SoloRedSeven.
 */
public final class SoloRed {

  /**
   * Main function.
   *
   * @param args inline commands for the main function
   */
  public static void main(String[] args) {
    String command;
    if (args.length > 0) {
      command = args[0];
    } else {
      command = "basic";
    }
    int palettes = 4;
    if (args.length > 1) {
      try {
        palettes = Integer.parseInt(args[1]);
      } catch (NumberFormatException e) {
        System.exit(1);
      }
    }
    int hand = 7;
    if (args.length > 2) {
      try {
        hand = Integer.parseInt(args[2]);
      } catch (NumberFormatException e) {
        System.exit(1);
      }
    }
    RedGameModel<Card> model;
    if (command.equals("advanced")) {
      model = RedGameCreator.createGame(RedGameCreator.GameType.ADVANCED);
    } else if (command.equals("basic")) {
      model = RedGameCreator.createGame(RedGameCreator.GameType.BASIC);
    } else {
      throw new IllegalArgumentException("nonsense game type.");
    }
    RedGameController controller = new SoloRedTextController(
            new InputStreamReader(System.in), System.out);
    try {
      controller.playGame(model, model.getAllCards(), true, palettes, hand);
    } catch (IllegalStateException | IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }
}
