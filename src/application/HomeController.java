package application;

import org.openqa.selenium.WebDriver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HomeController {
	public void loadBrowser() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Neo\\eclipse-workspace\\test7\\src\\application\\resources\\chromedriver.exe");
		
		ApplicationContext cnx = new ClassPathXmlApplicationContext("application/resources/beans.xml");
		WebDriver driver = (WebDriver) cnx.getBean("driver");
		
		driver.get("http://www.google.com");
		
		((ClassPathXmlApplicationContext) cnx).close();
	}
}
