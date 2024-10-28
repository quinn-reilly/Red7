package cs3500.solored;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.solored.model.hw02.PlayCard;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw04.AdvancedSoloRedGameModel;

/**
 * The test class for an AdvancedSoloRedGameModel.
 */
public class TestAdvancedModel extends AbstractTestModel {
  RedGameModel<PlayCard> modelGame;

  // Initial circumstances that are run before each test
  @Before
  public void inits() {
    super.inits();

    // Initialize models
    baseSetup = new AdvancedSoloRedGameModel();
    gameNotStarted = new AdvancedSoloRedGameModel();
    gameOverModel = new AdvancedSoloRedGameModel();
    testShuffle = new AdvancedSoloRedGameModel(rand1);
    redBehavior = new AdvancedSoloRedGameModel();
    orangeBehavior = new AdvancedSoloRedGameModel();
    blueBehavior = new AdvancedSoloRedGameModel();
    indigoBehavior = new AdvancedSoloRedGameModel();
    violetBehavior = new AdvancedSoloRedGameModel();
    modelGame = new AdvancedSoloRedGameModel(rand1);

    // Set models up for testing
    allCards = baseSetup.getAllCards();
    baseSetup.startGame(allCards, false, 3, 5);
    playToGameOver(gameOverModel);
    modelGame.startGame(modelGame.getAllCards(), true, 4, 7);
  }

  // Tests for drawForHand() expected exceptions
  @Test
  public void testDrawForHandExceptions() {
    Assert.assertThrows(IllegalStateException.class, () -> gameNotStarted.drawForHand());
    Assert.assertThrows(IllegalStateException.class, () -> gameOverModel.drawForHand());
  }

  // Test basic playing functionality of DrawForHand()
  @Test
  public void testDrawForHandBasic() {
    Assert.assertEquals(baseSetup.getHand().size(), 5);
    baseSetup.playToPalette(0, 0);
    Assert.assertEquals(baseSetup.getHand().size(), 4);
    baseSetup.drawForHand();
    Assert.assertEquals(baseSetup.getHand().size(), 5);
    // play to canvas with low value
    baseSetup.playToCanvas(3);
    Assert.assertEquals(baseSetup.getHand().size(), 4);
    baseSetup.playToPalette(1, 3);
    Assert.assertEquals(baseSetup.getHand().size(), 3);
    baseSetup.drawForHand();
    // ensure only drawing one card
    Assert.assertEquals(baseSetup.getHand().size(), 4);
  }

  // Test that DrawForHand() draws two correctly at start of game
  @Test
  public void testDrawTwo() {
    Assert.assertEquals(modelGame.getHand().size(), 7);
    modelGame.playToCanvas(5);
    Assert.assertEquals(modelGame.getHand().size(), 6);
    modelGame.playToPalette(3, 3);
    Assert.assertEquals(modelGame.getHand().size(), 5);
    modelGame.drawForHand();
    Assert.assertEquals(modelGame.getHand().size(), 7);
  }

  // Test that DrawForHand() draws two correctly when less than max hand size
  @Test
  public void testDrawTwoLessThanMax() {
    Assert.assertEquals(modelGame.getHand().size(), 7);
    modelGame.playToCanvas(3);
    Assert.assertEquals(modelGame.getHand().size(), 6);
    modelGame.playToPalette(0, 3);
    Assert.assertEquals(modelGame.getHand().size(), 5);
    modelGame.drawForHand();
    Assert.assertEquals(modelGame.getHand().size(), 6);
    modelGame.playToCanvas(4);
    Assert.assertEquals(modelGame.getHand().size(), 5);
    modelGame.playToPalette(0,4);
    Assert.assertEquals(modelGame.getHand().size(), 4);
    modelGame.drawForHand();
    Assert.assertEquals(modelGame.getHand().size(), 6);
  }
}
