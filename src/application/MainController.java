package application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainController {
	
	protected ApplicationContext cnx;

	public MainController() {
		this.cnx = new ClassPathXmlApplicationContext("application/resources/beans.xml");
	}

}
