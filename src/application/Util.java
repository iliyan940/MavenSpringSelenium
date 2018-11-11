package application;

import java.util.ArrayList;
import java.util.Random;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Util {
	
	public static int rngNumberWaitTime(int min, int max) {
		Random r = new Random();
		int millisecondsToWait = r.nextInt((max - min) + 1)
				+ min;
		
		return millisecondsToWait;
	}
	
	public static void sleepTime(int millisecondsToWait) {
		try {
			Thread.sleep(millisecondsToWait);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void fillField(String input, WebElement field, int millisecondsToWait) {
		for (int i = 0; i < input.length(); i++) {
			//TODO
			//Try catch for when can't send values ?
			field.sendKeys(String.valueOf(input.charAt(i)));
		}
	}
	
	public static void scrollToBottom(WebDriver driver) {
		JavascriptExecutor jsx = (JavascriptExecutor)driver;
		
		for(int i = 0; i < 1000; i++)
		{
			jsx.executeScript("window.scrollBy(0,3)", "");

		}
		sleepTime(2000);
	}
	
	public static void openInNewTab(WebDriver driver, String profile) {
		((JavascriptExecutor)driver).executeScript("window.open(arguments[0]);", profile);
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(1));
	}
	
	public static void closeCurrentTab(WebDriver driver) {
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.close();
		driver.switchTo().window(tabs.get(0));
	}
	
}
