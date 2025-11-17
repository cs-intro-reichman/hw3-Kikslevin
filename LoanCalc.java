// Computes the periodical payment necessary to pay a given loan.
public class LoanCalc {
	
	static double epsilon = 0.001;  // Approximation accuracy
	static int iterationCounter;    // Number of iterations 
	
	// Gets the loan data and computes the periodical payment.
    // Expects to get three command-line arguments: loan amount (double),
    // interest rate (double, as a percentage), and number of payments (int).  
	public static void main(String[] args) {		
		// Gets the loan data
		double loan = Double.parseDouble(args[0]);
		double rate = Double.parseDouble(args[1]);
		int n = Integer.parseInt(args[2]);
		System.out.println("Loan = " + loan + ", interest rate = " + rate + "%, periods = " + n);

		// Computes the periodical payment using brute force search
		System.out.print("\nPeriodical payment, using brute force: ");
		System.out.println((int) bruteForceSolver(loan, rate, n, epsilon));
		System.out.println("number of iterations: " + iterationCounter);

		// Computes the periodical payment using bisection search
		System.out.print("\nPeriodical payment, using bi-section search: ");
		System.out.println((int) bisectionSolver(loan, rate, n, epsilon));
		System.out.println("number of iterations: " + iterationCounter);
	}

	// Computes the ending balance of a loan, given the loan amount, the periodical
	// interest rate (as a percentage), the number of periods (n), and the periodical payment.
	private static double endBalance(double loan, double monthlyrate, int n, double payment) {	
		double balance = loan;
		for (int i = 0; i < n; i++) {
			balance = balance * (1 + monthlyrate) - payment;
		}
		return balance;
	}

	// Uses sequential search to compute an approximation of the periodical payment
	// that will bring the ending balance of a loan close to 0.
	// Given: the sum of the loan, the periodical interest rate (as a percentage),
	// the number of periods (n), and epsilon, the approximation's accuracy
	// Side effect: modifies the class variable iterationCounter.
    public static double bruteForceSolver(double loan, double rate, int n, double epsilon) {
		double monthlyrate = rate / 100 / 12;
		double increment = 0.01;
		double guess = loan / n + loan * monthlyrate;   //initial guess which doesn't consider interest
		iterationCounter = 0;
		int maxIterations = 2000000;

		while (Math.abs(endBalance(loan, monthlyrate, n, guess)) >= epsilon && iterationCounter < maxIterations){
			guess += increment;
			iterationCounter++;
		}
	return guess;
}
    
    // Uses bisection search to compute an approximation of the periodical payment 
	// that will bring the ending balance of a loan close to 0.
	// Given: the sum of the loan, the periodical interest rate (as a percentage),
	// the number of periods (n), and epsilon, the approximation's accuracy
	// Side effect: modifies the class variable iterationCounter.
    public static double bisectionSolver(double loan, double rate, int n, double epsilon) { 
	double monthlyrate = rate / 100 / 12;
	double L = 0.0;     // Lower bound
	double H = loan * 2;  // Upper bound
	iterationCounter = 0;
	double G;

	   while (H - L > epsilon){
		G = (L + H) / 2;
		iterationCounter++;
		if (endBalance(loan, monthlyrate, n, G) > 0) {
		L = G;
	    } else {
		H = G;
	   }
	}
	   return (L + H) / 2;
	}
}