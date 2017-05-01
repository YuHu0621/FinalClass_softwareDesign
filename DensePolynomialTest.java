import junit.framework.TestCase;

/**
 * Test for DensePolynomial class
 *
 */
public class DensePolynomialTest extends TestCase {

	private Polynomial zero = new DensePolynomial(0, 5);
	private Polynomial one = new DensePolynomial (1, 0);
	private Polynomial minusOne = new DensePolynomial (-1, 0);
	private Polynomial twoX = new DensePolynomial (2, 1);
	private Polynomial minusTwoX = new DensePolynomial (-2, 1);
	private DensePolynomial twoXplusOne = (DensePolynomial) twoX.add(one);
	private DensePolynomial fourXplusTwo = (DensePolynomial) new DensePolynomial(4, 1).add(new DensePolynomial (2, 0));
	private Polynomial minusTwoXMinusOne = minusTwoX.add(minusOne);
	private Polynomial fourXSquaredPlusFourXPlusOne = new DensePolynomial(4,2).add(new DensePolynomial(4,1).add(one));
	private Polynomial xTo100 = new DensePolynomial(1, 100);
	private Polynomial xToNegative100 = new SparsePolynomial(1, -100);
	
	/**
	 * Test method for {@link DensePolynomial#getCoeff(int)}.
	 */
	public void testGetCoeff() {
		assertEquals (0, zero.getCoeff(0));
		assertEquals (1, one.getCoeff(0));
		assertEquals (0, one.getCoeff(-1));
		assertEquals (0, one.getCoeff(1));
		assertEquals(-1, minusOne.getCoeff(0));
		assertEquals (1, xTo100.getCoeff(100));
		assertEquals (0, xTo100.getCoeff(10));
		assertEquals (0, xTo100.getCoeff(-1000));
	}
	
	/**
	 * Test method for {@link DensePolynomial#getMaxExponent()}.
	 */
	public void testgetMaxExponent() {
		assertEquals (0, zero.getMaxExponent());
		assertEquals (0, one.getMaxExponent());
		assertEquals (100, xTo100.getMaxExponent());
	}
	
	/**
	 * Test method for {@link DensePolynomial#getMinExponent()}.
	 */
	public void testgetMinExponent(){
		assertEquals(0, zero.getMinExponent());
		assertEquals(0, one.getMinExponent());
		assertEquals(1, twoX.getMinExponent());
		assertEquals(100, xTo100.getMinExponent());
		assertEquals(-100, one.add(xToNegative100).getMinExponent());
		assertEquals(0, one.add(xTo100).getMinExponent());
	}
	
	/**
	 * Test method for {@link DensePolynomial#add(Polynomial)}.
	 */
	public void testAdd() {
		assertEquals (zero.add(twoX), twoX.add(zero));
		assertEquals (zero, zero.add(zero));
		assertEquals (zero, one.add(minusOne));
		assertEquals (one, twoXplusOne.add(minusTwoX));
		xToNegative100 = (SparsePolynomial) one.add(xToNegative100);
		assertEquals("1 + x^-100", xToNegative100.toString());
		
		boolean thrown = false;
		  try {
		    zero.add(null);
		  } catch (NullPointerException e) {
		    thrown = true;
		  }
		assertTrue(thrown);
	}
	
	/**
	 * test final class DensePolynomial is immutable
	 */
	public void testImmutability(){
		one.add(xToNegative100);
		assertEquals(0, one.getCoeff(-100));
		assertEquals(1, one.getCoeff(0));
		
		one.multiply(-1);
		assertEquals(1, one.getCoeff(0));
		
		one.subtract(one);
		assertEquals(1, one.getCoeff(0));
	}
	
	/**
	 * Test method for {@link DensePolynomial#minus()}.
	 */
	public void testMinus() {
		assertEquals (zero.minus(), zero);
		assertEquals (one.minus(), minusOne);
		assertEquals (twoXplusOne.minus(), minusTwoXMinusOne);
		assertEquals(minusTwoX.minus(), twoX);
	}
	
	/**
	 * Test method for {@link DensePolynomial#isZero()}.
	 */
	public void testIsZero() {
		assertTrue (zero.isZero());
		assertFalse (one.isZero());
		assertFalse (twoX.isZero());
		assertTrue (twoX.add(minusTwoX).isZero());
	}

	/**
	 * Test method for {@link DensePolynomial#multiply(Polynomial)}.
	 */
	public void testMultiply() {
		assertEquals (zero.multiply(1), zero);
		assertEquals (one.multiply(0), zero);
		assertEquals (twoXplusOne.multiply(1), twoXplusOne);
		assertEquals (twoXplusOne.multiply(2), fourXplusTwo);
	}

	/**
	 * Test method for {@link DensePolynomial#subtract(Polynomial)}.
	 */
	public void testSubtract() {
		assertEquals (twoXplusOne.subtract(one), twoX);
		boolean thrown = false;
		  try {
		    zero.subtract(null);
		  } catch (NullPointerException e) {
		    thrown = true;
		  }
		assertTrue(thrown);
	}

	
	/**
	 * Test method for {@link DensePolynomial#toString()}.
	 */
	public void testToString() {
		assertEquals (zero.toString(), "0");
		assertEquals (one.toString(), "1");
		assertEquals (twoX.toString(), "2x");
		assertEquals (twoXplusOne.toString(), "2x + 1");
		assertEquals (minusTwoXMinusOne.toString(), "-2x + -1");
		assertEquals (fourXSquaredPlusFourXPlusOne.toString(), "4x^2 + 4x + 1");
	}


	/**
	 * Test method for {@link DensePolynomial#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() {
		assertEquals (zero, zero);
		boolean thrown = false;
		  try {
		    zero.equals(null);
		  } catch (NullPointerException e) {
		    thrown = true;
		  }
		assertTrue(thrown);
		assertFalse(zero.equals(new Integer(0)));
		assertFalse (zero.equals(twoX));
		assertFalse (zero.equals(twoXplusOne));
		assertFalse (zero.equals(one));
		assertEquals (fourXSquaredPlusFourXPlusOne, new SparsePolynomial(4,2).add(new SparsePolynomial(4,1).add(one)));
	}
}
