package farmeworkProject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;




	
    	
    		
    		

    		 import    java.io.File;  
    		 import java.io.IOException;
    		 import java.text.DateFormat;
    		 import java.text.SimpleDateFormat;
    		 import java.time.Duration;
    		 import java.util.Date;

    		 import org.openqa.selenium.By;
    		 import org.openqa.selenium.OutputType;
    		 import org.openqa.selenium.TakesScreenshot;
    		 import org.openqa.selenium.WebDriver;
    		 import org.openqa.selenium.WebElement;
    		 import org.openqa.selenium.chrome.ChromeDriver;
    		 import org.openqa.selenium.edge.EdgeDriver;
    		 import org.openqa.selenium.firefox.FirefoxDriver;
    		 import org.openqa.selenium.interactions.Actions;
    		 import org.openqa.selenium.support.ui.Select;

    		 import com.aventstack.extentreports.ExtentReports;
    		 import com.aventstack.extentreports.ExtentTest;
    		 import com.aventstack.extentreports.Status;
    		 import com.aventstack.extentreports.reporter.ExtentSparkReporter;
    		 import com.google.common.io.Files;
 
    		 public class WebDriverUtil { 
    		 	
    		     private static WebDriver driver ;	
    		     public static ExtentTest extTest ;
    		 	
    		     
    		     public static void openUrl(String url) {
    		     	try {
    		     		driver.get(url);
    		     		extTest.log(Status.INFO, url+" opened");
    		     	}catch(Exception e) {
    		     		extTest.log(Status.FAIL, "failed to open url - "+url);
    		     		e.printStackTrace();
    		     	}
    		     	
    		     	
    		     }

    		     public static void setImplicitWait(int maxTimeout) {
    		     	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(maxTimeout));
    		     }
    		     
    		 	public static WebDriver launchBrowser(String browserName, ExtentTest extTest) {
    		 		
    		 		if(browserName.equalsIgnoreCase("chrome")) {
    		 			
    		 			 driver = new ChromeDriver();
    		 			extTest.log(Status.INFO, "Chrome Browser launched successfully");
    		 		}else if(browserName.equalsIgnoreCase("firefox")) {
    		 		
    		 			 driver = new FirefoxDriver();
    		 			extTest.log(Status.INFO, "Firefox Browser launched successfully");
    		 		}else if(browserName.equalsIgnoreCase("edge")) {
    		 			
    		 			 driver = new EdgeDriver();
    		 			extTest.log(Status.INFO, "Edge Browser launched successfully");

    		 		}else {
    		 			extTest.log(Status.FAIL, "Browser Not Launched. Browser Name is not valid. Please check");
    		 		}
    		 		return driver;
    		 	}
    		 	
    		 	
    		 	
    		 	/// generic method - application independent util methods wrapper methods
    		 	public void verifySearchLeads() {
    		 		DateFormat df = new SimpleDateFormat("MM_dd_yyyy___HH_mm_ss");
    		 		String timeStamp = df.format(new Date());

    		 		ExtentSparkReporter esr = new ExtentSparkReporter("Automationreport" + timeStamp + ".html");
    		 		ExtentReports ext = new ExtentReports();
    		 		ext.attachReporter(esr);
    	        	 		 ExtentTest extTest = ext.createTest("verify search lead");
    		 		
    		 	}

    		 	//////

    		 	public static WebElement getWebElement( String locatorValue,  String locatorType) {
    		 		WebElement we = null;
    		 		/// we are checking locator Type value is xpath or not
    		 		if (locatorType.equalsIgnoreCase("xpath")) {
    		 			//// if it is xpath then it using
    		 			we = driver.findElement(By.xpath(locatorValue));

    		 			///
    		 		} else if (locatorType.equalsIgnoreCase("linkText")) {
    		 			we = driver.findElement(By.linkText(locatorValue));
    		 		} else if (locatorType.equalsIgnoreCase("name")) {
    		 			we = driver.findElement(By.name(locatorValue));
    		 		} else if (locatorType.equalsIgnoreCase("id")) {
    		 			we = driver.findElement(By.id(locatorValue));
    		 		} else if (locatorType.equalsIgnoreCase("class")) {
    		 			we = driver.findElement(By.className(locatorValue));
    		 		} else if (locatorType.equalsIgnoreCase("css")) {
    		 			we = driver.findElement(By.cssSelector(locatorValue));
    		 		} else {
    		 			extTest.log(Status.FAIL, locatorType + " Locator Type is Wrong. Please check");
    		 		}
    		 		return we;
    		 	}

    		 	public static void enterTextboxValue( String locatorValue, String locatorType,
    		 			String elementName, String dataValue) {
    		 		try {
    		 			WebElement we = getWebElement( locatorValue, locatorType);
    		 			boolean st = checkElement(we,  elementName);
    		 			if (st == true) {
    		 				we.sendKeys();
    		 			}
    		 		} catch (Exception e) {
    		 			takeScreenshot(driver, elementName);
    		 		}
    		 	}

    		 	public static void selectByText( String locatorValue, String locatorType,
    		 			String elementName, String textToSelect) {
    		 		try {
    		 			WebElement we = getWebElement(locatorValue, locatorType);
    		 			boolean st = checkElement(we, elementName);
    		 			if (st == true) {
    		 				Select selectObj = new Select(we);
    		 				selectObj.selectByVisibleText(textToSelect);
    		 				
    		 			}
    		 		} catch (Exception e) {
    		 			takeScreenshot(driver, elementName);
    		 		}
    		 	}

    		 	public static void selectByValueAttribute( String locatorValue,
    		 			String locatorType, String elementName, String valueAttributeToSelect) {
    		 		try {
    		 			WebElement we = getWebElement( locatorValue, locatorType);
    		 			boolean st = checkElement(we,  elementName);
    		 			if (st == true) {
    		 				Select selectObj = new Select(we);
    		 				selectObj.selectByValue(valueAttributeToSelect);
    		 				;
    		 			}
    		 		} catch (Exception e) {
    		 			takeScreenshot(driver, elementName);
    		 		}
    		 	}

    		 	public static String getInnerText(String locatorValue, String locatorType,
    		 			String elementName) {
    		 		String innerText = null;
    		 		try {
    		 			WebElement we = getWebElement(locatorValue, locatorType);
    		 			boolean st = checkElement(we, elementName);
    		 			if (st == true) {
    		 				innerText = we.getText();
    		 			}
    		 		} catch (Exception e) {
    		 			takeScreenshot(driver, elementName);
    		 		}
    		 		return innerText;
    		 	}

    		 	
    		 	public static String getAttributeValue(String locatorValue, String locatorType, String attributeName, String elementName) {
    		 		String attributeValue = null;
    		 		try {
    		 			WebElement we = getWebElement(locatorValue, locatorType);
    		 			boolean st = checkElement(we, elementName);
    		 			if (st == true) {
    		 				attributeValue = we.getAttribute("value");
    		 			}
    		 		} catch (Exception e) {
    		 			takeScreenshot(driver, elementName);
    		 		}
    		 		return attributeValue;
    		 		
    		 	}
    		 	
    		 	//// getTe

    		 	public static void takeScreenshot(WebDriver driver, String elementName) {
    		         
    		 		TakesScreenshot tss=(TakesScreenshot)driver;
    		 		File src=tss.getScreenshotAs(OutputType.FILE);
    		 		String ts=getTimeStamp();
    		 		File  destinationFile=new File("snapshots\\"+elementName+ts+".png");
    		 		try {
    		 			Files.copy(src, destinationFile);
    		 		} catch (Exception e) {
    		 			// TODO Auto-generated catch block
    		 			e.printStackTrace();	
    		 		}
    		 		
    		 	}
    		 	
    		 	public static String getTimeStamp() {
    		 		DateFormat df= new SimpleDateFormat("MM-dd-yyyy HH_MM_SS");
    		 		return df.format(new Date());
    		 	}
    		 	

    		 	public static void click(  String locatorValue, String locatorType,	String elementName) {
    		 		try {
    		 			WebElement we = getWebElement(locatorValue, locatorType);
    		 			boolean st = checkElement(we, elementName);
    		 			if (st == true) {
    		 				we.click();
    		 			}
    		 		} catch (Exception e) {
    		 			takeScreenshot(driver, elementName);

    		 		}

    		 	}

    		 	/*
    		 	 * checkElement method is made to verify whether element is actionable or not
    		 	 * parameters- WebElement - we - ELement On which we want to perform action.
    		 	 * 
    		 	 * return - boolean
    		 	 */
    		 	public static boolean checkElement(WebElement we,  String elementName) {
    		 		boolean status = false;
    		 		/// we are checking element is displaying or not. if it is displaying then it is
    		 		/// going into if condition
    		 		if (we.isDisplayed() == true) {

    		 			extTest.log(Status.INFO, "user name text box is  visible");
    		 			//// we
    		 			if (we.isEnabled() == true) {
    		 				extTest.log(Status.INFO, elementName + " text box is  enabled");
    		 				/// if element is visible and enabled then it is assigning true value
    		 				status = true;
    		 			} else {
    		 				extTest.log(Status.INFO, elementName + " text box is  disabled");
    		 			}
    		 		} else {
    		 			extTest.log(Status.FAIL, elementName + " text box is not visible");
    		 		}
    		 		return status;
    		 	}
    		 	
    		 	
    		 	public static void validateText(String locatorValue, String locatorType,String expectedText, String elementName) {
    		 	
    		 		String actualText=WebDriverUtil.getInnerText(locatorValue, locatorType ,  elementName);
    		         if(actualText.equals(expectedText)) {
    		         	extTest.log(Status.PASS, elementName+" Text Validation Passed. actualText-"+actualText+" && expectedtext-"+expectedText);
    		         }else {
    		         	extTest.log(Status.FAIL, elementName+" Text Validation failed. actualText-"+actualText+" && expectedtext-"+expectedText);

    		         }
    		 	}
    		 	
    		 	
    		 	
    		 	
    		 	public static void validateAttributeValue(String locatorValue, String locatorType,String attributeName, String expectedAttributeValue, String elementName) {
    		 		
    		 		String actualAttributeValue=WebDriverUtil.getAttributeValue(locatorValue, locatorType, attributeName, elementName);
    		         if(actualAttributeValue.equals(expectedAttributeValue)) {
    		         	extTest.log(Status.PASS, elementName+" "+ attributeName+" Attribute Validation Passed. actual-"+actualAttributeValue+" && expected-"+expectedAttributeValue);
    		         }else {
    		         	extTest.log(Status.FAIL, elementName+" Attribute Validation failed. actual-"+actualAttributeValue+" && expected-"+expectedAttributeValue);

    		         }
    		 	}	
    		 	
    		 	public static void validateElementIsEnabled(String locatorValue, String locatorType, String elementName) {
    		 		
    		 		WebElement we=getWebElement(locatorValue, locatorType);
    		 		boolean status=we.isEnabled();
    		         if(status==true) {
    		         	extTest.log(Status.PASS, elementName+" is Enabled");
    		         }else {
    		         	extTest.log(Status.FAIL, elementName+" is Disabled");
    		         }
    		 	}	
    		 	

    		 	public static void validateElementIsVisible(String locatorValue, String locatorType, String elementName) {
    		 		
    		 		WebElement we=getWebElement(locatorValue, locatorType);
    		 		boolean status=we.isDisplayed();
    		         if(status==true) {
    		         	extTest.log(Status.PASS, elementName+" is visible");
    		         }else {
    		         	extTest.log(Status.FAIL, elementName+" is invisible");
    		         }
    		 	}	
    		 	
    		 	public static void validateElementIsDisabled(String locatorValue, String locatorType, String elementName) {
    		 		
    		 		WebElement we=getWebElement(locatorValue, locatorType);
    		 		boolean status=we.isEnabled();
    		         if(status==false) {
    		         	extTest.log(Status.PASS, elementName+" is Disabled");
    		         }else {
    		         	extTest.log(Status.FAIL, elementName+" is Enabled");
    		         }
    		 	}	
    		 		
    		 	
    		 	
    		 	

    		 

    		 
    	 }
    		 
    		 
   	 	
    			
    			
    	 		
    	 	


    	 
	
	

