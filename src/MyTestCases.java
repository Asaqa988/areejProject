import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MyTestCases {

	WebDriver driver = new ChromeDriver();
	String URL = "https://magento.softwaretestingboard.com/customer/account/create/";
	Connection con = null;
	Statement stm = null;
	String getConnection = "jdbc:mysql://localhost:3306/classicmodels";
	String userName = "root";
	String password = "abed";
	ResultSet rs;
	String queryforGet = "select * from customers";

	ArrayList<String> CustomerName = new ArrayList<String>();
	ArrayList<String> CustomerLastName = new ArrayList<String>();

	Random rand = new Random();

	@BeforeTest

	public void mySetup() throws SQLException {
		con = DriverManager.getConnection(getConnection, userName, password);
	}

	@Test(priority = 1, invocationCount = 5)
	public void get() throws SQLException, InterruptedException {
		stm = con.createStatement();
		rs = stm.executeQuery(queryforGet);

		while (rs.next()) {
			String thecustomerName = rs.getString("customerName");
			String TheCustomerLastName = rs.getString("contactLastName");

//			String thecustomerNumber = rs.getString("customerNumber");
//			String CustomerCity = rs.getString("city");

			CustomerName.add(thecustomerName);
			CustomerLastName.add(TheCustomerLastName);

//			int randomCustomer = rand.nextInt(customers.size());
//			System.out.println(customers.get(randomCustomer));

		}
		Thread.sleep(3000);
		driver.get(URL);
		WebElement UserNameInTheWebSite = driver.findElement(By.id("firstname"));
		WebElement UserNameLastInTheWebSite = driver.findElement(By.id("lastname"));

		UserNameInTheWebSite.sendKeys(CustomerName.get(rand.nextInt(CustomerName.size())));
		UserNameLastInTheWebSite.sendKeys(CustomerLastName.get(rand.nextInt(CustomerLastName.size())));
	}

	@Test(priority = 2, enabled = false)
	public void add() throws SQLException {

		stm = con.createStatement();

		String Query = "INSERT INTO customers (customerNumber, customerName, contactLastName, contactFirstName, phone, addressLine1, city,country )"
				+ "VALUES (1121, 'abC COMPANY.', 'AHMAD', 'ALI', '23993837837', 'CAIRO adress line', 'CAIRO' ,'EGYPT');";
		int roweffected = stm.executeUpdate(Query);

		if (roweffected > 0) {
			Assert.assertEquals(true, true);
		} else {
			Assert.assertEquals(false, true);
		}

	}

	@Test(enabled = false)
	public void delete() throws SQLException {
		stm = con.createStatement();
		String deleteQuery = "Delete from customers where customerNumber = 1121";
		int roweffected = stm.executeUpdate(deleteQuery);

		if (roweffected > 0) {
			Assert.assertEquals(true, true);
		} else {
			Assert.assertEquals(false, true);
		}

	}

	@Test(enabled = false)
	public void update() throws SQLException {

		stm = con.createStatement();
		String updateQuery = "update customers  set customerName = 'areej' where customerNumber = 1120";

		int roweffected = stm.executeUpdate(updateQuery);

		if (roweffected > 0) {
			Assert.assertEquals(true, true);
		} else {
			Assert.assertEquals(false, true);
		}

	}

}
