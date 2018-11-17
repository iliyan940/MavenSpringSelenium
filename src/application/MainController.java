package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import application.DAO.Profiles;

public abstract class MainController {
	
	protected ApplicationContext cnx;

	public MainController() {
		this.cnx = new ClassPathXmlApplicationContext("application/resources/beans.xml");
	}

}
