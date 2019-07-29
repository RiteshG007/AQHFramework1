package TestScript;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import Common.CommonFunctions;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;


public class DriverScript {
	
	
   public static ExtentReports report=null;
   public static ExtentTest logger; 
   public static String extentReport;	
   public static WebDriver driver;
   public static String AppName;
   public static String ModulesDriverFiles;
   public static String AppUrl;
   public static String ScenarioDriverFile;
   public static String BrowserName;
   public static String sync;
   public static String Modules;
   public static String Priority;
   public static String TestCases;
   public static String Description;
   

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		Xls_Reader xr=new Xls_Reader(System.getProperty("user.dir")+"/src/DriverFiles/AppDriver.xlsx");
		int Approwcount = xr.getRowCount("AppDriver");
		for(int i=2;i<=Approwcount;i++)
		{
			String vAppRun=xr.getCellData("AppDriver", "Run", i).trim();
			if(vAppRun.equalsIgnoreCase("ON"))
			{
				 AppName = xr.getCellData("AppDriver", "AppName", i).trim();
				 AppUrl = xr.getCellData("AppDriver", "AppUrl", i).trim();
				 ModulesDriverFiles = xr.getCellData("AppDriver", "ModulesDriverFiles", i).trim();
				 ScenarioDriverFile = xr.getCellData("AppDriver", "ScenarioDriverFile", i).trim();
				 BrowserName = xr.getCellData("AppDriver", "BrowserName", i).trim();
				 sync = xr.getCellData("AppDriver", "sync", i).trim();
				 StepDriver sd=new StepDriver();
				 
				   if(report==null)
					{
						report=CommonFunctions.setupResult();
					}
				 
				 if(BrowserName.equals("Chrome"))
				 {
					 System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/Utilities/chromedriver.exe");
					 driver = new ChromeDriver();
					 driver.get(AppUrl);
				 }
				 driver.manage().window().maximize();
				 driver.manage().timeouts().implicitlyWait(Integer.parseInt(sync), TimeUnit.SECONDS);
				 
				 Xls_Reader xrm=new Xls_Reader(System.getProperty("user.dir")+"/src/TestData/"+ModulesDriverFiles);
				 Xls_Reader xrs=new Xls_Reader(System.getProperty("user.dir")+"/src/TestScenario/"+ScenarioDriverFile);
					
				 int Modrowcount= xr.getRowCount(AppName);
					for(int j=2;j<=Modrowcount;j++)
					{
						String vModRun=xr.getCellData(AppName, "Run", j).trim();
						if(vModRun.equalsIgnoreCase("ON"))
						{
							Modules = xr.getCellData(AppName, "Modules", j).trim();
							Priority = xr.getCellData(AppName, "Priority", j).trim();
							
							int TCCount=xrm.getRowCount(Modules);
							int SCCount=xrs.getRowCount(Modules);
							for(int k=2;k<=TCCount;k++)
							{
								String vTCRun=xrm.getCellData(Modules, "Run", k).trim();
								if(vTCRun.equalsIgnoreCase("ON"))
								{
									TestCases = xrm.getCellData(Modules, "TestCases", k).trim();
									Description = xrm.getCellData(Modules, "Description", k).trim();
									boolean flag = false;
									int rownum=0;
									System.out.println(TestCases);
									logger=report.startTest(TestCases);
									for(int x=2;x<=SCCount;x++)
									{
										String Action = xrs.getCellData(Modules, "Action", x).trim();
										if(Action.replace("TC-", "").equals(TestCases))
										{
											flag = true;
											rownum = x;
										}
										
										if((flag) && (x>rownum))
										{
											if(Action.startsWith("TC-"))
											{
												break;
											}
											else
											{
												String vSteps=Action;
												
												System.out
														.println(vSteps);
												sd.SelectSteps(vSteps, xrs, Modules, x,xrm,k);
											}
										}
										
									}
									
									report.endTest(logger);
									report.flush();
									
									
								}
							}
							
							
						}
					}
				 
				 driver.navigate().to(CommonFunctions.reporturl);
			}
		}

	}

}
