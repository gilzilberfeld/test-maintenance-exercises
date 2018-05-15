package testmaintenanceexercises;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationTests{


	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;
	
	
	@Test
	public void OverflowAfterMultiplePresses() throws Exception{
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
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
		mockMvc.perform(get("/calculator/display"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.display").value("E"))
				.andReturn();
	}

	@Test
	public void DivisionByZero() throws Exception {
		// Check message instead of display
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		mockMvc.perform(
				post("/calculator/press").param("key", "9"));
		mockMvc.perform(
				post("/calculator/press").param("key", "/"));
		mockMvc.perform(
				post("/calculator/press").param("key", "0"));
		mockMvc.perform(
				post("/calculator/press").param("key", "="));
		mockMvc.perform(get("/calculator/display"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.status").value("Division By Zero Error"))
		.andReturn();
	}

	@Test
	public void CalculationsWithLastValueSaved() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		
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
	
	@Test
	public void DependentOnLoggedOnUser() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		MvcResult currentUserResult = 
				mockMvc.perform(
						get("/calculator/currentUser"))
						.andReturn();


		if (currentUserResult.getResponse()
				.getContentAsString()
				.contains("Gil"))
		{
			mockMvc.perform(
					post("/calculator/press").param("key", "+"));
			mockMvc.perform(
					post("/calculator/press").param("key", "3"));
			mockMvc.perform(get("/calculator/display"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.display").value("3"))
			.andReturn();
		}
		else
			fail("Wrong user");
	}


	@Test
	public void AlsoDependentOnUser() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		// If user is logged on
		// “5”,”+”,"
	}

	@Test
	public void MultiParameterCalculation() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
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
		mockMvc.perform(get("/calculator/display"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.display").value("7"))
				.andReturn();
	}

	@Test
	public void SimpleCalcuation() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		mockMvc.perform(
				post("/calculator/press").param("key", "1"));
		mockMvc.perform(
				post("/calculator/press").param("key", "+"));
		mockMvc.perform(
				post("/calculator/press").param("key", "2"));
		mockMvc.perform(
				post("/calculator/press").param("key", "="));
		mockMvc.perform(get("/calculator/display"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.display").value("3"))
		.andReturn();
	}


	@Test
	public void DependingOnFile() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		//

	}

	@Test
	public void pressinOpKeyDoesntChangeDisplay() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		mockMvc.perform(
				post("/calculator/press").param("key", "6"));
		mockMvc.perform(
				post("/calculator/press").param("key", "+"));
		mockMvc.perform(get("/calculator/display"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.display").value("6"))
		.andReturn();
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
		mockMvc.perform(get("/calculator/display"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.display").value("8"))
		.andReturn();
	}

}
