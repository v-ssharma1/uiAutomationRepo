package com.test.automation.uiAutomation.RetryFailedTests;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer{
   public int counter=0;
   public int maxRetryCount=2;
	public boolean retry(ITestResult result) {
		if(counter<maxRetryCount) {
			counter++;
			return true;
		}
		return false;
	}
}
