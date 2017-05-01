/**
 * This interface can be used to represent polynomials with a single variable
 * where all of the coefficients and exponents are integers, such as 
 *   3 
 *   x^2 + 1 
 *   4 + x^-3
 * 
 * @author Barbara Lerner
 * @version Oct 4, 2015
 * 
 */
public interface Polynomial {
	

	/**
	 * Returns the smallest exponent with a non-zero coefficient
	 * 
	 * @return the smallest exponent with a non-zero coefficient. If all terms
	 *         have zero exponents, it returns 0.
	 */
	public int getMinExponent();
	
	/**
	 * Returns the largest exponent with a non-zero coefficient of the polynomial.
	 * 
	 * @return the largest exponent with a non-zero coefficient. If all terms
	 *         have zero exponents, it returns 0.
	 */
	public int getMaxExponent();

	/**
	 * Returns the coefficient corresponding to the given exponent. Returns 0 if
	 * there is no term with that exponent in the polynomial.
	 * 
	 * @param exp the exponent whose coefficent is returned.
	 * @return the coefficient of the term of whose exponent is d.
	 */
	public int getCoeff(int exp);

	/**
	 * @return true if the polynomial represents the zero constant
	 */
	public boolean isZero();

	/**
	 * Returns a polynomial by adding the parameter to this. Neither this nor
	 * the parameter are modified.
	 * 
	 * @param q the polynomial to add to this. q should not be null.
	 * @return this + q
	 */
	public Polynomial add(Polynomial q);

	/**
	 * Returns a polynomial by multiplying this by a constant. this
	 * is not modified. 
	 * 
	 * @param factor the constant to multiply this by.
	 * @return this * factor
	 */
	public Polynomial multiply(int factor);

	/**
	 * Returns a polynomial by subtracting the parameter from this. Neither this
	 * nor the parameter are modified.
	 * 
	 * @param q the polynomial to subtract from this. q should not be null.
	 * @return this - q
	 */
	public Polynomial subtract(Polynomial q);

	/**
	 * Returns a polynomial by negating this. this is not modified.
	 * 
	 * @return -this
	 */
	public Polynomial minus();

	/**
	 * Returns true if the object's class invariant holds
	 * 
	 * @return true iff the class invariant holds
	 */
	public boolean wellFormed();
}
