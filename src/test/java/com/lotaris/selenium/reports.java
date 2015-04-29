import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class reports {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://apps.ensoftek.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testReportsBH() throws Exception {
	  driver.get(baseUrl + "/DrCloudEMR2013/interface/login/login_frame.php?error=1&site=");
	    driver.findElement(By.name("authUser")).clear();
	    driver.findElement(By.name("authUser")).sendKeys("admin");
	    driver.findElement(By.name("clearPass")).clear();
	    driver.findElement(By.name("clearPass")).sendKeys("demo");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.switchTo().frame("Title");
        driver.findElement(By.linkText("Reports")).click();
        driver.switchTo().defaultContent();
       	driver.switchTo().frame("RTop");
        assertEquals("Select Reports to display from menu.", driver.findElement(By.cssSelector("html body#reports_top p")).getText());
        driver.switchTo().defaultContent();
        driver.switchTo().frame("left_nav");
        driver.findElement(By.linkText("Dashboard")).click();
        driver.switchTo().defaultContent();
        driver.switchTo().frame("RTop");
        driver.findElement(By.xpath("//*[@id='search_criteria']/tbody/tr/th[2]/a/span")).click();
        assertEquals("Utilization Month to Date - Residential ( September-2014 )", driver.findElement(By.xpath("//*[@id='report_results_therapy']/span[1]")).getText());
        assertEquals("Utilization Month to Date - OutPatient ( September-2014 )", driver.findElement(By.xpath("//*[@id='report_results_therapy']/span[2]")).getText());
        driver.switchTo().defaultContent();
        driver.switchTo().frame("left_nav");
        driver.findElement(By.linkText("Clients")).click();
       driver.switchTo().defaultContent();
        driver.switchTo().frame("left_nav");
        driver.findElement(By.linkText("List")).click();
        driver.switchTo().defaultContent();
       	driver.switchTo().frame("RTop");
        assertEquals("Report - Patient List", driver.findElement(By.xpath("html/body/span")).getText());
        if (!driver.findElement(By.xpath("//*[@id='discharge']")).isSelected()) {
      driver.findElement(By.xpath("//*[@id='discharge']")).click();
        };
     driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
     
     driver.switchTo().defaultContent();
     driver.switchTo().frame("left_nav");
     driver.findElement(By.linkText("Rx")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("RTop"); 
     assertEquals("Report - Prescriptions and Dispensations", driver.findElement(By.xpath("/html/body/span")).getText());
     driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
     
     driver.switchTo().defaultContent();
     driver.switchTo().frame("left_nav");
     driver.findElement(By.linkText("Clinical")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("RTop"); 
     assertEquals("Report - Clinical", driver.findElement(By.xpath("html/body/span")).getText());
     driver.findElement(By.xpath("//*[@id='form_details']")).click();
     driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("left_nav");
     driver.findElement(By.linkText("Immunization Registry")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("RTop");
     assertEquals("Report - Immunization Registry", driver.findElement(By.xpath("/html/body/span")).getText());
     driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a[1]/span")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("left_nav");
     driver.findElement(By.linkText("Patient Admit History")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("RTop");
     assertEquals("Report - Patient Admit History", driver.findElement(By.xpath("/html/body/div[3]/b")).getText());
     driver.findElement(By.xpath("//*[@id='submit']")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("left_nav");
     driver.findElement(By.linkText("Pellet Order")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("RTop");
     assertEquals("Pellet Order", driver.findElement(By.xpath("html/body/table/tbody/tr/th[1]/span")).getText());
     driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("left_nav");
     driver.findElement(By.linkText("View Deleted Encounter Forms")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("RTop");
     assertEquals("View Deleted Encounters", driver.findElement(By.xpath("html/body/span")).getText());
     driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("left_nav");
     driver.findElement(By.linkText("Case Load")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("RTop");
     assertEquals("Report - Case Load", driver.findElement(By.xpath("html/body/span")).getText());
     driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("left_nav");
     driver.findElement(By.linkText("Case Staff")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("RTop");
     assertEquals("Case Staff Report - List of staff assigned to a Patient", driver.findElement(By.xpath("html/body/span")).getText());
     driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("left_nav");
     driver.findElement(By.linkText("Birthday List")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("RTop");
     assertEquals("Birthday List", driver.findElement(By.xpath("html/body/span")).getText());
     driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
     
    // driver.findElement(By.linkText("Incomplete Nine Points Of Contact")).click();
    // assertEquals("List of Incomplete Nine Points Of Contact Forms", driver.findElement(By.xpath("html/body/span")).getText());
     //driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("left_nav");
     driver.findElement(By.linkText("Patient Hospitalization Report")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("RTop");
     assertEquals("Patient Hospitalization Report", driver.findElement(By.xpath("html/body/span")).getText());
     driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("left_nav");
     driver.findElement(By.linkText("PAN Usage Report")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("RTop");
     assertEquals("PAN Usage Report", driver.findElement(By.xpath("html/body/table/tbody/tr/th/span")).getText());
     driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
     //Mu Reports
     driver.switchTo().defaultContent();
     driver.switchTo().frame("left_nav");
     driver.findElement(By.xpath("//*[@id='accord-rept']/li[4]/a/span")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("left_nav");
     driver.findElement(By.linkText("Report Results")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("RTop");
     assertEquals("Report History/Results", driver.findElement(By.xpath("html/body/span")).getText());
     driver.switchTo().defaultContent();
     driver.switchTo().frame("left_nav");
     driver.findElement(By.linkText("Standard Measures")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("RTop");
     assertEquals("Report - Standard Measures", driver.findElement(By.xpath("html/body/span")).getText());
     driver.switchTo().defaultContent();
     driver.switchTo().frame("left_nav");
     driver.findElement(By.linkText("Quality Measures (CQM)")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("RTop");
     assertEquals("Report - Clinical Quality Measures (CQM)", driver.findElement(By.xpath("html/body/span")).getText());
     driver.switchTo().defaultContent();
     driver.switchTo().frame("left_nav");
     driver.findElement(By.linkText("Automated Measures (AMC)")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("RTop");
     assertEquals("Report - Automated Measure Calculations (AMC)", driver.findElement(By.xpath("html/body/span")).getText());
     driver.switchTo().defaultContent();
     driver.switchTo().frame("left_nav");
     driver.findElement(By.linkText("AMC Tracking")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("RTop");
     assertEquals("Report - Automated Measure Calculations (AMC) Tracking", driver.findElement(By.xpath("html/body/span")).getText());
     driver.switchTo().defaultContent();
     driver.switchTo().frame("left_nav");
     driver.findElement(By.xpath("//*[@id='accord-rept']/li[5]/a")).click();
     driver.findElement(By.linkText("Appointments")).click();
     driver.switchTo().defaultContent();
     driver.switchTo().frame("RTop");
     assertEquals("Report - Appointments", driver.findElement(By.xpath("html/body/span")).getText());
     if (!driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[1]/div/table/tbody/tr[1]/td[7]/input")).isSelected()) {
      driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[1]/div/table/tbody/tr[1]/td[7]/input")).click();
    };
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/input")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Encounters")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Encounters", driver.findElement(By.xpath("html/body/span")).getText());
    if (!driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[1]/div/table/tbody/tr[1]/td[7]/input")).isSelected()) {
      driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[1]/div/table/tbody/tr[1]/td[7]/input")).click();
    };
    if (!driver.findElement(By.xpath("//*[@id='form_details']")).isSelected()) {
      driver.findElement(By.xpath("//*[@id='form_details']")).click();
    };
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/input")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Appointments and Encounters By Provider")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Appointments and Encounters By Provider", driver.findElement(By.xpath("html/body/span")).getText());
     if (!driver.findElement(By.xpath("//*[@id='form_details']")).isSelected()) {
      driver.findElement(By.xpath("//*[@id='form_details']")).click();
    };
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/input[1]")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Appointments and Encounters By Patient")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Appointments and Encounters By Patient", driver.findElement(By.xpath("html/body/span")).getText());
    if (!driver.findElement(By.xpath("//*[@id='form_details']")).isSelected()) {
      driver.findElement(By.xpath("//*[@id='form_details']")).click();
    };
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/input")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Superbill")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Reports - Superbill", driver.findElement(By.xpath("html/body/span")).getText());
    driver.findElement(By.xpath("//*[@id='theform']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/input")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Eligibility")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Eligibility 270 Inquiry Batch", driver.findElement(By.xpath("html/body/span")).getText());
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a[1]/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Eligibility Response")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("EDI-271 File Upload", driver.findElement(By.xpath("html/body/div[2]/span")).getText());
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Syndromic Surveillance")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Syndromic Surveillance - Non Reported Issues", driver.findElement(By.xpath("html/body/span")).getText());
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Patient Appointments")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Patient Appointments", driver.findElement(By.xpath("html/body/span")).getText());
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Daily Status Report")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Daily Status Report", driver.findElement(By.xpath("html/body/span")).getText());
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a[1]/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Monthly Visit Count")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Monthly Visit Count", driver.findElement(By.xpath("//*[@id='theform']/span[1]")).getText());
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/a[1]/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Daily Visit Shift Count")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Daily Visit Shift Count", driver.findElement(By.xpath("html/body/span[1]")).getText());
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/a[1]/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.xpath("//*[@id='accord-rept']/li[6]/a")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Patient Demography")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Patient Population By Gender:", driver.findElement(By.xpath("html/body/table[1]/tbody/tr/td[1]/div/p")).getText());
    assertEquals("Patient Population By Appointments:", driver.findElement(By.xpath("//*[@id='patient_population']/p")).getText());
    assertEquals("Patient Population By Ethnicity:", driver.findElement(By.xpath("html/body/table[2]/tbody/tr/td[1]/div/p")).getText());
    assertEquals("Patient Population By Age Group:", driver.findElement(By.xpath("html/body/table[2]/tbody/tr/td[2]/div/p")).getText());
    driver.findElement(By.xpath("//*[@id='form_refresh']/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Patient Population By Medical Issues")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Patient Population By Medical Issues:", driver.findElement(By.xpath("//*[@id='main_div']/div/p")).getText());
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Admissions")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("New Admissions Per Day:", driver.findElement(By.xpath("//*[@id='admit_population']/p")).getText());
    driver.findElement(By.xpath("//*[@id='form_refresh']/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Therapist Productivity By Payer Source")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Therapist Productivity By Payer Source Summary", driver.findElement(By.xpath("html/body/span")).getText());
    if (!driver.findElement(By.xpath("//*[@id='details']")).isSelected()) {
      driver.findElement(By.xpath("//*[@id='details']")).click();
    };
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a[1]/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Therapist Productivity By Payer Code")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Therapist Productivity By Payer Code Summary", driver.findElement(By.xpath("html/body/span")).getText());
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Notes Completion Compliance Graph")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Notes Completion Compliance Graph:", driver.findElement(By.xpath("//*[@id='forms_population']/p[1]")).getText());
    if (!driver.findElement(By.xpath("//*[@id='chk_show_details']")).isSelected()) {
      driver.findElement(By.xpath("//*[@id='chk_show_details']")).click();
    };
    driver.findElement(By.xpath("//*[@id='form_refresh']/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Utilization Summary By Payer Source")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Utilization Summary By Payer Source", driver.findElement(By.xpath("html/body/span")).getText());
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a[1]/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Utilization Summary By Payer Code")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Utilization Summary By Payer Code", driver.findElement(By.xpath("html/body/span")).getText());
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a[1]/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Resi. Utilization Report")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Client Utilization Report for the month Sep - 2014", driver.findElement(By.xpath("html/body/span")).getText());
    driver.findElement(By.xpath(".//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Package Orders")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Package Orders", driver.findElement(By.xpath("html/body/span")).getText());
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a[1]/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Progress Note Report")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Progress Notes", driver.findElement(By.xpath("html/body/span")).getText());
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a[1]/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("BOP Sign In/Sign Out Log Report")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("BOP Sign In / Sign Out Log Report", driver.findElement(By.xpath("//*[@id='report_header']/span")).getText());
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("24 Hour Nursing Summary")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    new Select(driver.findElement(By.xpath("//*[@id='form_facility']"))).selectByVisibleText("AACMT - Bozeman");
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/a[1]/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Admit/Discharge Survey Report")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Admit / Discharge Survey Report", driver.findElement(By.xpath("//*[@id='report_header']/span")).getText());
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a[1]/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("ETAR Report")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    if (!driver.findElement(By.xpath("//*[@id='chkDetail']")).isSelected()) {
      driver.findElement(By.xpath("//*[@id='chkDetail']")).click();
    };
   
    assertEquals("Please select a search criteria and click submit.", driver.findElement(By.xpath("//*[@id='spanMessage']")).getText());
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr[2]/td/table/tbody/tr/td[1]/a/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Length of Stay")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Length of Stay", driver.findElement(By.xpath("html/body/div[3]/b")).getText());
    driver.findElement(By.xpath("//*[@id='submit']")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.xpath("//*[@id='accord-rept']/li[7]/a")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Sales")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Sales by Item", driver.findElement(By.xpath("html/body/span")).getText());
     if (!driver.findElement(By.xpath("//*[@id='form_details']")).isSelected()) {
      driver.findElement(By.xpath("//*[@id='form_details']")).click();
    };
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Patient Rec")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Patient Receipts by Provider", driver.findElement(By.xpath("html/body/span")).getText());
    if (!driver.findElement(By.xpath("//*[@id='form_details']")).isSelected()) {
      driver.findElement(By.xpath("//*[@id='form_details']")).click();
    };
    if (!driver.findElement(By.xpath("//*[@id='form_procedures']")).isSelected()) {
      driver.findElement(By.xpath("//*[@id='form_procedures']")).click();
    };
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Receipts Summary")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Receipts Summary", driver.findElement(By.xpath("html/body/span")).getText());
   
    if (!driver.findElement(By.xpath("//*[@id='form_details']")).isSelected()) {
      driver.findElement(By.xpath("//*[@id='form_details']")).click();
    };
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Collections")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Collections", driver.findElement(By.xpath("html/body/span")).getText());
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Cancelled Checks")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Canceled Checks", driver.findElement(By.xpath("html/body/span")).getText());
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Financial Summary By Service Codes")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Financial Summary by Service Code", driver.findElement(By.xpath("html/body/span")).getText());
   
    if (!driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[1]/div/table/tbody/tr[2]/td[4]/input")).isSelected()) {
      driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[1]/div/table/tbody/tr[2]/td[4]/input")).click();
    };
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Outstanding Revenue By Age")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
      assertEquals("Outstanding Revenue By Age :", driver.findElement(By.xpath("//*[@id='collections']/p")).getText());
    
    driver.findElement(By.xpath("//*[@id='collections']/table/tbody/tr/td[9]/a/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("ResFunds Transactions")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
      //assertEquals("Report - ResFunds Transactions", driver.findElement(By.xpath("//*[@id='Patient_funds']/span")).getText());
      driver.switchTo().defaultContent();
      driver.switchTo().frame("left_nav");
   
    driver.findElement(By.linkText("Billed Claims")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
      assertEquals("Billed Claims", driver.findElement(By.xpath("html/body/table/tbody/tr/th/span")).getText());
   
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a[1]/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Overridden Encounters")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
      assertEquals("Overridden Encounters", driver.findElement(By.xpath("html/body/span")).getText());
    
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
    
    //driver.findElement(By.linkText("simple layout report")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Unapplied Cash Report")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Unapplied Cash Report", driver.findElement(By.xpath("html/body/span")).getText());
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.xpath("//*[@id='accord-rept']/li[8]/a")).click();
    //driver.switchTo().defaultContent();
   // driver.switchTo().frame("left_nav");
    //driver.findElement(By.xpath(".//*[@id='rep0']")).click();
   // driver.switchTo().defaultContent();
   // driver.switchTo().frame("RTop");
    //assertEquals("Inventory List", driver.findElement(By.xpath("html/body/span")).getText());
    //driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/a/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Activity")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Inventory Activity", driver.findElement(By.xpath("html/body/span")).getText());
    if (!driver.findElement(By.xpath("//*[@id='form_details']")).isSelected()) {
      driver.findElement(By.xpath("//*[@id='form_details']")).click();
    };
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/a/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Transactions")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Inventory Transactions", driver.findElement(By.xpath("html/body/span")).getText());
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/a/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.xpath("//*[@id='accord-rept']/li[9]/a")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Pending Res")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
      assertEquals("Pending Orders", driver.findElement(By.xpath("html/body/center/span/h2")).getText());
    
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr[1]/td[1]/input[3]")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Statistics")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
      assertEquals("Procedure Statistics Report", driver.findElement(By.xpath("html/body/center/h2")).getText());
    
    driver.findElement(By.xpath("//*[@id='theform']/table/tbody/tr[3]/td[3]/input")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.xpath("//*[@id='accord-rept']/li[10]/a")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Distribution")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Patient Insurance Distribution", driver.findElement(By.xpath("html/body/span")).getText());
    if (!driver.findElement(By.xpath("//*[@id='form_details']")).isSelected()) {
      driver.findElement(By.xpath("//*[@id='form_details']")).click();
    };
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Distribution By Payer Code")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Patient Payer Code Distribution", driver.findElement(By.xpath("html/body/span")).getText());
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Indigents")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Indigent Residents", driver.findElement(By.xpath("html/body/span")).getText());
    if (!driver.findElement(By.xpath("//*[@id='details']")).isSelected()) {
      driver.findElement(By.xpath("//*[@id='details']")).click();
    };
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Unique SP")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Unique Seen Patients", driver.findElement(By.xpath("html/body/span")).getText());
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.xpath("//*[@id='accord-rept']/li[11]/a")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Demographics")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Fee Sheet")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Referral")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Patient Right Access Health Information")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Consent for Search of Property")).click();
    //driver.switchTo().defaultContent();
    //driver.switchTo().frame("left_nav");
    //driver.findElement(By.linkText("Hipaa Privacy")).click();
    //driver.switchTo().defaultContent();
   // driver.switchTo().frame("left_nav");
   // driver.findElement(By.linkText("Client questionnaire")).click();
   // driver.switchTo().defaultContent();
   // driver.switchTo().frame("left_nav");
   // driver.findElement(By.linkText("Female Body Fat Composition")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.xpath("//*[@id='accord-rept']/li[12]/a/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Group Session Tracking")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Group Session Tracking", driver.findElement(By.xpath("html/body/span")).getText());
    new Select(driver.findElement(By.xpath("//*[@id='form_facility']"))).selectByVisibleText("AACMT - Bozeman");
    if (!driver.findElement(By.xpath("//*[@id='show_only_due']")).isSelected()) {
      driver.findElement(By.xpath("//*[@id='show_only_due']")).click();
    };
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Outstanding dues")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    assertEquals("Report - Outstanding dues", driver.findElement(By.xpath("html/body/span")).getText());
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a[1]/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.linkText("Print Roster")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("RTop");
    new Select(driver.findElement(By.xpath("//*[@id='form_facility']"))).selectByVisibleText("AACMT - Bozeman");
    assertEquals("Report - Group Session Roster", driver.findElement(By.xpath("html/body/p/span")).getText());
    driver.findElement(By.xpath("//*[@id='report_parameters']/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/a/span")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("left_nav");
    driver.findElement(By.xpath("//*[@id='accord-rept']/li[13]/a/span")).click();
    driver.navigate().refresh();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
