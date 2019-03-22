package testmaintenanceexercises.approval;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.approvaltests.Approvals;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import testmaintenanceexercises.configurations.CalculatorTestConfiguration;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CalculatorTestConfiguration.class})
@DataJpaTest
@SpringBootTest
@AutoConfigureMockMvc
public class CalculatorTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Ignore
	@Test
	public void GetWithApprovals() throws Exception
	{
		MvcResult result = mockMvc.perform(get("/calculator/display"))
		.andExpect(status().isOk())
		.andReturn();
		
		Approvals.verify(result.getResponse().getContentAsString());

	}
}
