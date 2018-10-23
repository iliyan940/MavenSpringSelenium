package application.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Position {
    
	private SimpleStringProperty id;
	private SimpleStringProperty name;
	private CheckBox select;
	private ImageView editButton;

	public Position(String id, String name) {
		this.id = new SimpleStringProperty(id);
		this.name = new SimpleStringProperty(name);	
		this.select = new CheckBox();	
		this.editButton = new ImageView(new Image("/application/resources/img/edit-icon.png"));
	}
	
	public void setName(String name) {
        this.name.set(name);
    }
	
	public String getName() {
		return name.get();
	}

	public String getId() {
		return id.get();
	}
	
	public CheckBox getSelect() {
		return select;
	}
	
	public void setSelect(CheckBox select) {
		this.select = select;
	}
	
	public ImageView getEditButton() {
		return editButton;
	}
	
	public void setEditButton(ImageView editButton) {
		this.editButton = editButton;
	}
}
