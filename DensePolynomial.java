

/**
 *  A dense polynomial class represents a polynomial with an array of coefficients.  
 * @author yuhu
 *
 */
final public class DensePolynomial extends AbstractPolynomial implements Polynomial {

	//variables
	final private int[] polyArr;

	/**
	 * Creates the zero polynomial 
	 */
	public DensePolynomial(){
		polyArr = new int[0];
	
		assert wellFormed();
	}
	/**
	 * Creates a polynomial array with a single coefficient
	 * @param coeff coefficient 
	 * @param exponent x^exponent
	 */
	
	public DensePolynomial(int coeff, int exponent){
		if(coeff == 0){
			polyArr = new int[0];
	
		}
		else
		{
			polyArr = new int[exponent+1];
			polyArr[exponent]=coeff;
		}
		assert wellFormed();
	}
	
	/**
	 * Return the smallest exponent of the polynomial.
	 * @return return the smallest exponent
	 */
	@Override
	public int getMinExponent() {
		if(isZero()){
			return 0;
		}
		int min = 0;
		for(int i = 0; i < polyArr.length; i++){
			if(polyArr[i]!=0){
				min = i;
				break;
			}
		}
		return min;
	}

	/**
	 * Return the largest exponent of the polynomial
	 * @return return the largest exponent
	 */
	@Override
	public int getMaxExponent() {
		if(isZero()){
			return 0;
		}
		return polyArr.length -1;
	}

	/**
	 * Get the coefficient for the term with the given exponent
	 * @param exp the exponent
	 * @return the coefficient for the term with the given exponent. Return 0 if there is no term for the exponent.  
	 * 
	 */
	@Override
	public int getCoeff(int exp) {
		if(exp<polyArr.length && exp >=0){
		return polyArr[exp];
		}else{
			return 0;
		}
	}
	
	/**
	 * Check if the polynomial is 0
	 * @return Return true if this polynomial is the constant 0.
	 */
	@Override
	public boolean isZero() {
		if(polyArr.length == 0){
			return true;
		}else{
			for(int i = 0; i < polyArr.length; i++){
				if(polyArr[i]!=0){
					return false;
				}
			}
			
			return true;
		}
	}

	/**
	 * Return the sum of this polynomial and q.  Neither this nor q are changed.
	 * if a sparsePolynomial is passed in, return a sparsePolynomial
	 * @param q a polynomial
	 * @return this + q
	 * @exception nullPointerException is thrown if q is null
	 */
	@Override
	public Polynomial add(Polynomial q) {
		if(q == null){
			throw new NullPointerException("NullPointerException");
		}
		if (q instanceof DensePolynomial) {
			return addDense((DensePolynomial) q);
		}else{
			SparsePolynomial newPoly = (SparsePolynomial) q;
			for(int i  = 0; i < polyArr.length; i++){
				SparsePolynomial t = new SparsePolynomial(polyArr[i], i);
				newPoly = (SparsePolynomial) newPoly.add(t);
			}
			assert newPoly.wellFormed();
			return newPoly;
		}
		
	
	}

	/**
	 * Return the sum of this polynomial and q.  Neither this nor q are changed.
	 * @param q the polynomial
	 * @return return the sum of this polynomial and q
	 */
	public DensePolynomial addDense(DensePolynomial q){
		
		if(q.isZero()){
			DensePolynomial dp = this;
			return dp;
		}
		if(isZero()){
			return q;
		}
		
		int a = polyArr.length;
		int b = q.polyArr.length;
		int max = Math.max(a, b);
		DensePolynomial dp = new DensePolynomial(1, max-1);

		int counter = 0;
		
		while(counter < max){
			
			dp.polyArr[counter]= getCoeff(counter)+q.getCoeff(counter);
			counter ++;
		}
		if(dp.isZero()){
			dp = new DensePolynomial();
			return dp;
		}
		dp = dp.truncate();
		assert dp.wellFormed();
		return dp;
		
	}
	
	/**
	 * truncate the DensePolynomial Array
	 * @return the truncated array
	 */
	private DensePolynomial truncate(){
		
		int i = polyArr.length -1;
		while(i>=0){
			if(polyArr[i]!=0){
				break;
			}
			i--;
		}
		DensePolynomial dp = new DensePolynomial(polyArr[i], i);
		for(int j = 0; j <= i; j++){
			dp.polyArr[j] = polyArr[j];
		}
		
		assert dp.wellFormed();
		return dp;
	}
	
