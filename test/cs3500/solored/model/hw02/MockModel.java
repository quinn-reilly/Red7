package cs3500.solored.model.hw02;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The class for a MockModel of a SoloRedGameModel, does not compute data, just stores inputs.
 */
public class MockModel implements RedGameModel<PlayCard> {
  private Appendable log;

  public MockModel(Appendable log) {
    this.log = log;
  }

  @Override
  public void playToPalette(int paletteIdx, int cardIdxInHand) {
    try {
      log.append(String.format("palIdx = %d, cardIdxInHand = %d\n", paletteIdx, cardIdxInHand));
    } catch (IOException e) {
      //
    }
  }

  @Override
  public void playToCanvas(int cardIdxInHand) {
    try {
      log.append(String.format("cardIdxInHand = %d\n", cardIdxInHand));
    } catch (IOException e) {
      //
    }
  }

  @Override
  public void drawForHand() {
    try {
      log.append(" ");
    } catch (IOException e) {
      //
    }
  }

  @Override
  public void startGame(List<PlayCard> deck, boolean shuffle, int numPalettes, int handSize) {
    try {
      log.append(String.format("shuffle = %b, numPalettes = %d, handSize = %d\n",
              shuffle, numPalettes, handSize));
    } catch (IOException e) {
      //
    }
  }

  @Override
  public int numOfCardsInDeck() {
    return 0;
  }

  @Override
  public int numPalettes() {
    return 0;
  }

  @Override
  public int winningPaletteIndex() {
    return 0;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public boolean isGameWon() {
    return false;
  }

  @Override
  public List<PlayCard> getHand() {
    return new ArrayList<>();
  }

  @Override
  public List<PlayCard> getPalette(int paletteNum) {
    try {
      log.append(String.format("paletteNum = %d\n", paletteNum));
    } catch (IOException e) {
      //
    }
    return new ArrayList<>();
  }

  @Override
  public PlayCard getCanvas() {
    return new PlayCard(Color.R, 1);
  }

  @Override
  public List<PlayCard> getAllCards() {
    return null;
  }
}
