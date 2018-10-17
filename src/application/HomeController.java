package application;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class HomeController {
	
	private Actions action;
	private ApplicationContext cnx;
	private Util util;
	private WebDriver driver;
	
	public HomeController() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Neo\\eclipse-workspace\\test7\\src\\application\\resources\\chromedriver.exe");
	}
	
	public void loadBrowser() {
		this.cnx = new ClassPathXmlApplicationContext("application/resources/beans.xml");
		driver = (WebDriver) cnx.getBean("driver");
		action = new Actions(driver);
		this.util = (Util) cnx.getBean("util");
		
		openSite(driver);
		
		((ClassPathXmlApplicationContext) cnx).close();
	}
	
	public void openSite(WebDriver driver) {
		int min = 158;
		int max = 5535;
		
		driver.manage().window().maximize();
		
		driver.get("https://www.linkedin.com/");
		util.sleepTime(util.rngNumberWaitTime(min, max));
		
		WebElement regLastNameElement = driver.findElement(By.id("reg-lastname"));
//		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", regLastNameElement);
		action.moveToElement(regLastNameElement);
		action.build();
		action.perform();
		util.sleepTime(util.rngNumberWaitTime(min, max));
		logIn();
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
	
	
}
