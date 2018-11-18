package application.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Position {
    
	private SimpleStringProperty id;
	private SimpleStringProperty name;
	private CheckBox select;
	private ImageView editButton;
	private TextField pages;

	public Position(String id, String name) {
		this.id = new SimpleStringProperty(id);
		this.name = new SimpleStringProperty(name);	
		this.select = new CheckBox();	
		this.editButton = new ImageView(new Image("/application/resources/img/edit-icon.png"));
		this.pages = new TextField();
		
		this.pages.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            pages.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		
		this.pages.setText("5");
	}
	
	public TextField getPages() {
		return pages;
	}

	public void setPages(TextField pages) {
		this.pages = pages;
	}

	public void setName(String name) {
;        this.name.set(name);
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
	
	public boolean isSelected() {
		return this.select.isSelected();
	}
	
}
