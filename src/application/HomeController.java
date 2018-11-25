package application;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import application.DAO.Positions;
import application.DAO.Profiles;
import application.models.Position;
import application.models.Profile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
	
public class HomeController extends MainController implements Initializable{
	
	private Actions action;
	
	private Util util;
	private WebDriver driver;
	
	@FXML
    private TableView<Position> tableView;
	@FXML
	private TableColumn<Position, String> positions;
	@FXML
	private TableColumn<Position, String> select;
	@FXML
	private TableColumn<Position, String> pages;
	@FXML
	public TextField selectedProfile;
	@FXML
	private PasswordField salt;
	@FXML
	private TextField position;
	@FXML
	private Button deleteButton;
	
	private Map<String, Integer> editInfo = new HashMap<String, Integer>();
	
	private Profile activeProfile;
	
	private Positions positionsDAO = cnx.getBean("Positions", Positions.class);
	
	public HomeController() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Neo\\eclipse-workspace\\SpringMavenSelenium\\src\\application\\resources\\chromedriver.exe");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		positions.setCellValueFactory(new PropertyValueFactory<Position, String>("name"));
		select.setCellValueFactory(new PropertyValueFactory<Position, String>("select"));
		pages.setCellValueFactory(new PropertyValueFactory<Position, String>("pages"));
		Positions positions = cnx.getBean("Positions", Positions.class);
		this.tableView.setItems(positions.get());
		updatedChoosedProfile();
	}
	
	public void updatedChoosedProfile(){
		Profiles active = cnx.getBean("Profiles", Profiles.class);
		this.activeProfile = active.getActive();
		
		selectedProfile.setText(this.activeProfile.getEmail());
	}
	
	public void loadBrowser() throws Exception {
		driver = (WebDriver) cnx.getBean("driver");
		action = new Actions(driver);
		this.util = (Util) cnx.getBean("util");
		
		openSite();
		((ClassPathXmlApplicationContext) cnx).close();
	}
	
	public void openSite() throws Exception {
		int min = 158;
		int max = 5535;
		
		driver.manage().window().maximize();
		
		driver.get("https://www.linkedin.com/");
		util.sleepTime(2000);
		
		WebElement regLastNameElement = driver.findElement(By.id("reg-lastname"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", regLastNameElement);
		action.moveToElement(regLastNameElement);
		action.build();
		action.perform();
		util.sleepTime(1500);
		logIn();
		
		util.sleepTime(2000);
		
		for(int i = 0; i<this.tableView.getItems().size(); i++) {
			if(this.tableView.getItems().get(i).isSelected()) {
				String position = this.tableView.getItems().get(i).getName();
				int pages = Integer.parseInt(this.tableView.getItems().get(i).getPages().getText());
				
				searchForParticularPosition(position, pages);
			} else {
				continue;
			}
		}	
	}
	
	public void searchForParticularPosition(String position, int pagesToLookIn) {
		WebElement searchField = driver.findElement(By.xpath("//input[@placeholder='Search']"));
		searchField.click();
		util.sleepTime(2000);
		
		util.fillField(position, searchField, 200);
		
		util.sleepTime(2000);
		
		WebElement searchinPeople2 = driver.findElement(By.xpath("//*[text() = 'People']"));
		searchinPeople2.click();
		util.sleepTime(2000);
		
		String currentUrl = driver.getCurrentUrl();
		
		for(int i = 0; i < pagesToLookIn; i++) {
			int page = i+1;
			openParticularPage(currentUrl, page);
		}
		util.sleepTime(2000);
		
		WebElement searchField_again = driver.findElement(By.xpath("//input[@placeholder='Search']"));
		searchField_again.clear();
	}
	
	public void openParticularPage(String url, int page) {
		String urlToOpen = url + "&page=" + page;
			driver.get(urlToOpen);
			util.sleepTime(2000);
//		searchResults();	
	}
	
	public void searchResults()
	{	
		util.scrollToBottom(this.driver);
		
		WebElement ul = driver.findElement(By.className("search-results__list"));

		List<WebElement> results =  ul.findElements(By.className("search-result__info"));
//		for (int i = 0; i < results.size(); i++)
		for (int i = 0; i < 1; i++)
		{
			WebElement link = results.get(i).findElement(By.cssSelector("a[data-control-name='search_srp_result']"));
			
			String profile = link.getAttribute("href");
			util.openInNewTab(this.driver, profile);
		    
			util.sleepTime(6000);

			WebElement messageButton = driver.findElement(By.className("pv-s-profile-actions--message"));	
			
			if(messageButton != null) {
				messageButton.click();
				
				WebElement meesageBox = driver.findElement(By.className("msg-form__contenteditable"));
				meesageBox.click();
				util.sleepTime(1000);
				util.fillField("Are you looking for a job?", meesageBox, 200);
				WebElement submitMessage = driver.findElement(By.className("msg-form__send-button"));
				submitMessage.click();
				util.sleepTime(1000);
				util.closeCurrentTab(driver);
			}
			
		}
	}
	
	public void logIn() throws Exception {
		String decryptedPassword = new Crypt(this.salt.getText()).decrypt(this.activeProfile.getPassword());		
		
		WebElement userNameField = driver.findElement(By.name("session_key"));
		WebElement passField = driver.findElement(By.name("session_password"));
		WebElement submitButton = driver.findElement(By.id("login-submit"));
		String email = this.activeProfile.getEmail();
		
		util.fillField(email, userNameField, 100);
		util.fillField(decryptedPassword, passField, 100);
		submitButton.click();
	}
	
	public void manageProfiles() {
		new ProfileController().openWindow(this);
	}
	
	public void add() {
		String position = this.position.getText();
		
		int id = this.positionsDAO.Insert(position);
		
		Position newPosition = new Position(id, position);
		tableView.getItems().add(newPosition);
		
		this.position.clear();
	}
	
	public void edit() {
		Position position = this.tableView.getSelectionModel().getSelectedItem();
		this.deleteButton.setVisible(true);
		this.position.setText(position.getName());
		
		this.editInfo.put("id", Integer.parseInt(position.getId()));
		this.editInfo.put("index", this.tableView.getSelectionModel().getFocusedIndex());
	}
	
	public void delete() {
			this.positionsDAO.delete(this.editInfo.get("id"));
			this.tableView.getItems().remove((int) this.tableView.getSelectionModel().getFocusedIndex());
			this.deleteButton.setVisible(false);
			this.position.clear();
	}
}
