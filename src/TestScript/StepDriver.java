package TestScript;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import Common.CommonFunctions;

import com.relevantcodes.extentreports.LogStatus;


public class StepDriver {
	
	public void SelectSteps(String steps, Xls_Reader xrs, String Module, int x,Xls_Reader xrm,int k) throws Exception
	{
		WebElement elm=null;
		String vTarget = ReferenceData(xrm,Module,k,xrs.getCellData(Module, "Target", x).trim());
		String vValue = ReferenceData(xrm,Module,k,xrs.getCellData(Module, "Value", x).trim());
		 switch(steps)
		 {
		 case "open":
			 open(vTarget);
			 break;
		 case "verifyTitle":
			 verifyTitle(vTarget);
			 break;
		 case "verifyElementPresent":
			 elm=GetLocator(vTarget);
			 verifyElementPresent(elm);
			 break;
		 case "verifyText":
			 elm=GetLocator(vTarget);
			 verifyText(elm,vValue);
			 break;
		 case "type":
			 elm=GetLocator(vTarget);
			 type(elm,vValue);
			 break;
		 case "verifyValue":
			 elm=GetLocator(vTarget);
			 verifyValue(elm,vValue);
			 break;
		 case "clickAndWait":
			 elm=GetLocator(vTarget);
			 click(elm);
			 break;
		 case "click":
			 elm=GetLocator(vTarget);
			 click(elm);
			 break;
		 case "select":
			 elm=GetLocator(vTarget);
			 select(elm,vValue);
			 break;
		 case "ScrollDown":
			 ScrollDown();
			 break;
		 }
		 
	}
	
	public void ScrollDown()
	{		
		JavascriptExecutor jse = (JavascriptExecutor)DriverScript.driver;
		jse.executeScript("window.scrollBy(0,1000)");
	}
	
	public void select(WebElement elm, String vValue)
	{
		elm.sendKeys(vValue);
		if(true)
		{
			System.out.println("PASSED");
			DriverScript.logger.log(LogStatus.PASS, "value selected");
		}
		else
		{
			System.out.println("FAILED");
			DriverScript.logger.log(LogStatus.FAIL, "value did not select");
		}
	}
	
	public void click(WebElement elm)
	{
	try
	 {
		elm.click();
		if(true)
		{
			System.out.println("PASSED");
			DriverScript.logger.log(LogStatus.PASS, "Element clicked");
		}
		else
		{
			System.out.println("FAILED");
			DriverScript.logger.log(LogStatus.FAIL, "Element not found");
			
		}
	 }catch(Throwable t)
	 {
		 DriverScript.logger.log(LogStatus.FAIL, t.getMessage());
	 }
	
		
	}
	
	public void verifyValue(WebElement elm, String vValue)
	{
		
		if(elm.getAttribute("value").equals(vValue))
		{
			System.out.println("PASSED");
			DriverScript.logger.log(LogStatus.PASS, "value verified");
		}
		else
		{
			System.out.println("FAILED");
			DriverScript.logger.log(LogStatus.FAIL, "value not found");
		}
	}
	
	public void type(WebElement elm, String vValue)
	{
		elm.clear();
		elm.sendKeys(vValue);
		if(elm.getAttribute("value").equals(vValue))
		{
			System.out.println("PASSED");
			DriverScript.logger.log(LogStatus.PASS, "Value entered within textbox");
		}
		else
		{
			System.out.println("FAILED");
			DriverScript.logger.log(LogStatus.FAIL, "Element not found");
		}
	}
	
	
	public void verifyText(WebElement elm, String vValue)
	{
		if(elm.getText().trim().equals(vValue))
		{
			System.out.println("PASSED");
			DriverScript.logger.log(LogStatus.PASS, "Text matched");
		}
		else
		{
			System.out.println("FAILED");
			DriverScript.logger.log(LogStatus.FAIL, "Text not found"); 
		}
	}
	
	
	public void verifyElementPresent(WebElement elm)
	{
		if(elm.isDisplayed())
		{
			System.out.println("PASSED");
			DriverScript.logger.log(LogStatus.PASS, "Element exists");
		}
		else
		{
			System.out.println("FAILED");
			DriverScript.logger.log(LogStatus.FAIL, "Element not found");
		}
	}
	
	public void verifyTitle(String vTarget) throws Exception
	{
		if(vTarget.equals(DriverScript.driver.getTitle()))
		{
			System.out.println("PASSED");
			DriverScript.logger.log(LogStatus.PASS, "Title verified");
		}
		else
		{
			String filename=CommonFunctions.getscreenshot(DriverScript.driver);
			System.out.println("FAILED");
			DriverScript.logger.log(LogStatus.FAIL, "Title did not match<span class='test-status label pass'><a href="+filename+">Screenshot</a></span>");
		}
	}
	
	public void open(String vTarget)
	{
		DriverScript.driver.navigate().to(DriverScript.AppUrl+vTarget);
		DriverScript.logger.log(LogStatus.PASS, "Application url navigated suucesfully");
	}
	
	public String GetTarget()
	{
		return "";
	}
	
	
	public WebElement GetLocator(String Target)
	{
		WebElement elm= null;
	  try
	  {
		if(Target.startsWith("//"))
		{
			elm= DriverScript.driver.findElement(By.xpath(Target));
		}
		else if(Target.startsWith("css="))
		{
			elm= DriverScript.driver.findElement(By.cssSelector(Target.replace("css=", "")));
		}
		else if(Target.startsWith("link="))
		{
			elm= DriverScript.driver.findElement(By.linkText(Target.replace("link=", "")));
		}
		else if(Target.startsWith("name="))
		{
			elm= DriverScript.driver.findElement(By.name(Target.replace("name=", "")));
		}
		else if(Target.startsWith("id="))
		{
			elm= DriverScript.driver.findElement(By.id(Target.replace("id=", "")));
		}
	  }
	  catch(Throwable t)
	  {
		  t.printStackTrace();
	  }
		return elm;
	}
	
	public String ReferenceData(Xls_Reader xrm, String Module, int k,String str)
	{
		String newData="";
		if(str.startsWith("Param"))
		{
			newData=xrm.getCellData(Module, str, k).trim();
		}
		else if(str.equalsIgnoreCase("CurrentDate"))
		{
			DateFormat format=new SimpleDateFormat("yyyyMMddhhmmss");
			Date date=new Date();
			newData=format.format(date);
		}
		else
		{
			newData=str;
		}		
		return newData;
		
	}
	

}
