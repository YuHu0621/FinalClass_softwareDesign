import java.util.Iterator;

import junit.framework.TestCase;

/**
 * Tests for the SparsePolynomial class.
 */
public class SparsePolynomialTest extends TestCase {
	private Polynomial zero = new SparsePolynomial(0, 5);
	private Polynomial one = new SparsePolynomial (1, 0);
	private Polynomial minusOne = new SparsePolynomial (-1, 0);
	private Polynomial twoX = new SparsePolynomial (2, 1);
	private Polynomial minusTwoX = new SparsePolynomial (-2, 1);
	private SparsePolynomial twoXplusOne = (SparsePolynomial) twoX.add(one);
	private Polynomial fourXplusTwo = new SparsePolynomial(4, 1).add(new SparsePolynomial (2, 0));
	private Polynomial minusTwoXMinusOne = minusTwoX.add(minusOne);
	private Polynomial fourXSquaredPlusFourXPlusOne = new SparsePolynomial(4,2).add(new SparsePolynomial(4,1).add(one));
	private Polynomial xTo100 = new SparsePolynomial(1, 100);
	private Polynomial xToNegative100 = new SparsePolynomial(1, -100);
	
	/**
	 * Test method for {@link SparsePolynomial#getCoeff(int)}.
	 */
	public void testGetCoeff() {
		assertEquals (0, zero.getCoeff(0));
		assertEquals (1, one.getCoeff(0));
		assertEquals (0, one.getCoeff(-1));
		assertEquals (0, one.getCoeff(1));
		assertEquals (1, xTo100.getCoeff(100));
		assertEquals (0, xTo100.getCoeff(10));
		assertEquals (0, xTo100.getCoeff(1000));
		assertEquals (0, xTo100.getCoeff(-1000));
		assertEquals (1, xToNegative100.getCoeff(-100));
	}

	/**
	 * Test method for {@link SparsePolynomial#getMaxExponent()}.
	 */
	public void testgetMaxExponent() {
		assertEquals (0, zero.getMaxExponent());
		assertEquals (0, one.getMaxExponent());
		assertEquals (100, xTo100.getMaxExponent());
		assertEquals (-100, xToNegative100.getMaxExponent());
	}

	/**
	 * Test method for {@link SparsePolynomial#iterator()}.
	 */
	public void testIterator() {
		Iterator<Term> iter = twoXplusOne.iterator();
		assertTrue (iter.hasNext());
		Term t = iter.next();
		assertEquals (new Term(1, 0), t);
		assertTrue (iter.hasNext());
		t = iter.next();
		assertEquals(new Term(2, 1), t);
		assertFalse (iter.hasNext());
	}

	/**
	 * Test method for {@link SparsePolynomial#add(Polynomial)}.
	 */
	public void testAdd() {
		assertEquals (zero.add(twoX), twoX.add(zero));
		assertEquals (zero, zero.add(zero));
		assertEquals (zero, one.add(minusOne));
		assertEquals (one, twoXplusOne.add(minusTwoX));
	}

	/**
	 * Test method for {@link SparsePolynomial#minus()}.
	 */
	public void testMinus() {
		assertEquals (zero.minus(), zero);
		assertEquals (one.minus(), minusOne);
		assertEquals (twoXplusOne.minus(), minusTwoXMinusOne);
	}

	/**
	 * Test method for {@link SparsePolynomial#isZero()}.
	 */
	public void testIsZero() {
		assertTrue (zero.isZero());
		assertFalse (one.isZero());
		assertFalse (twoX.isZero());
	}

	/**
	 * Test method for {@link SparsePolynomial#multiply(Polynomial)}.
	 */
	public void testMultiply() {
		assertEquals (zero.multiply(1), zero);
		assertEquals (one.multiply(0), zero);
		assertEquals (twoXplusOne.multiply(1), twoXplusOne);
		assertEquals (twoXplusOne.multiply(2), fourXplusTwo);
	}

	/**
	 * Test method for {@link SparsePolynomial#subtract(Polynomial)}.
	 */
	public void testSubtract() {
		assertEquals (twoXplusOne.subtract(one), twoX);
	}

	/**
	 * Test method for {@link SparsePolynomial#toString()}.
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
	 * Test method for {@link SparsePolynomial#clone()}.
	 */
	public void testClone() {
		assertEquals (fourXSquaredPlusFourXPlusOne, fourXSquaredPlusFourXPlusOne);
	}

	/**
	 * Test method for {@link SparsePolynomial#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() {
		assertEquals (zero, zero);
		assertFalse (zero.equals(null));
		assertFalse (zero.equals(new Integer(0)));
		assertFalse (zero.equals(twoX));
		assertFalse (zero.equals(twoXplusOne));
		assertFalse (zero.equals(one));
		assertEquals (fourXSquaredPlusFourXPlusOne, new SparsePolynomial(4,2).add(new SparsePolynomial(4,1).add(one)));
	}

}
