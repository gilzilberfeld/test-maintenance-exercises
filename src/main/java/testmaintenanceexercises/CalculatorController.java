package testmaintenanceexercises;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/calculator")
public class CalculatorController {
	private Calculator calculator;
	
	public CalculatorController() {
		calculator = new Calculator();
	}
	
	@RequestMapping(value ="/press", method = RequestMethod.POST)
	public void press(@RequestParam(value = "key") String key) {
		calculator.press(key);
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.GET) 
	public DisplayResult getDisplay() {
		
		DisplayResult dr = new DisplayResult();
		dr.setDisplay(calculator.getDisplay());
		dr.setStatus("OK");
		
		return dr;
	}
	
	@RequestMapping(value = "/stored", method = RequestMethod.GET)
	public String getStoredValueForUser(@RequestParam(value = "user") String user) {
		return calculator.getLastValueFor(user);
	}
	
	@RequestMapping(value = "/currentUser", method = RequestMethod.GET)
	public String getCurrentUser() {
		return calculator.getCurrentUser();
	}
}
