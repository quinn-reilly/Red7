package cs3500.solored;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * The test class for a SoloRedGameModel.
 */
public class TestModel extends AbstractTestModel {

  // Initial circumstances that are run before each test
  @Before
  public void inits() {
    super.inits();

    // Initialize models
    baseSetup = new SoloRedGameModel();
    gameNotStarted = new SoloRedGameModel();
    gameOverModel = new SoloRedGameModel();
    testShuffle = new SoloRedGameModel(rand1);
    redBehavior = new SoloRedGameModel();
    orangeBehavior = new SoloRedGameModel();
    blueBehavior = new SoloRedGameModel();
    indigoBehavior = new SoloRedGameModel();
    violetBehavior = new SoloRedGameModel();

    // Set models up for testing
    allCards = baseSetup.getAllCards();
    baseSetup.startGame(allCards, false, 3, 5);
    playToGameOver(gameOverModel);
  }


  // Tests for drawForHand() expected functionality
  @Test
  public void testDrawForHand() {
    Assert.assertThrows(IllegalStateException.class, () -> gameNotStarted.drawForHand());
    Assert.assertThrows(IllegalStateException.class, () -> gameOverModel.drawForHand());
    Assert.assertEquals(baseSetup.getHand().size(), 5);
    baseSetup.playToPalette(0, 0);
    Assert.assertEquals(baseSetup.getHand().size(), 4);
    baseSetup.drawForHand();
    Assert.assertEquals(baseSetup.getHand().size(), 5);
  }




}
