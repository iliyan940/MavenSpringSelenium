package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import application.DAO.Positions;
import application.DAO.Profiles;
import application.models.Profile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProfileController extends MainController implements Initializable {
	
	@FXML
    private TableView<Profile> tableView;
	@FXML
	private TableColumn email;
	@FXML
	private TableColumn editButton;
	
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private PasswordField salt;
	
	public void initialize(URL location, ResourceBundle resources) {
		email.setCellValueFactory(new PropertyValueFactory<Profile, String>("email"));
		editButton.setCellValueFactory(new PropertyValueFactory<Profile, String>("editButton"));
		Profiles db = cnx.getBean("Profiles", Profiles.class);
		tableView.setItems(db.getProfiles());
	}
	
	public void addProfile() throws Exception {
		String username = this.username.getText();
		String password = this.password.getText();
		String salt = this.salt.getText();
		
		Crypt crypt = new Crypt(salt);
		String enctyptedPassword = crypt.encrypt(password);
		
		updateTableView(username, password, salt);		
		
		Profiles profiles = cnx.getBean("Profiles", Profiles.class);
		profiles.Insert(username, enctyptedPassword);
	}
	
	public void updateTableView(String username, String password, String salt) {
		
		Profile newProfile = new Profile(username);
		tableView.getItems().add(newProfile);
		
		this.username.clear();
		this.password.clear();
		this.salt.clear();
	}
	
	
}
