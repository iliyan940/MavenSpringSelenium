package application.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Profile {
	
	private SimpleStringProperty email;
	private ImageView editButton;
	
	public Profile(String email) {
		this.email = new SimpleStringProperty(email);
		this.editButton = new ImageView(new Image("/application/resources/img/edit-icon.png"));
	}
	
	public ImageView getEditButton() {
		return editButton;
	}

	public void setEditButton(ImageView editButton) {
		this.editButton = editButton;
	}

	public String getEmail() {
		return email.get();
	}

	public void setEmail(SimpleStringProperty email) {
		this.email = email;
	}
}
