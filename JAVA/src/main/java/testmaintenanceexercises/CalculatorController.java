package testmaintenanceexercises;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/calculator")
public class CalculatorController {
	@Autowired private Calculator calculator;
	
	@PostMapping(value ="/press")
	public ResponseEntity<?> press(@RequestParam(value = "key") String key) {
		calculator.press(key);
		return ResponseEntity.ok(null);
	}
	
	@GetMapping(value = "/display") 
	public ResponseEntity<String> getDisplay() {
		
		return new ResponseEntity<String>(calculator.getDisplay(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/restore")
	public ResponseEntity<?> getStoredValueForUser(@RequestParam(value = "user") String user) {
		calculator.getLastValueFor(user);
		return ResponseEntity.ok(null);
	}
	
	@GetMapping(value = "/currentUser")
	public String getCurrentUser() {
		return calculator.getCurrentUser();
	}
}
