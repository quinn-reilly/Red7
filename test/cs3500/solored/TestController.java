package cs3500.solored;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import cs3500.solored.controller.RedGameController;
import cs3500.solored.controller.SoloRedTextController;
import cs3500.solored.model.hw02.Color;
import cs3500.solored.model.hw02.MockModel;
import cs3500.solored.model.hw02.PlayCard;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * The test class for a SoloRedTextController.
 **/
public class TestController {
  Readable rd1;
  Appendable ap1;

  SoloRedTextController cont1;
  SoloRedGameModel model1;

  List<PlayCard> allCards;
  List<PlayCard> deck2;


  @Before
  public void inits() {
    rd1 = new StringReader("");
    ap1 = new StringBuilder();

    model1 = new SoloRedGameModel();
    cont1 = new SoloRedTextController(rd1, ap1);

    allCards = new ArrayList<>();

    deck2 = new ArrayList<>();
    deck2.add(new PlayCard(Color.R, 3));
    deck2.add(new PlayCard(Color.O, 4));
    deck2.add(new PlayCard(Color.B, 5));
    deck2.add(new PlayCard(Color.O, 2));
    deck2.add(new PlayCard(Color.I, 7));

  }

  @Test
  public void testControllerExceptions() {
    Assert.assertThrows(IllegalArgumentException.class, () ->
            new SoloRedTextController(null, ap1));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            new SoloRedTextController(rd1, null));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            new SoloRedTextController(null, null));
  }

  @Test
  public void testPlayGameExceptions() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader(" canvas 1 q ");
    RedGameController controller1 = new SoloRedTextController(in, out);
    Assert.assertThrows(IllegalArgumentException.class, () ->
            controller1.playGame(null, allCards, false, 2, 2));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            controller1.playGame(model1, deck2, false, -1, 2));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            controller1.playGame(model1, deck2, false, 7, 10));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            controller1.playGame(model1, deck2, false, 2, 10));
  }

  @Test
  public void testCanvasControl() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader(" canvas q ");
    RedGameController controller = new SoloRedTextController(in, out);
    RedGameModel<PlayCard> model = new SoloRedGameModel();
    controller.playGame(model, model.getAllCards(), false, 2, 3);
    Assert.assertEquals("Canvas: R\n" +
            "P1: R1\n" +
            "> P2: R2\n" +
            "Hand: R3 R4 R5\n" +
            "Number of cards in deck: 30\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "Canvas: R\n" +
            "P1: R1\n" +
            "> P2: R2\n" +
            "Hand: R3 R4 R5\n" +
            "Number of cards in deck: 30\n", out.toString());
    out = new StringBuilder();
    in = new StringReader("canvas p l 1 q ");
    controller = new SoloRedTextController(in, out);
    model = new SoloRedGameModel();
    controller.playGame(model, model.getAllCards(), false, 2, 3);
    Assert.assertEquals("Canvas: R\n" +
            "P1: R1\n" +
            "> P2: R2\n" +
            "Hand: R3 R4 R5\n" +
            "Number of cards in deck: 30\n" +
            "Canvas: R\n" +
            "P1: R1\n" +
            "> P2: R2\n" +
            "Hand: R4 R5\n" +
            "Number of cards in deck: 30\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "Canvas: R\n" +
            "P1: R1\n" +
            "> P2: R2\n" +
            "Hand: R4 R5\n" +
            "Number of cards in deck: 30\n", out.toString());
    out = new StringBuilder();
    in = new StringReader("canvas p l 1 q ");
    controller = new SoloRedTextController(in, out);
    model = new SoloRedGameModel();
    controller.playGame(model, model.getAllCards(), false, 2, 3);
    Assert.assertEquals("Canvas: R\n" +
            "P1: R1\n" +
            "> P2: R2\n" +
            "Hand: R3 R4 R5\n" +
            "Number of cards in deck: 30\n" +
            "Canvas: R\n" +
            "P1: R1\n" +
            "> P2: R2\n" +
            "Hand: R4 R5\n" +
            "Number of cards in deck: 30\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "Canvas: R\n" +
            "P1: R1\n" +
            "> P2: R2\n" +
            "Hand: R4 R5\n" +
            "Number of cards in deck: 30\n", out.toString());
  }

  @Test
  public void testPaletteControl() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader(" palette q ");
    RedGameController controller = new SoloRedTextController(in, out);
    RedGameModel<PlayCard> model = new SoloRedGameModel();
    controller.playGame(model, model.getAllCards(), false, 2, 3);
    Assert.assertEquals("Canvas: R\n" +
            "P1: R1\n" +
            "> P2: R2\n" +
            "Hand: R3 R4 R5\n" +
            "Number of cards in deck: 30\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "Canvas: R\n" +
            "P1: R1\n" +
            "> P2: R2\n" +
            "Hand: R3 R4 R5\n" +
            "Number of cards in deck: 30\n", out.toString());
    out = new StringBuilder();
    in = new StringReader("palette 1 q ");
    controller = new SoloRedTextController(in, out);
    model = new SoloRedGameModel();
    controller.playGame(model, model.getAllCards(), false, 2, 3);
    Assert.assertEquals("Canvas: R\n" +
            "P1: R1\n" +
            "> P2: R2\n" +
            "Hand: R3 R4 R5\n" +
            "Number of cards in deck: 30\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "Canvas: R\n" +
            "P1: R1\n" +
            "> P2: R2\n" +
            "Hand: R3 R4 R5\n" +
            "Number of cards in deck: 30\n", out.toString());
    out = new StringBuilder();
    in = new StringReader("palette 2 1 q ");
    controller = new SoloRedTextController(in, out);
    model = new SoloRedGameModel();
    controller.playGame(model, model.getAllCards(), false, 2, 3);
    Assert.assertEquals("Canvas: R\n" +
            "P1: R1\n" +
            "> P2: R2\n" +
            "Hand: R3 R4 R5\n" +
            "Number of cards in deck: 30\n" +
            "Invalid move. Try again. Cannot play to winning palette\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "Canvas: R\n" +
            "P1: R1\n" +
            "> P2: R2\n" +
            "Hand: R3 R4 R5\n" +
            "Number of cards in deck: 30\n", out.toString());
  }

  @Test
  public void testPaletteNonValid() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader(" palette 1 number 3 q");
    RedGameController controller = new SoloRedTextController(in, out);
    RedGameModel<PlayCard> model = new SoloRedGameModel();
    controller.playGame(model, model.getAllCards(), false, 2, 3);
    Assert.assertEquals("Canvas: R\n" +
            "P1: R1\n" +
            "> P2: R2\n" +
            "Hand: R3 R4 R5\n" +
            "Number of cards in deck: 30\n" +
            "Canvas: R\n" +
            "> P1: R1 R5\n" +
            "P2: R2\n" +
            "Hand: R3 R4 R6\n" +
            "Number of cards in deck: 29\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "Canvas: R\n" +
            "> P1: R1 R5\n" +
            "P2: R2\n" +
            "Hand: R3 R4 R6\n" +
            "Number of cards in deck: 29\n", out.toString());
  }

  @Test
  public void testControllerOutputs() {
    StringBuilder log = new StringBuilder();
    RedGameModel<PlayCard> mock = new MockModel(log);

    StringBuilder out = new StringBuilder();
    Reader in = new StringReader(" Q ");

    RedGameController controllerTest = new SoloRedTextController(in, out);
    controllerTest.playGame(mock, allCards, false, 2, 3);
    // game has started?
    Assert.assertEquals("shuffle = false, numPalettes = 2, handSize = 3\n",
            log.toString());

  }

  @Test
  public void testNoInput() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader(" ");
    RedGameController controller = new SoloRedTextController(in, out);
    RedGameModel<PlayCard> model = new SoloRedGameModel();
    controller.playGame(model, model.getAllCards(), false, 2, 3);
    Assert.assertEquals("Canvas: R\n" +
            "P1: R1\n" +
            "> P2: R2\n" +
            "Hand: R3 R4 R5\n" +
            "Number of cards in deck: 30\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "Canvas: R\n" +
            "P1: R1\n" +
            "> P2: R2\n" +
            "Hand: R3 R4 R5\n" +
            "Number of cards in deck: 30\n", out.toString());
  }

  @Test
  public void playThroughToWin() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader(" canvas 3 palette 1 numher 2 palette 2 1 ");
    RedGameController controller = new SoloRedTextController(in, out);
    RedGameModel<PlayCard> model = new SoloRedGameModel();
    controller.playGame(model, deck2, false, 2, 3);
    Assert.assertEquals("Canvas: R\n" +
            "P1: R3\n" +
            "> P2: O4\n" +
            "Hand: B5 O2 I7\n" +
            "Number of cards in deck: 0\n" +
            "Canvas: I\n" +
            "P1: R3\n" +
            "> P2: O4\n" +
            "Hand: B5 O2\n" +
            "Number of cards in deck: 0\n" +
            "Canvas: I\n" +
            "> P1: R3 O2\n" +
            "P2: O4\n" +
            "Hand: B5\n" +
            "Number of cards in deck: 0\n" +
            "Canvas: I\n" +
            "P1: R3 O2\n" +
            "> P2: O4 B5\n" +
            "Hand: \n" +
            "Number of cards in deck: 0\n" +
            "Game won.\n" +
            "Canvas: I\n" +
            "P1: R3 O2\n" +
            "> P2: O4 B5\n" +
            "Hand: \n" +
            "Number of cards in deck: 0\n", out.toString());
  }


}