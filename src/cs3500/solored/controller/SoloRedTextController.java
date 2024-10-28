package cs3500.solored.controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.view.hw02.RedGameView;
import cs3500.solored.view.hw02.SoloRedGameTextView;

/**
 * Controller for a solo game of RedSeven.
 * This class utilizes a model and a view class,
 * ordering and enforcing the proper sequence of events
 * for a game.
 */
public class SoloRedTextController implements RedGameController {
  private final Appendable appendable;
  private final Scanner scanner;
  private RedGameModel<?> model;
  private RedGameView view;
  private boolean gameQuit;

  /**
   * Constructor for a SoloRedTextController
   * that takes in a readable and appendable.
   *
   * @param rd an object that implements the Readable Interface,
   *           from which input is scanned
   * @param ap an object that implements the Appendable Interface,
   *           to which values will be sent to
   * @throws IllegalArgumentException if either argument is null
   **/
  public SoloRedTextController(Readable rd, Appendable ap) throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Inputs cannot be null");
    }
    appendable = ap;
    scanner = new Scanner(rd);

  }

  @Override
  public <C extends Card> void playGame(RedGameModel<C> model, List<C> deck, boolean shuffle,
                                        int numPalettes, int handSize) {
    if (model == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    this.model = model;
    try {
      model.startGame(deck, shuffle, numPalettes, handSize);
    } catch (IllegalStateException e) {
      throw new IllegalArgumentException("Issue starting game");
    }
    view = new SoloRedGameTextView(model, appendable);
    baseView();
    if (scanner.hasNext()) {
      while (!model.isGameOver() && !gameQuit) {
        // baseView();
        while (!model.isGameOver()) {
          if (!continueGame()) {
            break;
          }
        }
      }
    }
    endGame();
  }

  private boolean continueGame() {
    if (scanner.hasNext()) {
      String input = scanner.next();
      switch (input) {
        case "palette":
          int palNum = readInputForNum();
          if (palNum == -1) {
            quitGame();
            return false;
          }
          int handIdx = readInputForNum();
          if (handIdx == -1) {
            quitGame();
            return false;
          }
          tryPlayToPalette(palNum - 1, handIdx - 1);
          break;
        case "canvas":
          int canvasHandIdx = readInputForNum();
          if (canvasHandIdx == -1) {
            quitGame();
            return false;
          }
          tryPlayToCanvas(canvasHandIdx - 1);
          break;
        case "q":
        case "Q":
          quitGame();
          return false;
        default:
          invalidCommand();
          break;
      }
    } else {
      quitGame();
    }
    return true;
  }

  // Helper method that gives feedback to appendable object,
  // as well as checking issues with input/output.
  private void invalidCommand() {
    try {
      appendable.append("Invalid command. Try again. "
              + "Must play to canvas, palette, or quit(q, Q)");
      newLine();
      baseView();
    } catch (IOException e) {
      throw new IllegalStateException("Issue with inputs/outputs");
    }
  }

  private void newLine() {
    try {
      appendable.append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Issue with inputs/outputs");
    }
  }

  // Helper method that attempts to play to the canvas with the given
  // input, and sends feedback if unsuccessful
  // NOTE: values have already been changed from 1s index to 0s
  private void tryPlayToCanvas(int canvasHandIdx) {
    try {
      model.playToCanvas(canvasHandIdx);
      try {
        view.render();
        transmitDeck();
      } catch (IOException e) {
        throw new IllegalStateException("Issue with inputs/outputs");
      }
    } catch (IllegalArgumentException | IllegalStateException e) {
      try {
        appendable.append("Invalid move. Try again. ").append(e.getMessage());
        newLine();
        baseView();
      } catch (IOException io) {
        throw new IllegalStateException("Issue with inputs/outputs");
      }
    }
  }

  private void transmitDeck() {
    try {
      newLine();
      appendable.append("Number of cards in deck: ");
      appendable.append(Integer.toString(model.numOfCardsInDeck()));
      newLine();
    } catch (IOException e) {
      throw new IllegalStateException("Issue with inputs/outputs");
    }
  }

  // Helper method that attempts to play to the palette with the given
  // input, and sends feedback if unsuccessful
  // NOTE: values have already been changed from 1s index to 0s
  private void tryPlayToPalette(int palNum, int handIdx) {
    try {
      this.model.playToPalette(palNum, handIdx);

      if (!this.model.isGameOver()) {
        this.model.drawForHand();
      }
      try {
        view.render();
        transmitDeck();
      } catch (IOException e) {
        throw new IllegalStateException("Issue with inputs/outputs");
      }
    } catch (IllegalArgumentException | IllegalStateException e) {
      try {
        appendable.append("Invalid move. Try again. ").append(e.getMessage());
        newLine();
      } catch (IOException io) {
        throw new IllegalStateException("Issue with inputs/outputs");
      }
    }
  }

  // Helper method that constructs the base view for every turn
  private void baseView() {
    try {
      view.render();
      transmitDeck();
    } catch (IOException e) {
      throw new IllegalStateException("Issue with inputs/outputs");
    }
  }

  // helper method for start game
  // returns a natural number that represents the users input
  // or -1 if they entered 'q' or 'Q'
  private int readInputForNum() {
    int inputAsInt;

    String input = scanner.next();
    if (input.equals("q") || input.equals("Q")) {
      return -1;
    }
    try {
      // if this works, they entered a whole number
      inputAsInt = Integer.parseInt(input);
      if (inputAsInt < 0) {
        return readInputForNum();
      } else {
        return inputAsInt;
      }

    } catch (NumberFormatException e) {
      // invalid input, keep searching
      return readInputForNum();
    }
  }

  // Helper method that ends the game, and transmits the appropriate information
  private void endGame() {
    if (model.isGameOver()) {
      try {
        if (model.isGameWon()) {
          appendable.append("Game won.");
          newLine();
          scanner.close();
        } else {
          appendable.append("Game lost.");
          newLine();
          scanner.close();
        }
        view.render();
        transmitDeck();
      } catch (IOException e) {
        throw new IllegalStateException("Issue with inputs/outputs");
      }
    } else if (!gameQuit) {
      quitGame();
    }
  }

  // Helper method that is called when the user quits the game that
  // transmits the appropriate information
  private void quitGame() {
    gameQuit = true;
    scanner.close();
    try {
      appendable.append("Game quit!");
      newLine();
      appendable.append("State of game when quit:");
      newLine();
      view.render();
      transmitDeck();
    } catch (IOException e) {
      throw new IllegalStateException("Issue with inputs/outputs");
    }
  }
}