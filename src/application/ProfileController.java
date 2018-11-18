package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


import application.DAO.Profiles;
import application.models.Profile;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ProfileController extends MainController implements Initializable {
	
	@FXML
    private TableView<Profile> tableView;
	@FXML
	private TableColumn<Profile,String> emailColumn;
	
	@FXML
	private TextField email;
	@FXML
	private PasswordField password;
	@FXML
	private PasswordField salt;
	@FXML
	private Button deleteButton;
	@FXML
	private Button editButton;
	@FXML
	private Button updateButton;
	@FXML
	private Button addButton;
	@FXML
	private Button chooseButton;
	
	private Stage stage ;
	private HomeController homeController;
	
	private Map<String, Integer> editInfo = new HashMap<String, Integer>();
	private Profiles profiles = cnx.getBean("Profiles", Profiles.class);

	
	public void initialize(URL location, ResourceBundle resources) {
		emailColumn.setCellValueFactory(new PropertyValueFactory<Profile, String>("email"));
		tableView.setItems(profiles.get());
	}
	
	public void add() throws Exception {
		String email = this.email.getText();
		String password = this.password.getText();
		String salt = this.salt.getText();
		
		Crypt crypt = new Crypt(salt);
		String enctyptedPassword = crypt.encrypt(password);
		
		Profiles profiles = cnx.getBean("Profiles", Profiles.class);
		int id = profiles.Insert(email, enctyptedPassword);
		
		updateTableView(id, email, password, salt);		
	}
	
	public void updateTableView(Integer id, String email, String password, String salt) {
		Profile newProfile = new Profile(id, email, password);
		tableView.getItems().add(newProfile);
		
		this.email.clear();
		this.password.clear();
		this.salt.clear();
	}
	
	public void edit() {
		Profile profile = this.tableView.getSelectionModel().getSelectedItem();
		this.email.setText(profile.getEmail());
		this.password.setText(profile.getPassword());
		
		this.editInfo.put("id", profile.getId());
		this.editInfo.put("index", this.tableView.getSelectionModel().getFocusedIndex());
		
		toggleEditButtons();
	}
	
	public void update() {
		Profile updatedProfile = new Profile(editInfo.get("id"), this.email.getText(), this.password.getText());
		profiles.update(updatedProfile);
		
		SimpleStringProperty tes = new SimpleStringProperty(this.email.getText());
		this.tableView.getItems().get(editInfo.get("index")).setEmail(tes);
		this.tableView.refresh();
		
		 toggleEditButtons();
		 clearTextFields();
	}
	
	public void delete() {
		 profiles.delete(this.editInfo.get("id"));
		 clearTextFields();
		 this.tableView.getItems().remove((int) this.tableView.getSelectionModel().getFocusedIndex());
		 this.deleteButton.setVisible(false);
		 this.updateButton.setVisible(false);
		 
		 this.addButton.setVisible(true);
	}
	
	public void choose() {
		Profile selectedProfile = this.tableView.getSelectionModel().getSelectedItem();
		this.profiles.setActive(selectedProfile.getId());
		this.homeController.selectedProfile.setText(selectedProfile.getEmail());
		this.stage.close();
	}
	
	public void toggleEditButtons() {
		
		List<Button> buttons = new ArrayList<Button>();
		buttons.add(this.updateButton);
		buttons.add(this.deleteButton);
		buttons.add(addButton);
		
		for(int i = 0; i < buttons.size(); i++) {
			if(!buttons.get(i).isVisible()) {
				buttons.get(i).setVisible(true);
			} else {
				buttons.get(i).setVisible(false);
			}
		}
		
	}
	
	public void clearTextFields() {
		 this.email.clear();
		 this.password.clear();
		 this.salt.clear();
	}
	
	public void openWindow(HomeController homeController) {
		FXMLLoader root;
		try {
			root = new FXMLLoader(getClass().getResource("resources/Profiles.fxml"));
			
			BorderPane borderPane = root.load();
			Scene scene = new Scene(borderPane	);
			scene.getStylesheets().add(getClass().getResource("resources/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setMinWidth(418);
			
			stage.show();
			
			ProfileController controller = root.getController();
			controller.setStage(stage);
			controller.setHomeController(homeController);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void setHomeController(HomeController homeController) {
		this.homeController = homeController;
	}
	
}
