package testmaintenanceexercises.integration;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import testmaintenanceexercises.Calculator;
import testmaintenanceexercises.User;
import testmaintenanceexercises.configurations.CalculatorTestConfiguration;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = {CalculatorTestConfiguration.class})
@DataJpaTest
@SpringBootTest
@AutoConfigureMockMvc
public class CalculatorTests{


	@Autowired
	private MockMvc mockMvc;
	
	
	@Test
	public void OverflowAfterMultiplePresses() throws Exception{
		mockMvc.perform(
				post("/calculator/press").param("key", "9"));
		mockMvc.perform(
				post("/calculator/press").param("key", "9"));
		mockMvc.perform(
				post("/calculator/press").param("key", "9"));
		mockMvc.perform(
				post("/calculator/press").param("key", "9"));
		mockMvc.perform(
				post("/calculator/press").param("key", "9"));
		mockMvc.perform(
				post("/calculator/press").param("key", "9"));
		MvcResult result = mockMvc.perform(get("/calculator/display"))
				.andExpect(status().isOk())
				.andReturn();

		assertEquals("E", result.getResponse().getContentAsString());
	}

	@Test
	public void DivisionByZero() throws Exception {
		mockMvc.perform(
				post("/calculator/press").param("key", "9"));
		mockMvc.perform(
				post("/calculator/press").param("key", "/"));
		mockMvc.perform(
				post("/calculator/press").param("key", "0"));
		mockMvc.perform(
				post("/calculator/press").param("key", "="));
		MvcResult result = mockMvc.perform(get("/calculator/display"))
		.andExpect(status().isOk())
		.andReturn();

		assertEquals("E", result.getResponse().getContentAsString());
	}

	@Ignore
	@Test
	public void CalculationsWithLastValueSaved() throws Exception {
		
		// Wait for server to answer
		MvcResult storedResult = 
				mockMvc.perform(
						get("/calculator/stored?user=Gil"))
						.andExpect(request().asyncStarted())
						.andReturn();
		
		String lastValue = (String) storedResult.getAsyncResult(1000);
		mockMvc.perform(
				post("/calculator/press").param("key", lastValue));
		mockMvc.perform(
				post("/calculator/press").param("key", "+"));
		mockMvc.perform(
				post("/calculator/press").param("key", "3"));
		mockMvc.perform(
				post("/calculator/press").param("key", "="));
		mockMvc.perform(get("/calculator/display"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.display").value("3"))
		.andReturn();
	}

	@Test
	public void displayingNegativeNumbers() {
		Calculator calc = new Calculator();
		calc.press("-");
		calc.press("2");
		calc.press("=");
		String result = calc.getDisplay();
		assertEquals("-2", result);
	}
	
	@Autowired TestEntityManager entityManager;
	@Before
	public void setup() {
		User user = new User();
		user.setName("Gil");
		user.setMemory((long) 2);
		entityManager.persistAndFlush(user);
	}
	
	@Test
	public void RestoreByUser() throws Exception {
		
		mockMvc.perform(post("/calculator/restore?user=Gil"))
				.andExpect(status().isOk());
		
		MvcResult result = mockMvc.perform(get("/calculator/display"))
				.andExpect(status().isOk())
				.andReturn();
		
		assertEquals(result.getResponse().getContentAsString(), "2");
	}
	
	@Test
	public void RestoreByUserAndContinue() throws Exception {

		mockMvc.perform(post("/calculator/restore?user=Gil"))
				.andExpect(status().isOk());
		
		mockMvc.perform(
				post("/calculator/press").param("key", "+"));
		
		MvcResult result = mockMvc.perform(get("/calculator/display"))
				.andExpect(status().isOk())
				.andReturn();

		
		assertEquals(result.getResponse().getContentAsString(), "2");
	}
	


	@Ignore
	@Test
	public void MultiParameterCalculation() throws Exception {
		mockMvc.perform(
				post("/calculator/press").param("key", "3"));
		mockMvc.perform(
				post("/calculator/press").param("key", "-"));
		mockMvc.perform(
				post("/calculator/press").param("key", "5"));
		mockMvc.perform(
				post("/calculator/press").param("key", "+"));
		mockMvc.perform(
				post("/calculator/press").param("key", "9"));
		mockMvc.perform(
				post("/calculator/press").param("key", "="));
		MvcResult result = mockMvc.perform(get("/calculator/display"))
				.andExpect(status().isOk())
				.andReturn();

		assertEquals (result.getResponse().getContentAsString(), "7");
	}

	@Test
	public void SimpleCalcuation() throws Exception {
		mockMvc.perform(
				post("/calculator/press").param("key", "1"));
		mockMvc.perform(
				post("/calculator/press").param("key", "+"));
		mockMvc.perform(
				post("/calculator/press").param("key", "2"));
		mockMvc.perform(
				post("/calculator/press").param("key", "="));
		MvcResult result = mockMvc.perform(get("/calculator/display"))
		.andExpect(status().isOk())
		.andReturn();

		assertEquals("3", result.getResponse().getContentAsString());
	}


	@Test
	public void pressingOpKeyDoesntChangeDisplay() throws Exception {
		mockMvc.perform(
				post("/calculator/press").param("key", "6"));
		mockMvc.perform(
				post("/calculator/press").param("key", "+"));
		MvcResult result = mockMvc.perform(get("/calculator/display"))
		.andExpect(status().isOk())
		.andReturn();
		
		assertEquals("6", result.getResponse().getContentAsString());
}

	@Test
	public void AnotherSimpleCalculation() throws Exception {
		mockMvc.perform(
				post("/calculator/press").param("key", "5"));
		mockMvc.perform(
				post("/calculator/press").param("key", "+"));
		mockMvc.perform(
				post("/calculator/press").param("key", "3"));
		mockMvc.perform(
				post("/calculator/press").param("key", "="));
		MvcResult result = mockMvc.perform(get("/calculator/display"))
		.andExpect(status().isOk())
		.andReturn();

		assertEquals("8", result.getResponse().getContentAsString());
	}

}
