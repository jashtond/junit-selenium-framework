import java.awt.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;


public class Applicant {

	@Test
	public void Login() throws Exception {
		
		FirefoxDriver fd=new FirefoxDriver();
		fd.get("http://apps.ensoftek.com/DrCloudEMR2013/interface/login/login_frame.php?site=AFRH");
		fd.findElementByXPath("//*[@id='login_form']/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]/input").sendKeys("admin");
		fd.findElementByXPath("//*[@id='login_form']/table[2]/tbody/tr/td[2]/table/tbody/tr[4]/td[2]/input").sendKeys("demo");
		fd.findElementByXPath("//*[@id='login_form']/table[2]/tbody/tr/td[2]/table/tbody/tr[6]/td[2]/input[3]").click();
		Thread.sleep(20000);
		fd.switchTo().frame("Title");
		fd.findElementByXPath("//*[@id='user_tabs']/ul/li[2]/a").click();
		fd.switchTo().defaultContent();
		fd.switchTo().frame("RTop");
		fd.findElementByXPath("html/body/div[1]/div/form/table[1]/tbody/tr/td[2]/a/span").click();
		//Firstname		
		fd.findElementByXPath("html/body/form/div[5]/div[1]/table/tbody/tr[3]/td/input[2]").sendKeys("Test");
		//Middlename
		fd.findElementByXPath("html/body/form/div[5]/div[1]/table/tbody/tr[3]/td/input[3]").sendKeys("Client");
		//Lastname
		fd.findElementByXPath("html/body/form/div[5]/div[1]/table/tbody/tr[3]/td/input[1]").sendKeys("patient");
		//Expected Date of Entry
		//fd.findElementByXPath("//*[@id='expected_date_entry']").clear();
		//fd.findElementByXPath("//*[@id='expected_date_entry']").click();
		
		//Selecting the referral id
		new Select(fd.findElementByXPath("html/body/form/div[5]/div[1]/table/tbody/tr[5]/td/table/tbody/tr/td[2]/select")).selectByVisibleText("Active Duty Member");
		//selecting the facility
		new Select(fd.findElementByName("facility_id[]")).selectByVisibleText("AFRH - Armed Forces Resident Home");
		fd.findElementByXPath("html/body/form/ul/li[2]/a").click();
		fd.findElementByXPath("html/body/form/div[5]/div[2]/table/tbody/tr[3]/td[2]/input").click();
		fd.findElementByXPath("html/body/form/div[5]/div[2]/table/tbody/tr[4]/td[2]/input").click();
		fd.findElementByXPath("html/body/form/div[5]/div[2]/table/tbody/tr[5]/td[2]/input").click();
		fd.findElementByXPath("html/body/form/div[5]/div[2]/table/tbody/tr[6]/td[2]/input").click();
		fd.findElementByXPath("html/body/form/div[5]/div[2]/table/tbody/tr[10]/td/input[2]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[2]/table/tbody/tr[11]/td/input[2]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[2]/table/tbody/tr[12]/td/input[2]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[2]/table/tbody/tr[13]/td/input[2]").click();
		fd.findElementByXPath("html/body/form/ul/li[3]/a").click();
		//ss number
		fd.findElementByXPath("html/body/form/div[5]/div[3]/table[1]/tbody/tr[1]/td[1]/input").sendKeys("859-66-9696");
		// Military Service
		fd.findElementByXPath("html/body/form/div[5]/div[3]/table[1]/tbody/tr[1]/td[2]/input").sendKeys("582965");
		//Address
		fd.findElementByXPath("html/body/form/div[5]/div[3]/table[1]/tbody/tr[2]/td/input").sendKeys("2055'N. 22nd Street 11135@#$%#$.'");
		//City
		fd.findElementByXPath("html/body/form/div[5]/div[3]/table[1]/tbody/tr[3]/td[1]/input").sendKeys("NY");
		// State
		new Select(fd.findElementByXPath("html/body/form/div[5]/div[3]/table[1]/tbody/tr[3]/td[2]/select")).selectByVisibleText("New York");
		// Zip code
		fd.findElementByXPath("html/body/form/div[5]/div[3]/table[1]/tbody/tr[4]/td[1]/input").sendKeys("58475");
		// Phone
		fd.findElementByXPath("html/body/form/div[5]/div[3]/table[1]/tbody/tr[4]/td[2]/input").sendKeys("584-785-9859");
		// Email
		fd.findElementByXPath("html/body/form/div[5]/div[3]/table[1]/tbody/tr[5]/td[1]/input").sendKeys("thrivenic@ensoftek.com");
		fd.findElementByXPath("html/body/form/div[5]/div[3]/table[1]/tbody/tr[5]/td[2]/input").sendKeys("CA");
		fd.findElementByXPath("html/body/form/div[5]/div[3]/table[2]/tbody/tr/td/img").click();
		fd.findElementByXPath("html/body/div[5]/table/thead/tr[2]/td[3]/div").click();
		fd.findElementByXPath("html/body/form/div[5]/div[3]/table[3]/tbody/tr[1]/td[2]/input[1]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[3]/table[3]/tbody/tr[1]/td[2]/input[4]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[3]/table[3]/tbody/tr[1]/td[2]/input[5]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[3]/table[3]/tbody/tr[2]/td/input[1]").sendKeys("Test mother");
		fd.findElementByXPath("html/body/form/div[5]/div[3]/table[3]/tbody/tr[3]/td/input").sendKeys("TEST Maiden");
		fd.findElementByXPath("html/body/form/div[5]/div[3]/table[3]/tbody/tr[4]/td/input[1]").sendKeys("Test Father");
		fd.findElementByXPath("html/body/form/div[5]/div[3]/table[3]/tbody/tr[5]/td/input").sendKeys("Test Child1");
		fd.findElementByXPath("html/body/form/div[5]/div[3]/table[3]/tbody/tr[6]/td/input").sendKeys("Test Child1");
		fd.findElementByXPath("html/body/form/div[5]/div[3]/table[3]/tbody/tr[7]/td/input").sendKeys("Military");
		fd.findElementByXPath("html/body/form/div[5]/div[3]/table[3]/tbody/tr[8]/td/input").sendKeys("+2");
		fd.findElementByXPath("html/body/form/div[5]/div[3]/table[3]/tbody/tr[9]/td/input").sendKeys("ARMY");
		fd.findElementByXPath("html/body/form/div[5]/div[3]/table[3]/tbody/tr[10]/td/input").sendKeys("Service1");
		fd.findElementByXPath("html/body/form/div[5]/div[3]/table[3]/tbody/tr[11]/td/input").sendKeys("Hobbies");
		fd.findElementByXPath("html/body/form/ul/li[4]/a").click();
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[2]/td/input[1]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[3]/td/input[2]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[4]/td[1]/input").sendKeys("20");
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[4]/td[2]/input").sendKeys("584785");
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[5]/td[1]/input[1]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[5]/td[2]/input").sendKeys("50000");
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[6]/td/input[1]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[7]/td/input").sendKeys("Cond1");
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[8]/td/input[1]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[9]/td/input[1]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[10]/td/table/tbody/tr/td[2]/input[1]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[10]/td/table/tbody/tr/td[3]/input").sendKeys("4785");
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[11]/td/input[1]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[12]/td/input[1]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[13]/td/input[1]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[14]/td/input[1]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[14]/td/textarea").sendKeys("The execption states that the tag for dropdown is not select but an image. Either the element is identified wrongfully or you need to change the selection pattern. Select method works only if the tag is select. Please share the html. Will help you out.");
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[15]/td/input[1]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[16]/td/textarea").sendKeys("if you need to select the value from such drop downs then you need to first click on the textbox. This makes the dropdown visible and after that you need to perform click operation using an xpath for the city name. Let me know if you require more help");
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[17]/td/input[1]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[18]/td/input[1]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[19]/td/input[1]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[20]/td/input").sendKeys("Ensoftek");
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[21]/td/input[1]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[22]/td/input[1]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[4]/table/tbody/tr[23]/td/input[1]").click();
		fd.findElementByXPath("html/body/form/ul/li[5]/a").click();
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[1]/tbody/tr[2]/td[1]/input").click();
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[1]/tbody/tr[2]/td[2]/input").click();
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[1]/tbody/tr[3]/td[1]/input").click();
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[1]/tbody/tr[3]/td[2]/input").click();
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[1]/tbody/tr[4]/td[1]/input").click();
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[1]/tbody/tr[4]/td[2]/input").click();
		//fd.findElementByXPath("html/body/form/div[5]/div[5]/table[1]/tbody/tr[7]/td/table/tbody/tr/td/img").click();
		//fd.findElementByXPath("html/body/div[7]/table/thead/tr[2]/td[3]/div").click();
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[1]/tbody/tr[8]/td/input").sendKeys("WA");
		//fd.findElementByXPath("html/body/form/div[5]/div[5]/table[1]/tbody/tr[9]/td/table/tbody/tr/td/img").click();
		//fd.findElementByXPath("html/body/div[11]/table/thead/tr[2]/td[3]/div").click();
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[1]/tbody/tr[11]/td[1]/input").click();
		new Select(fd.findElementByXPath("html/body/form/div[5]/div[5]/table[1]/tbody/tr[10]/td[2]/select")).selectByVisibleText("W02");
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[1]/tbody/tr[11]/td[2]/input").sendKeys("25");
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[1]/tbody/tr[13]/td/table/tbody/tr/td[2]/input").click();
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[1]/tbody/tr[13]/td/table/tbody/tr/td[3]/input").click();
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[1]/tbody/tr[13]/td/table/tbody/tr/td[4]/input").click();
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[1]/tbody/tr[13]/td/table/tbody/tr/td[5]/input").click();
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[1]/tbody/tr[13]/td/table/tbody/tr/td[6]/input").click();	
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[1]/tbody/tr[14]/td/input[5]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[1]/tbody/tr[15]/td/input").sendKeys("USA");
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[2]/tbody/tr[2]/td/table/tbody/tr[1]/td[1]/input").click();
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[2]/tbody/tr[2]/td/table/tbody/tr[1]/td[2]/input").click();
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[2]/tbody/tr[2]/td/table/tbody/tr[1]/td[3]/input").click();
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[2]/tbody/tr[2]/td/table/tbody/tr[1]/td[4]/input").click();
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[2]/tbody/tr[2]/td/table/tbody/tr[2]/td[1]/input").click();
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[2]/tbody/tr[2]/td/table/tbody/tr[2]/td[2]/input").click();
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[2]/tbody/tr[2]/td/table/tbody/tr[2]/td[3]/input").click();
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[2]/tbody/tr[2]/td/table/tbody/tr[2]/td[4]/input").click();
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[3]/tbody/tr[1]/td/input[1]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[3]/tbody/tr[2]/td/input[2]").click();
		fd.findElementByXPath("html/body/form/div[5]/div[5]/table[3]/tbody/tr[3]/td/input[1]").click();
		//fd.findElementByXPath("html/body/form/div[5]/div[5]/table[3]/tbody/tr[3]/td/img").click();
		//fd.findElementByXPath("html/body/div[9]/table/thead/tr[2]/td[3]/div").click();
		fd.findElementByXPath("html/body/form/ul/li[6]/a").click();
		fd.findElementByXPath("html/body/form/div[4]/table/tbody/tr/td[2]/a[1]/span").click();
		fd.switchTo().defaultContent();
		fd.switchTo().frame("left_nav");
		fd.findElementByXPath("html/body/div[1]/form/ul[2]/li[2]/a").click();
		fd.switchTo().defaultContent();
		fd.switchTo().frame("RTop");
		fd.findElementByXPath("html/body/div[1]/form/div/div/table/tbody/tr[2]/td[1]/table/tbody/tr/td[1]/a/span/b").click();
		//fd.getWindowHandle();
		//fd.switchTo().window("iframe_medium");
		//fd.findElementByClassName("iframe_medium");
		//fd.switchTo().alert();
		//fd.getWindowHandles();
				
		new Select(fd.findElementByXPath("html/body/div[1]/form/div[2]/div/table[1]/tbody/tr/td[2]/select")).selectByVisibleText("Approved with Vacancy");
		new Select(fd.findElementByXPath("html/body/div[1]/form/div[2]/div/table[2]/tbody/tr/td/div/table/tbody/tr/td[2]/select")).selectByVisibleText("AFRH - Armed Forces Resident Home");
		
	}
}
