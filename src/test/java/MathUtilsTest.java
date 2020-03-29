import edu.baylor.csi3471.netime_planner.util.MathUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MathUtilsTest {
	private static final Logger LOGGER  = Logger.getLogger(MathUtilsTest.class.getName());
	
	private static final int MAX_VALUE = 1000;
	
	private static int NUM_OF_PRIMES = 10000;
	
	private static int[] primes = new int[NUM_OF_PRIMES];
	
	private static void assertEqualsLCM(int a, int b, int val) {
		Assertions.assertEquals(val, MathUtils.LCM(a, b),"a: " + a + " b: " + b);
		Assertions.assertEquals(val, MathUtils.LCM(b, a),"a: " + a + " b: " + b);
	}
	
	private static void assertEqualsGCD(int a, int b, int val) {
		Assertions.assertEquals(val, MathUtils.GCD(a, b),"a: " + a + " b: " + b);
		Assertions.assertEquals(val, MathUtils.GCD(b, a),"a: " + a + " b: " + b);
	}
	
	@BeforeAll
	public static void initializePrimeArray() {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("primes"));
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.WARNING, "File not found", e);
		}
		
		for (int i = 0; i < NUM_OF_PRIMES; i++) {
			
			int prime = scanner.nextInt();
			primes[i] = prime;
			
			if (!scanner.hasNextInt()) {
				NUM_OF_PRIMES = i;
				LOGGER.info("# of primes: " + NUM_OF_PRIMES);
				break;
			}
		}
	}
	
	@Test
	public void testPrimeArray() {
		int[] testArray = {2,3,5,7,11,13,17,19,23,29};
		for (int i = 0; i < testArray.length; i++) {
			if (primes[i] != testArray[i]) {
				Assertions.assertTrue(false, primes[i] + " does not equal " + testArray[i]);
			}
			Assertions.assertTrue(true, "The values were equal.");
		}
	}
	
	@Test
	public void LCMTest1() {
		for (int i = 1; i < MAX_VALUE; i++) {
			assertEqualsLCM(1, i, i);
		}
	}
	
	@Test
	public void LCMTest2() {
		for (int i = 0; i < MAX_VALUE; i++) {
			assertEqualsLCM(0, i, 0);
		}
	}
	
	@Test
	public void LCMTest3() {
		for (int i = 2; i < MAX_VALUE; i+=2) {
			assertEqualsLCM(i,i/2,i);
		}
	}
	
	@Test
	public void LCMTest4() {
		for (int i = 3; i < MAX_VALUE; i+=3) {
			assertEqualsLCM(i,i/3,i);
		}
	}
	
	@Test
	public void LCMTest5() {
		for (int i = 1; i < NUM_OF_PRIMES; i++) {
			int prime1 = primes[i-1];
			int prime2 = primes[i];
			assertEqualsLCM(prime1, prime2, prime1*prime2);
		}
	}
	
	@Test
	public void GCDTest1() {
		for (int i = 1; i < MAX_VALUE; i++) {
			assertEqualsGCD(1, i, 1);
		}
	}
	
	@Test
	public void GCDTest2() {
		for (int i = 2; i < MAX_VALUE; i+=2) {
			assertEqualsGCD(i,i/2,i/2);
		}
	}
	
	@Test
	public void GCDTest3() {
		for (int i = 3; i < MAX_VALUE; i+=3) {
			assertEqualsGCD(i,i/3,i/3);
		}
	}
	
	@Test
	public void GCDTest4() {
		for (int i = 2; i < MAX_VALUE; i++) {
			assertEqualsGCD(2,i, (i % 2 == 0 ? 2 : 1));
		}
	}
	
	@Test
	public void GCDTest5() {
		for (int i = 1; i < MAX_VALUE; i++) {
			assertEqualsGCD(0, i, i);
		}
	}
	
	@Test
	public void GCDTest6() {
		for (int i = 1; i < NUM_OF_PRIMES; i++) {
			int prime1 = primes[i-1];
			int prime2 = primes[i];
			assertEqualsLCM(prime1, prime2, 1);
		}
	}
	
}
