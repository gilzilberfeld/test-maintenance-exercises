package testmaintenanceexercises.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import testmaintenanceexercises.Calculator;
import testmaintenanceexercises.CalculatorController;

@Configuration
@Import(JpaConfiguration.class)
public class CalculatorTestConfiguration {
	
	@Bean CalculatorController controller() {
		return new CalculatorController();
	}
	
	@Bean Calculator calculator() {
		return new Calculator();
	}
}

