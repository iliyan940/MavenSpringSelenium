package application.models;

import javafx.beans.property.SimpleStringProperty;

public class Profile {
	
	private int id;
	private SimpleStringProperty email;
	private String password;
	
	public Profile(Integer id ,String email, String password) {
		this.id = id;
		this.email = new SimpleStringProperty(email);
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email.get();
	}

	public void setEmail(SimpleStringProperty email) {
		this.email = email;
	}
	
	
}
