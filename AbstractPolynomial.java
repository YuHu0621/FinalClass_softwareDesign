
/**
 * AbstractPolynomial is the super class of both DensePolynomial and SparsePolynomial
 * @author yuhu
 *
 */
 public abstract class AbstractPolynomial implements Polynomial {

	@Override
	/**
	 * return the minimum exponent of the polynomial
	 * @return return the minimum exponent
	 */
	public abstract int getMinExponent();

	@Override
	/**
	 * return the maximum exponent of the polynomial
	 * @return return the maximum exponent
	 */
	public abstract int getMaxExponent();

	@Override
	/**
	 * return the coefficient of an exponent
	 * return 0 if there's no term related to the exponent
	 * @return return the coefficient at the exponent. return 0 if there's no term at certain exponent
	 * @param exp the exponent
	 */
	public abstract int getCoeff(int exp);

	@Override
	/**
	 * return true if the polynomial is zero
	 * otherwise return false
	 * @return return true if the polynomial is constant 0
	 * 
	 */
	public abstract boolean isZero();

	@Override
	/**
	 * return a polynomial that add q to this
	 * @param q polynomial
	 * @return this+q
	 */
	public abstract Polynomial add(Polynomial q);

	@Override
	/**
	 * return a polynomial that is the current polynomial multiplied by the factor
	 * @param factor multiplication factor
	 * @return this*factor
	 */
	public abstract Polynomial multiply(int factor) ;

	@Override
	/**
	 * return a polynomial that subtract q. This and q is not changed
	 * @param q polynomial
	 * @return this - q
	 */
	public Polynomial subtract(Polynomial q) {
		return add(q.multiply(-1));
		
	}

	@Override
	/**
	 * return a polynomial that is the negative of the current polynomial
	 * this polynomial is not changed
	 * @return -this
	 */
	public Polynomial minus() {
		return multiply(-1);
	}

	@Override
	/**
	 * Check if the polynomial is well-formed. 
	 * @return Return true if the polynomial satisfies the class invariants
	 */
	public abstract boolean wellFormed();

}
