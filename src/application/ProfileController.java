package application;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import application.DAO.Positions;
import application.DAO.Profiles;
import application.models.Position;
import application.models.Profile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProfileController extends MainController implements Initializable {
	
	@FXML
    private TableView<Profile> tableView;
	@FXML
	private TableColumn email;
	@FXML
	private TableColumn editButton;
	
	public void initialize(URL location, ResourceBundle resources) {
		
		email.setCellValueFactory(new PropertyValueFactory<Profile, String>("email"));
		editButton.setCellValueFactory(new PropertyValueFactory<Profile, String>("editButton"));
			
		Profiles db = cnx.getBean("Profiles", Profiles.class);
		
		tableView.setItems(db.getProfiles());
		

	}
	
	
}