	/**
	 * Return a polynomial that is the product of this and factor. 
	 * @param multiplication factor
	 * @return polynomial * factor
	 */
	@Override
	public Polynomial multiply(int factor) {
		if(isZero()){
			return this;
		}
		if(factor == 0){
			return new DensePolynomial();
		}
		DensePolynomial dp = new DensePolynomial(polyArr[polyArr.length-1]*factor, polyArr.length-1);
		
		for(int i = 0; i < polyArr.length; i++){
			dp.polyArr[i]=polyArr[i]*factor;
		}

		
		assert dp.wellFormed();
		return dp;
	}


	/**
	 * Return true if the polynomial satisfies the class invariants
	 * @return return false is the array is null. return false if the array has 0 in the end. Return true if the last element in the array is non-zero. Return true if the array size is 0.
	 * 
	 */
	@Override
	public boolean wellFormed() {
		if(polyArr == null){
			return false;
		}
		if(polyArr.length==0){
			return true;
		}
		
			if(polyArr[polyArr.length-1]==0){
				return false;
			}
			else{
				return true;
			}
		}
		
	
	
	/**
	 *  Returns true if o is an equivalent polynomial.  That is it contains the same coefficients
	 * for the same exponents.
	 * @param object
	 * @return return true if the two polynomial are the same. return false if the object is not a polynomial. 
	 * @exception nullPointerException is thrown if the object is null.
	 */
	public boolean equals(Object o){
		if(o == null){
			throw new NullPointerException("NullPointerException is thrown");
		}
		if(o == this){
			return true;
		}
		
		if(!(o instanceof Polynomial)){
			return false;
		}
		
		// If o is a Polynomial but not a DensePolynomial, we rely on the string representations
		// being the same
		if(!(o instanceof DensePolynomial)){
			String oString = o.toString();
			if(oString.equals(this.toString())){
				return true;
			}else{
				return false;
			}
		}
		DensePolynomial obj = (DensePolynomial) o;
		if(obj.polyArr.length != polyArr.length){
			return false;
		}
		
		// All the easy tests passed.  Compare each index of the array.
		for(int i = 0; i < polyArr.length; i++){
			if(obj.polyArr[i]!= polyArr[i]){
				return false;
			}
		}
		return true;
	}

	/**
	 * return the string representation of a dense polynomial
	 * @return return the string representation of the polynomial. start from exponent is 0. when the coefficient is 0, the term is not presented. 
	 */
	public String toString(){
		StringBuilder builder = new StringBuilder();
		if(isZero()){
			return "0";
		}
		int i = 0; 
		while(i < polyArr.length){
			if(polyArr[i]!=0){
				if(i == 0){
					builder.insert(0, polyArr[i]);
				}
				if(i == 1){
					if(polyArr[i-1]==0){
						builder.insert(0, polyArr[i]+"x");
					}else{
						builder.insert(0, polyArr[i]+"x"+" + ");
					}
				}
				if(i > 1){
					builder.insert(0, polyArr[i]+"x^"+i + " + ");
				}
			}
			i++;
		}
		return builder.toString();

	}
	
	public static void main(String[]args){
		DensePolynomial zero = new DensePolynomial();
		
		System.out.println("zero: " + zero.toString());
		DensePolynomial one = new DensePolynomial(1,0);
		System.out.println("one: " + one.toString());
		DensePolynomial twoX = new DensePolynomial(2,1);
		System.out.println("one plus twoX: " + one.add(twoX).toString());
		DensePolynomial minusTwoX = new DensePolynomial(-2,1);
		System.out.println("one plus TwoX plus minuns TwoX: "+one.add(twoX).add(minusTwoX).toString());
		SparsePolynomial minus100X = new SparsePolynomial(1, -100);
		Polynomial onePlusMinus100X = one.add(minus100X);
		System.out.println("one plus x to -100: "+ onePlusMinus100X.toString());
		one.add(twoX);
		if(one.getCoeff(1)==0){
			System.out.println("Add method test: DensePolynomial is an immutable class.");
		}else{
			System.out.println("Add method test: DensePolynomial is not an immutable class.");
		}
		
		one.subtract(twoX);
		if(one.getCoeff(1)==0){
			System.out.println("Subtract method test: DensePolynomial is an immutable class.");
		}else{
			System.out.println("Subtract method test: DensePolynomial is not an immutable class.");
		}
		one.multiply(2);
		if(one.getCoeff(0)==1){
			System.out.println("Multiply method test: DensePolynomial is an immutable class.");
		}else{
			System.out.println("Multiply method test: DensePolynomial is not an immutable class.");
		}
		one.minus();
		if(one.getCoeff(0)==1){
			System.out.println("Minus method test: DensePolynomial is an immutable class.");
		}else{
			System.out.println("Minus method test: DensePolynomial is not an immutable class.");
		}
		
	}
	
}
