package cs3500.solored.model.hw02;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the PlayCard class.
 */
public class TestPlayCard {
  PlayCard r1;
  PlayCard o4;
  PlayCard b7;
  PlayCard i7;
  PlayCard v6;

  @Before
  public void inits() {
    r1 = new PlayCard(Color.R, 1);
    o4 = new PlayCard(Color.O, 4);
    b7 = new PlayCard(Color.B, 7);
    i7 = new PlayCard(Color.I, 7);
    v6 = new PlayCard(Color.V, 6);
  }

  // Tests for the expected functionality of copy()
  @Test
  public void testCopy() {
    Assert.assertNotSame(r1, r1.copy());
    Assert.assertNotSame(o4, o4.copy());
  }

  // Tests for the expected functionality of PlayCard construction
  @Test
  public void testConstruction() {
    Assert.assertThrows(IllegalArgumentException.class, () ->
            new PlayCard(Color.R, -5));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            new PlayCard(Color.R, 9));
    Assert.assertEquals(r1.getColor(), Color.R);
    Assert.assertEquals(b7.getValue(), 7);
  }

  // Tests for the expected functionality of isHigherThan()
  @Test
  public void testIsHigherThat() {
    Assert.assertTrue(i7.isHigherThan(v6));
    Assert.assertTrue(b7.isHigherThan(i7));
    Assert.assertFalse(r1.isHigherThan(o4));
    Assert.assertFalse(v6.isHigherThan(i7));
  }
}
