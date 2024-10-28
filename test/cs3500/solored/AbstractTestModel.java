package cs3500.solored;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs3500.solored.model.hw02.Color;
import cs3500.solored.model.hw02.PlayCard;
import cs3500.solored.model.hw02.RedGameModel;

/**
 * The test class that holds common tests between instantiations
 * of SoloRedGameModel.
 */
public abstract class AbstractTestModel {
  // Random objects
  Random rand1;

  // Decks
  List<PlayCard> allCards;
  List<PlayCard> deck2;
  List<PlayCard> deck3;
  List<PlayCard> deck4;

  // Solo game models
  RedGameModel<PlayCard> gameNotStarted;
  RedGameModel<PlayCard> baseSetup;
  RedGameModel<PlayCard> gameOverModel;
  RedGameModel<PlayCard> testShuffle;
  RedGameModel<PlayCard> redBehavior;
  RedGameModel<PlayCard> orangeBehavior;
  RedGameModel<PlayCard> blueBehavior;
  RedGameModel<PlayCard> indigoBehavior;
  RedGameModel<PlayCard> violetBehavior;

  // Initial circumstances that are run before each test
  protected void inits() {
    // Initialize random object for testing
    this.rand1 = new Random(1);

    // Initialize decks
    deck2 = new ArrayList<>();
    deck2.add(new PlayCard(Color.R, 3));
    deck2.add(new PlayCard(Color.O, 4));
    deck2.add(new PlayCard(Color.B, 7));
    deck2.add(new PlayCard(Color.O, 2));
    deck2.add(new PlayCard(Color.I, 7));

    deck3 = new ArrayList<>(List.of(
            new PlayCard(Color.R, 3),
            new PlayCard(Color.O, 4),
            new PlayCard(Color.I, 2),
            new PlayCard(Color.V, 4),
            new PlayCard(Color.B, 5),
            new PlayCard(Color.O, 2)));

    deck4 = new ArrayList<>(List.of(
            new PlayCard(Color.R, 1),
            new PlayCard(Color.O, 2),
            new PlayCard(Color.I, 3),
            new PlayCard(Color.I, 1),
            new PlayCard(Color.V, 4),
            new PlayCard(Color.B, 5),
            new PlayCard(Color.R, 2),
            new PlayCard(Color.V, 3),
            new PlayCard(Color.O, 4),
            new PlayCard(Color.B, 6),
            new PlayCard(Color.O, 5)));

  }

  @Test
  public void testShuffle() {
    testShuffle.startGame(deck3, true, 3, 3);
    Assert.assertEquals(testShuffle.getPalette(0),
            List.of(new PlayCard(Color.V, 4)));
    Assert.assertEquals(testShuffle.getPalette(1),
            List.of(new PlayCard(Color.B, 5)));
    Assert.assertEquals(testShuffle.getPalette(2),
            List.of(new PlayCard(Color.O, 4)));
    Assert.assertEquals(testShuffle.getHand(),
            List.of(new PlayCard(Color.R, 3),
                    new PlayCard(Color.I, 2),
                    new PlayCard(Color.O, 2)));
  }

