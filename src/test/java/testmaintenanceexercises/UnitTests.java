package testmaintenanceexercises;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Isolation;


public class UnitTests {
	@Test
	public void CancelTheFirstNumber() {
		Calculator c = new Calculator();
		c.press("1");
		c.press("C");
		String result = c.getDisplay();
		assertEquals("0", result);
	}

	@Test
	public void CancelTheFirstNumber2() {
		Calculator c = new Calculator();
		c.press("1");
		c.press("C");
		c.press("2");
		String result = c.getDisplay();
		assertEquals("2", result);
	}

	@Test
	@Category(Isolation.class)
	public void displayOnlyIfUserIsLoggedIn(){
		Calculator calc = new Calculator();
		if (calc.getCurrentUser() != null)
			assertEquals("0",calc.getDisplay());
	}
	
	@Value("memory")
	private String memory;
	
	@Test
	public void restoreMemoryAndContinue() {
		Calculator calculator = new Calculator();
		String lastStoredValue = calculator.getLastValueFor("Gil");
		if (lastStoredValue == memory) {
			calculator.press("2");
			String result = calculator.getDisplay();
			assertEquals("12", result);
		}
	}

	@Test
	public void CancelAfterOperation() {
		String result; 
		Calculator c = new Calculator();
		c.press("1");
		c.press("+");
		c.press("C");
		result = c.getDisplay();
	}

	@Test
 	public void NotEnoughInfo() {
		Calculator c = new Calculator();
		c.press("1");
		String result = c.getDisplay();
		assertTrue(result.equals("1"));
	}

	@Test
	public void RedundnatTest1() {
		Calculator c = new Calculator();
		c.press("1");
		c.press("2");
		String result = c.getDisplay();
		assertEquals(result, "12");
	}

	@Test
	public void RedundnatTest2() {
		Calculator c = new Calculator();
		c.press("9");
		c.press("5");
		String result = c.getDisplay();
		assertEquals(result, "95");
	}

	@Ignore
	@Test
	public void RedundnatTest4() {
		Calculator c = new Calculator();
		c.press("7");
		c.press("3");
		String result = c.getDisplay();
		assertEquals(result, "73");
	}
	
	@Test
	public void orderOfOperations() {
		/// This is a long test
		Calculator c = new Calculator();
		c.press("1");
		c.press("+");
		c.press("2");
		c.press("*");
		c.press("4");
		c.press("=");
		String result = c.getDisplay();
		assertEquals(result, "9");
	}

}
