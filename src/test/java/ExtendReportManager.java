import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtendReportManager implements ITestListener {

    public ExtentSparkReporter extentSparkReporter ;
    public ExtentReports extentReports ;
     public ExtentTest extentTest ;

String repName ;
     public void onStart(ITestContext context) {
         SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
         Date date = new Date();
         String timeStamp = df.format(date);
         //String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
repName = "TestReport-"+timeStamp+".html" ;

extentSparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);
extentSparkReporter.config().setDocumentTitle("QA Automation");
extentSparkReporter.config().setReportName("SwagLabs Testing");
extentSparkReporter.config().setTheme(Theme.STANDARD);

extentReports = new ExtentReports();

extentReports.attachReporter(extentSparkReporter);
extentReports.setSystemInfo("Application" , "SwagLabs");
extentReports.setSystemInfo("Environment","QA");
extentReports.setSystemInfo("UserName", System.getProperty("user.name"));

//datas which is retrived from parameter tag from XML file
         String os = context.getCurrentXmlTest().getParameter("os");
         extentReports.setSystemInfo("Operating System" ,os);

         String browserName = context.getCurrentXmlTest().getParameter("browser");
         extentReports.setSystemInfo("BrowserName" ,browserName);


    }
    public void onTestSuccess(ITestResult result) {
       extentTest = extentReports.createTest(result.getTestClass().getName());
       extentTest.log(Status.PASS, result.getName() + "is sucessfully Executed");

    }

    public void onTestFailure(ITestResult result) {
        extentTest = extentReports.createTest(result.getTestClass().getName());
        extentTest.log(Status.FAIL, result.getName() + "is Faileds");
try {
    String impPath = new BaseClass().captureScreenShot(result.getName());
extentTest.addScreenCaptureFromPath(impPath);
} catch (Exception e) {
    throw new RuntimeException(e);
}
    }

    public void onTestSkipped(ITestResult result) {
        extentTest = extentReports.createTest(result.getTestClass().getName());
        extentTest.log(Status.SKIP, result.getName() + "is Skipped");
    }
    public  void onFinish(ITestContext context) {
         extentReports.flush();


         //open extend report automatically
         String openExtendreport = System.getProperty("user.dir")+".\\reports\\" +repName ;
         File extendReport = new File(openExtendreport);
         try {
             Desktop.getDesktop().browse(extendReport.toURI());
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
    }
}
