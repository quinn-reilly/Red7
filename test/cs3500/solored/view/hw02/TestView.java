package cs3500.solored.view.hw02;

import org.junit.Assert;
import org.junit.Test;

import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * The test class for a SoloRedGameTextView.
 **/
public class TestView {

  // Tests for the toString method
  @Test
  public void testToString() {
    SoloRedGameModel model = new SoloRedGameModel();
    model.startGame(model.getAllCards(), false, 5, 7);
    SoloRedGameTextView view = new SoloRedGameTextView(model);
    Assert.assertEquals(view.toString(),
            "Canvas: R\n"
                    + "P1: R1\n"
                    + "P2: R2\n"
                    + "P3: R3\n"
                    + "P4: R4\n"
                    + "> P5: R5\n"
                    + "Hand: R6 R7 O1 O2 O3 O4 O5");
  }


}
