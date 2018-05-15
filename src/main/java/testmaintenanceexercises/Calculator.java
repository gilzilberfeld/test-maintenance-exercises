package testmaintenanceexercises;

public class Calculator {
	String display = "";
	
	public void press(String key) {
		if (key=="C")
			display = "";
		else
			display += key;
	}

	public String getDisplay() {
		if (display == "")
			return "0";
		return display;
	}

	public String getLastValueFor(String user) {
		return "-1";
	}

	public String getCurrentUser() {
		return "Gil";
	}

}