  // Tests for startGame() expected functionality
  @Test
  public void testStartGame() {
    Assert.assertThrows(IllegalStateException.class, () ->
            baseSetup.startGame(deck2, false, 2, 2));
    Assert.assertThrows(IllegalStateException.class, () ->
            gameOverModel.startGame(deck2, false, 2, 2));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            gameNotStarted.startGame(allCards, false, 1, 2));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            gameNotStarted.startGame(allCards, false, 3, 0));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            gameNotStarted.startGame(null, false, 1, 2));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            gameNotStarted.startGame(deck2, false, 9, 2));
    deck2.add(null);
    Assert.assertThrows(IllegalArgumentException.class, () ->
            gameNotStarted.startGame(deck2, false, 1, 2));
    deck2.remove(5);
    deck2.add(new PlayCard(Color.R, 3));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            gameNotStarted.startGame(deck2, false, 1, 2));
    gameNotStarted.startGame(allCards, false, 2, 3);
    allCards.remove(0);
    allCards.remove(0);
    Assert.assertEquals(allCards.size(), 33);
    Assert.assertTrue(gameNotStarted.getPalette(0)
            .contains(new PlayCard(Color.R, 1)));
  }

  @Test
  public void playToTestRed() {
    redBehavior.startGame(deck3, false, 2, 4);
    redBehavior.playToPalette(0, 2);
    Assert.assertEquals(redBehavior.winningPaletteIndex(), 0);
    redBehavior.playToPalette(1, 0);
    Assert.assertTrue(redBehavior.isGameOver());
    Assert.assertFalse(redBehavior.isGameWon());
  }

  @Test
  public void playToTestOrange() {
    orangeBehavior.startGame(deck3, false, 3, 3);
    orangeBehavior.playToCanvas(2);
    Assert.assertEquals(orangeBehavior.getCanvas().getColor(), Color.O);
    orangeBehavior.playToPalette(2, 1);
    orangeBehavior.playToPalette(1, 0);
    Assert.assertTrue(orangeBehavior.isGameWon());
    Assert.assertTrue(orangeBehavior.isGameOver());
  }

  @Test
  public void playToTestBlue() {
    blueBehavior.startGame(deck3, false, 2, 4);
    blueBehavior.playToCanvas(2);
    blueBehavior.playToPalette(0, 2);
    Assert.assertEquals(blueBehavior.winningPaletteIndex(), 0);
    blueBehavior.playToPalette(1, 1);
    Assert.assertFalse(blueBehavior.isGameOver());
    Assert.assertEquals(blueBehavior.winningPaletteIndex(), 1);
    blueBehavior.playToPalette(0, 0);
    Assert.assertEquals(blueBehavior.winningPaletteIndex(), 0);
    Assert.assertTrue(blueBehavior.isGameOver());
    Assert.assertTrue(blueBehavior.isGameWon());
  }

  // Tests to determine proper implementation of rules under indigo canvas
  @Test
  public void playToTestIndigo() {
    indigoBehavior.startGame(deck4, false, 3, 6);
    indigoBehavior.playToCanvas(0);
    indigoBehavior.playToPalette(0, 2);
    indigoBehavior.drawForHand();
    indigoBehavior.playToPalette(2, 3);
    indigoBehavior.drawForHand();
    indigoBehavior.playToPalette(0, 2);
    indigoBehavior.playToPalette(2, 3);
    indigoBehavior.playToPalette(0, 1);
    Assert.assertTrue(indigoBehavior.isGameOver());
    Assert.assertFalse(indigoBehavior.isGameWon());
  }

  // Tests to determine proper implementation of rules under violet canvas
  @Test
  public void playToTestViolet() {
    violetBehavior.startGame(deck3, false, 3, 3);
    violetBehavior.playToCanvas(0);
    violetBehavior.playToPalette(2, 1);
    violetBehavior.playToPalette(0, 0);
    Assert.assertTrue(violetBehavior.isGameOver());
    Assert.assertFalse(violetBehavior.isGameWon());
  }

  // Updates given model to a state of the game being over
  public void playToGameOver(RedGameModel model) {
    model.startGame(deck2, false, 3, 2);
    model.playToPalette(1, 0);
  }

  // Tests for playToPalette() expected exceptions
  @Test
  public void testPlayToPaletteExceptions() {
    Assert.assertTrue(gameOverModel.isGameOver());
    Assert.assertThrows(IllegalStateException.class, () ->
            gameNotStarted.playToPalette(1, 2));
    Assert.assertThrows(IllegalStateException.class, () ->
            gameOverModel.playToPalette(1, 0));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            baseSetup.playToPalette(-1, 2));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            baseSetup.playToPalette(4, 2));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            baseSetup.playToPalette(1, -1));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            baseSetup.playToPalette(1, 5));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            baseSetup.playToPalette(1, 10));
    Assert.assertThrows(IllegalStateException.class, () ->
            baseSetup.playToPalette(2, 1));
  }


  // Tests for playToPalette() expected functionality
  @Test
  public void testPlayToPalette() {
    Assert.assertEquals(baseSetup.getPalette(0),
            List.of(new PlayCard(Color.R, 1)));
    Assert.assertEquals(baseSetup.getPalette(1),
            List.of(new PlayCard(Color.R, 2)));
    Assert.assertEquals(baseSetup.getPalette(2),
            List.of(new PlayCard(Color.R, 3)));
    baseSetup.playToPalette(1, 0);
    Assert.assertEquals(baseSetup.getPalette(0),
            List.of(new PlayCard(Color.R, 1)));
    Assert.assertEquals(baseSetup.getPalette(1),
            List.of(new PlayCard(Color.R, 2), new PlayCard(Color.R, 4)));
    Assert.assertEquals(baseSetup.getPalette(2),
            List.of(new PlayCard(Color.R, 3)));
    baseSetup.drawForHand();
    Assert.assertEquals(baseSetup.getHand().size(), 5);
    Assert.assertFalse(baseSetup.getHand().contains(new PlayCard(Color.R, 4)));
    Assert.assertEquals(baseSetup.winningPaletteIndex(), 1);
  }

  // Tests for playToCanvas() expected functionality
  @Test
  public void testPlayToCanvas() {
    Assert.assertEquals(baseSetup.getCanvas().getColor(), Color.R);
    Assert.assertEquals(baseSetup.getHand().size(), 5);
    baseSetup.playToCanvas(4);
    Assert.assertEquals(baseSetup.getCanvas().getColor(), Color.O);
    Assert.assertFalse(baseSetup.getHand().contains(new PlayCard(Color.O, 1)));
  }


  // Tests for numCardsInDeck() expected functionality
  @Test
  public void testNumOfCardsInDeck() {
    Assert.assertThrows(IllegalStateException.class, () ->
            gameNotStarted.numOfCardsInDeck());
    Assert.assertEquals(baseSetup.numOfCardsInDeck(), 27);
    baseSetup.playToPalette(0, 0);
    baseSetup.drawForHand();
    Assert.assertEquals(baseSetup.numOfCardsInDeck(), 26);
  }

  // Tests for numPalettes() expected functionality
  @Test
  public void testNumPalettes() {
    Assert.assertThrows(IllegalStateException.class, () ->
            gameNotStarted.numPalettes());
    Assert.assertEquals(baseSetup.numPalettes(), 3);
    Assert.assertEquals(gameOverModel.numPalettes(), 3);
  }

  // Tests for winningPaletteIndex() expected functionality
  // Tests for this method are also tested in various playToTest...() methods
  @Test
  public void testWinningPaletteIndex() {
    Assert.assertThrows(IllegalStateException.class, () ->
            gameNotStarted.winningPaletteIndex());
    Assert.assertEquals(baseSetup.winningPaletteIndex(), 2);

  }

  // Tests for isGameOver() expected functionality
  // Tests for this method are also tested in various playToTest...() methods
  @Test
  public void testIsGameOver() {
    Assert.assertThrows(IllegalStateException.class, () -> gameNotStarted.isGameOver());
    Assert.assertFalse(baseSetup.isGameOver());
    Assert.assertTrue(gameOverModel.isGameOver());
  }

  // Tests for isGameWon() expected functionality
  // Tests for this method are also tested in various playToTest...() methods
  @Test
  public void testIsGameWon() {
    Assert.assertThrows(IllegalStateException.class, () -> gameNotStarted.isGameWon());
    Assert.assertThrows(IllegalStateException.class, () -> baseSetup.isGameWon());

  }

  // Tests for getHand() expected functionality
  @Test
  public void testGetHand() {
    Assert.assertThrows(IllegalStateException.class, () -> gameNotStarted.getHand());
    Assert.assertEquals(baseSetup.getHand().size(), 5);
    baseSetup.getHand().remove(0);
    Assert.assertEquals(baseSetup.getHand().size(), 5);
  }

  // Tests for getPalette() expected functionality
  @Test
  public void testGetPalette() {
    Assert.assertThrows(IllegalStateException.class, () ->
            gameNotStarted.getPalette(0));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            baseSetup.getPalette(-1));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            baseSetup.getPalette(3));
    Assert.assertEquals(baseSetup.getPalette(0).size(), 1);
    baseSetup.getPalette(0).remove(new PlayCard(Color.R, 1));
    Assert.assertTrue(baseSetup.getPalette(0).contains(
            new PlayCard(Color.R, 1)));
  }

  // Tests for getCanvas() expected functionality
  @Test
  public void testGetCanvas() {
    Assert.assertThrows(IllegalStateException.class, () -> gameNotStarted.getCanvas());
    Assert.assertEquals(baseSetup.getCanvas().getColor(), Color.R);
  }

  // Tests for getAllCards() expected functionality
  @Test
  public void testGetAllCards() {
    List<PlayCard> test1 = baseSetup.getAllCards();
    Assert.assertEquals(baseSetup.getAllCards(), test1);
    Assert.assertEquals(baseSetup.getAllCards(), baseSetup.getAllCards());
    Assert.assertEquals(test1.size(), 35);
    test1.remove(0);
    Assert.assertEquals(test1.size(), 34);
    Assert.assertEquals(baseSetup.getAllCards().size(), 35);
  }

  // tests that attempting to draw from an empty deck does not throw error
  @Test
  public void testDrawWithEmptyDeck() {
    testShuffle.startGame(deck3, false, 3, 3);
    Assert.assertEquals(testShuffle.getHand().size(), 3);
    testShuffle.drawForHand();
    Assert.assertEquals(testShuffle.getHand().size(), 3);
  }
}
