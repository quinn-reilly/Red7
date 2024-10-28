package cs3500.solored.model.hw02;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class that tests the protected methods in the model classes.
 */
public class TestProtectedModel {

  // Tests for canvasExceptions() expected exceptions
  @Test
  public void testCanvasExceptions() {
    SoloRedGameModel baseSetup = new SoloRedGameModel();
    SoloRedGameModel gameNotStarted = new SoloRedGameModel();
    SoloRedGameModel gameOverModel = new SoloRedGameModel();
    Assert.assertThrows(IllegalStateException.class, () ->
            gameNotStarted.playToCanvas(4));
    Assert.assertThrows(IllegalStateException.class, () ->
            gameOverModel.playToCanvas(0));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            baseSetup.playToCanvas(-1));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            baseSetup.playToCanvas(7));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            baseSetup.playToCanvas(5));
    baseSetup.playToCanvas(4);
    Assert.assertThrows(IllegalStateException.class, () ->
            baseSetup.playToCanvas(3));

  }
}
