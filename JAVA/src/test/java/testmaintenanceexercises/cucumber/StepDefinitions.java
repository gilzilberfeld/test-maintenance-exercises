package testmaintenanceexercises.cucumber;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import testmaintenanceexercises.configurations.CalculatorTestConfiguration;

@ContextConfiguration(classes = {CalculatorTestConfiguration.class})
@DataJpaTest
@SpringBootTest
@AutoConfigureMockMvc
public class StepDefinitions {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Given("A reset calculator")
	public void a_reset_calculator() {
	    
	}

	@When("The user presses {string}")
	public void the_user_presses(String key) throws Exception {
		mockMvc.perform(
				post("/calculator/press").param("key", key))
				.andExpect(status().isOk());
	}

	
	@Then("The calculator displays {string}")
	public void the_calculator_displays(String result) throws Exception {
		MvcResult response = mockMvc.perform(get("/calculator/display"))
				.andExpect(status().isOk())
				.andReturn();

		assertEquals (result, response.getResponse().getContentAsString());	
	}

}
