package testmaintenanceexercises.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import testmaintenanceexercises.Calculator;
import testmaintenanceexercises.User;


public class CalculatorTests {
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
	public void displayOnlyIfUserIsLoggedIn(){
		Calculator calc = new Calculator();
		if (calc.getCurrentUser() != null)
			assertEquals("0",calc.getDisplay());
	}
	
	@Test
	public void restoreMemoryAndContinue() {
		
		Calculator calculator = new Calculator();
		MockUserRepository mockUserRepository = new MockUserRepository();
		User mockUser = new User();
		mockUser.setName("Gil");
		mockUser.setMemory(2L);
		mockUserRepository.mockUser = mockUser;
		calculator.userRepository = mockUserRepository;
		calculator.getLastValueFor("Gil");
		String lastStoredValue = calculator.getDisplay();
		if (lastStoredValue.equals("2")) {
			calculator.press("3");
			String result = calculator.getDisplay();
			assertEquals("23", result);
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
	public void TwoNumbers() {
		Calculator c = new Calculator();
		c.press("1");
		c.press("2");
		String result = c.getDisplay();
		assertEquals(result, "12");
	}

	@Test
	public void AnotherTwoNumbers() {
		Calculator c = new Calculator();
		c.press("9");
		c.press("5");
		String result = c.getDisplay();
		assertEquals(result, "95");
	}

	@Ignore
	@Test
	public void OtherTwoNumbers() {
		Calculator c = new Calculator();
		c.press("7");
		c.press("3");
		String result = c.getDisplay();
		assertEquals(result, "73");
	}
	
	@Ignore
	@Test
	public void orderOfOperations() {
		Calculator c = new Calculator();
		c.press("1");
		c.press("+");
		c.press("8");
		c.press("/");
		c.press("4");
		c.press("=");
		String result = c.getDisplay();
		assertEquals(result, "3");
	}

}
