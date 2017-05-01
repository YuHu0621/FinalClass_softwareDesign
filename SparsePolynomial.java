import java.util.Iterator;
import java.util.LinkedList;

/**
 * A sparse polynomial is a polynomial provides an efficient implementation of polynomials
 * in the case that the polynomial has few terms relative to its degree.  For example,
 * x^100 has just 1 term although its degree is 100.
 * 
 * @author Barbara Lerner
 * @version Feb. 23, 2017
 *
 */
final public class SparsePolynomial extends AbstractPolynomial implements Polynomial {

	// Internal linked list that holds all terms of Sparse Polynomial
	// Class invariants:
	//	  terms are in the list in ascending numerical order
	//	  all terms have non-zero coefficients
	final private LinkedList<Term> list; 

	/**
	 * Creates the zero polynomial 
	 */
	public SparsePolynomial() {
		list = new LinkedList<Term>();
		assert wellFormed();
	}

	/**
	 * Creates a polynomial with a single term
	 * @param coeff the coefficeient of the term
	 * @param exponent the exponent of the term
	 */
	public SparsePolynomial(int coeff, int exponent) {
		list = new LinkedList<Term>();
		if (coeff == 0) {
			return;
		}
		
		Term term = new Term (coeff, exponent);
		list.add(term);
		assert wellFormed();
	}

	/**
	 * Returns an iterator over the terms of the polynomial from the lowest to highest
	 * exponent.
	 */
	public Iterator<Term> iterator() {
		return list.iterator();
	}
	
	/**
	 * Return the smallest exponent of the polynomial.
	 */
	@Override
	public int getMinExponent() {
		if (isZero()) {
			return 0;
		}
		return list.getFirst().getExponent();
	}

	/**
	 * Return the largest exponent of the polynomial. 
	 */
	@Override
	public int getMaxExponent() {
		if (isZero()) {
			return 0;
		}
		return list.getLast().getExponent();
	}

	/**
	 * Return the coefficient for the term with the given exponent.  Return 0 if there
	 * is no term for the exponent.
	 */
	@Override
	public int getCoeff(int exp) {
		for (Term t : list) {
			int tExp = t.getExponent();
			if (tExp == exp) {
				return t.getCoefficient();
			}
			if (tExp > exp) {
				return 0;
			}
		}
		return 0;
	}

	/**
	 * Return true if this polynomial is the constant 0.
	 */
	@Override
	public boolean isZero() {
		return (list.size() == 0);  
	}

	/**
	 * Return the sum of this polynomial and q.  Neither this nor q are changed.
	 */
	@Override
	public Polynomial add(Polynomial q) {
		if (q instanceof SparsePolynomial) {
			return addSparse ((SparsePolynomial) q);
		}
		
		Polynomial result = new SparsePolynomial().addSparse(this);
		for (int exp = q.getMinExponent(); exp <= q.getMaxExponent(); exp++) {
			result = result.add (new SparsePolynomial (q.getCoeff(exp), exp));
		}
		
		assert result.wellFormed();
		return result;
	}
	
	/**
	 * Return the sum of this polynomial and q.  Neither this nor q are changed.
	 */
	private SparsePolynomial addSparse (SparsePolynomial q) {
		if (q.isZero()) {
			return this;
		}
		
		if (isZero()) {
			return q;
		}
		
		SparsePolynomial sp = new SparsePolynomial();
		
		Iterator<Term> iter1 = iterator();
		Iterator<Term> iter2 = q.iterator();
		
		Term t1 = iter1.next();
		Term t2 = iter2.next();
		
		do {
			int t1Exp = t1.getExponent();
			int t2Exp = t2.getExponent();
			
			// Terms have same exponent
			if (t1Exp == t2Exp) {
				int newCoeff = t1.getCoefficient() + t2.getCoefficient();
				if (newCoeff != 0) {
					sp.list.add(new Term(newCoeff, t1Exp));
				}
				t1 = nextTerm (iter1);
				t2 = nextTerm (iter2);
			} 
			
			// t2 has a lower exponent
			else if (t1Exp > t2Exp) {
				sp.list.add(new Term(t2.getCoefficient(), t2Exp));
				t2 = nextTerm (iter2);
			} 
			
			// t1 has a lower exponent
			else {
				sp.list.add(new Term(t1.getCoefficient(), t1Exp));
				t1 = nextTerm (iter1);
			}
		} while (t1 != null && t2 != null);
		
		// We have run out of terms in at least one of the polynomials.  Add
		// in whatever is left of the remaining polynomial.
		if (t1 != null) {
			addRemainingTerms(sp, iter1, t1);
		}
		
		else if (t2 != null) {
			addRemainingTerms(sp, iter2, t2);
		}

		assert sp.wellFormed();
		return sp;
	}

