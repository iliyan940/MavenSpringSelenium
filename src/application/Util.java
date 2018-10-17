package application;

import java.util.Random;

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
}
