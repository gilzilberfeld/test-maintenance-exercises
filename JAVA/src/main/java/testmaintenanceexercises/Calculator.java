package testmaintenanceexercises;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;


public class Calculator {
	String display = "";
	int lastArgument = 0;
	int result = 0; 
	Boolean newArgument = false;
	Boolean shouldReset = true;
	
	OperationType lastOperation;

	@Autowired 
	public UserRepository userRepository;
	
	public void press(String key) {
		if (key.equals("C"))
			display = "";
		else {
			if (key.equals("+")) {
				lastOperation = OperationType.Plus;	
				lastArgument =  Integer.parseInt(display);
				newArgument = true;
			} else {
				if (key.equals("/")) {
					lastOperation = OperationType.Div;	
					lastArgument =  Integer.parseInt(display);
					newArgument = true;
				}
				else if (key.equals("=")) {
					int currentArgument = Integer.parseInt(display);
					if (lastOperation == OperationType.Plus) {
						display = Integer.toString(lastArgument + currentArgument);
					}
					if (lastOperation == OperationType.Div && currentArgument == 0) {
						display = "Division By Zero Error";
					}
					shouldReset = true;
				}
				else {
					if (shouldReset) {
						display = "";
						shouldReset = false;
					}
					if (newArgument) {
						display ="";
						newArgument = false;
					}
					display += key;
				}
			}
		}
	}

	public String getDisplay() {
		if (display.equals(""))
			return "0";
		if (display.length() > 5)
			return "E";
		return display;
	}

	public void getLastValueFor(String userName) {
		User user = getUserByName(userName);
		if (user != null) {
			display = user.getMemory().toString();
			shouldReset = false;
		}
	}

	private User getUserByName(String userName) {
		User criterion = new User();
		criterion.setName(userName);
		criterion.setMemory(null);
		
		Example<User> filter = Example.of(criterion);
		Optional<User> user = userRepository.findOne(filter);
		if (user.isPresent())
			return user.get();
		else
			return null;
	}

	public String getCurrentUser() {
		return "Gil";
	}

}