	/**
	 * Add this term and all remaining terms to sp.
	 * @param sp the polynomial to add terms to
	 * @param iter the iterator that will return the remaining terms
	 * @param t the current term to add.  It has already been returned by iter.
	 *    Precondition: there is no term in sp with the same exponent as any of the 
	 *    remaining terms in iter.
	 */
	private void addRemainingTerms(SparsePolynomial sp, Iterator<Term> iter,
			Term t) {
		do {
			sp.list.add (new Term (t.getCoefficient(), t.getExponent()));
			t = nextTerm (iter);
		} while (t != null);
		assert sp.wellFormed();
	}

	/**
	 * @param iter the iterator to get the term from
	 * @return the next term from the iterator.  Return null when there are no more terms.
	 */
	private Term nextTerm (Iterator<Term> iter) {
		if (iter.hasNext()) {
			return iter.next();
		} else {
			return null;
		}

	}

	/**
	 * Return a polynomial that is the product of this and factor.  Does not modify this.
	 */
	@Override
	public Polynomial multiply(int factor) {
		if (isZero()) {
			return this;
		}
		
		if (factor == 0) {
			return new SparsePolynomial();
		}
		
		// Multiple each term of this by the factor and add together those term-wise products.
		SparsePolynomial product = new SparsePolynomial();
		for (Term t : list) {
			int newCoeff = t.getCoefficient() * factor;
			product.list.add(new Term (newCoeff, t.getExponent()));
		}
		
		assert product.wellFormed();
		return product;
	}



	/**
	 * Returns true if o is an equivalent polynomial.  That is it contains the same coefficients
	 * for the same exponents.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		
		if (o == this) {
			return true;
		}
		
		if (!(o instanceof Polynomial)) {
			return false;
		}
		
		// If o is a Polynomial but not a SparsePolynomial, we rely on the string representations
		// being the same
		if (!(o instanceof SparsePolynomial)) {
			return toString().equals(o.toString());
		}
		
		SparsePolynomial obj = (SparsePolynomial) o;
		if (obj.isZero() != isZero()) {
			return false;
		}
		
		// All the easy tests passed.  Compare the terms.
		Iterator<Term> iter = iterator();
		Iterator<Term> otherIter = obj.iterator();
		while (iter.hasNext()) {
			Term t = iter.next();
			Term otherT = otherIter.next();
			
			if (!t.equals(otherT)) {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Generates the polynomial in canonical form.  Terms are sorted by exponent from high to 
	 * low.  Terms with a 0 coefficient are not displayed, except in the case that the 
	 * polynomial is the constant 0.  There is no more than one term with the same exponent. 
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		Iterator<Term> iter = iterator();
		if (!iter.hasNext()) {
			return "0";
		}
		
		Term t = iter.next();
		builder.insert (0, t.toString());
		while (iter.hasNext()) {
			t = iter.next();
			builder.insert (0, t.toString() + " + ");
		}
		
		return builder.toString();
		
	}

	/**
	 * Return true if the polynomial satisfies the class invariants
	 */
	@Override
	public boolean wellFormed() {
		if (list == null) {
			return false;
		}
		
		if (list.size() == 0) {
			return true;
		}
		
		// There should be no terms with 0 for coefficient
		Iterator<Term> iter = list.iterator();
		Term current = iter.next();
		if (current.getCoefficient() == 0) {
			return false;
		}
		
		// Check that the terms are sorted by exponent from low to high
		// Each term should have a unique exponent
		// No term should have zero for its coefficient
		while (iter.hasNext()) {
			Term next = iter.next();
			
			if (current.getExponent() >= next.getExponent()) {
				return false;
			}
			if (next.getCoefficient() == 0) {
				return false;
			}
			current = next;
		}

		return true;
	}
}
