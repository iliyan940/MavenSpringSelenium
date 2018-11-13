package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import application.DAO.Positions;
import application.DAO.Profiles;
import application.models.Position;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class HomeController extends MainController implements Initializable{
	
	private Actions action;
	
	private Util util;
	private WebDriver driver;
	private ArrayList<Position> allPositions;
	
	@FXML
    private TableView<Position> tableView;
	@FXML
	private TableColumn positions;
	@FXML
	private TableColumn select;
	@FXML
	private TableColumn editButton;
	
	
	public HomeController() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Neo\\eclipse-workspace\\SpringMavenSelenium\\src\\application\\resources\\chromedriver.exe");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		positions.setCellValueFactory(new PropertyValueFactory<Position, String>("name"));
		select.setCellValueFactory(new PropertyValueFactory<Position, String>("select"));
		editButton.setCellValueFactory(new PropertyValueFactory<Position, String>("editButton"));
			
		Positions db = cnx.getBean("Positions",Positions.class);
		
		tableView.setItems(db.getPositions());
		
	}
	
	
	public void loadBrowser() {
		driver = (WebDriver) cnx.getBean("driver");
		action = new Actions(driver);
		this.util = (Util) cnx.getBean("util");
		
		openSite();
		((ClassPathXmlApplicationContext) cnx).close();
	}
	
	public void openSite() {
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
		
		WebElement searchField = driver.findElement(By.xpath("//input[@placeholder='Search']"));
		searchField.click();
		util.sleepTime(2000);
		
		util.fillField("Full Stack", searchField, 200);
		
		util.sleepTime(2000);
		
		WebElement searchInPeople = driver.findElement(By.id("nav-search-artdeco-typeahead-results-result-0"));
		searchInPeople.click();
		util.sleepTime(2000);
		
		
		// Look for all given pages
		// Get number of the pages; for the test the value is 2
		
		int pagesToLookIn = 1;
		
		String currentUrl = driver.getCurrentUrl();
		
		for(int i = 0; i < pagesToLookIn; i++) {
			int page = i+1;
			openParticularPage(currentUrl, page);
		}
	}
	
	public void openParticularPage(String url, int page) {
		String urlToOpen = url + "&page=" + page;
		
		if(page>1) {
			driver.get(urlToOpen);
			util.sleepTime(2000);
		}
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
	
	public void logIn() {
		WebElement userNameField = driver.findElement(By.name("session_key"));
		WebElement passField = driver.findElement(By.name("session_password"));
		WebElement submitButton = driver.findElement(By.id("login-submit"));
		String userName = "test";
		String password = "test";
		util.fillField(userName, userNameField, 100);
		util.fillField(password, passField, 100);
		submitButton.click();
	}
	
	public void manageProfiles() {
		BorderPane root;
		try {
			root = (BorderPane)FXMLLoader.load(getClass().getResource("resources/Profiles.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("resources/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setMinWidth(418);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
