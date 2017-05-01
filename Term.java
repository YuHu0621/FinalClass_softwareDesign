/**
 * A term is a component in a polynomial consisting of a single coefficient
 * and a single exponent.  For example, in 2x^3, 2 is the coefficient and 
 * 3 is the exponent.
 * 
 * @author Barbara Lerner
 * @version Oct 4, 2015
 *
 */
public class Term {

	// The coefficient
	private int coef;
	
	// The exponent
	private int exp;

	/**
	 * Term takes in a coefficient and exponent
	 * @param coef the coefficient
	 * @param exp the exponent
	 */
	public Term(int coef, int exp) {
		this.coef = coef;
		this.exp = exp;
	}

	/**
	 * @return the coefficient of the term
	 */
	public int getCoefficient() {
		return this.coef;
	}

	/**
	 * @return the exponent of the term
	 */
	public int getExponent() {
		return this.exp;
	}

	/**
	 * @return true if this term has the same coefficient
	 * and exponent as the parameter
	 */
	@Override
	public boolean equals (Object o) {
		if (o == null) {
			return false;
		}
		
		if (o == this) {
			return true;
		}
		
		if (o.getClass() != Term.class) {
			return false;
		}
		
		Term other = (Term) o;
		if (coef != other.coef) {
			return false;
		}

		if (exp != other.exp) {
			return false;
		}
		
		return true;
	}

	/**
	 * @return a string representation of the term
	 */
	@Override
	public String toString() {
		if (exp == 0) {
			return "" + coef;
		}
		if (exp == 1) {
			return "" + coef + "x";
		}
		if (coef == 1) {
			return "x^" + exp;
		}
		return "" + coef + "x^" + exp;
	}

}
