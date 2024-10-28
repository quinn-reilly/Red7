package cs3500.solored;

import org.junit.Assert;
import org.junit.Test;

import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedGameModel;
import cs3500.solored.model.hw04.AdvancedSoloRedGameModel;
import cs3500.solored.model.hw04.RedGameCreator;

/**
 * Test class for the RedGameCreator class.
 */
public class TestRedGameCreator {
  // Test the proper construction of a SoloRedGameModel
  @Test
  public void testCreatorBasic() {
    RedGameModel model;
    model = RedGameCreator.createGame(RedGameCreator.GameType.BASIC);
    Assert.assertTrue(model instanceof SoloRedGameModel);
  }

  // Test proper construction of AdvancedSoloRedGameModel
  @Test
  public void testCreatorAdvanced() {
    RedGameModel model;
    model = RedGameCreator.createGame(RedGameCreator.GameType.ADVANCED);
    Assert.assertTrue(model instanceof AdvancedSoloRedGameModel);
  }
}
