package Common;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;

public class CommonFunctions {
	
	public static String reporturl="";
	
	public static ExtentReports setupResult()
	{	
		DateFormat format=new SimpleDateFormat("yyyyMMddhhmmss");
		Date date=new Date();
		String datestr=format.format(date);
		String extentReport=System.getProperty("user.dir")+"/src/TestReport/vtigerReport_"+datestr+".html";
		reporturl=extentReport;
		System.out.println(extentReport);
		ExtentReports report=new ExtentReports(extentReport);
		return report;
	}
	
	 public static String getscreenshot(WebDriver driver) throws Exception 
     {
		    DateFormat format=new SimpleDateFormat("yyyyMMddhhmmss");
			Date date=new Date();
			String datestr=format.format(date);
			String filename=System.getProperty("user.dir")+"/src/TestReport/Screenshot/"+datestr+".png";
		 
             File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            //The below method will save the screen shot in d drive with name "screenshot.png"
             FileUtils.copyFile(scrFile, new File(filename));
             return filename;
     }

}
