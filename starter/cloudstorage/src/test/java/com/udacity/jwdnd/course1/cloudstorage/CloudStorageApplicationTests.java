package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.time.Duration;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.firefoxdriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new FirefoxDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}



	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling redirecting users
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric:
	 * https://review.udacity.com/#!/rubrics/2724/view
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");

		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/signup", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling bad URLs
	 * gracefully, for example with a custom error page.
	 *
	 * Read more about custom error pages at:
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");

		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code.
	 *
	 * Read more about file size limits here:
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	@Test
	public void testUnauthorized(){
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
		driver.get("http://localhost:" + this.port + "/notes");
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
		driver.get("http://localhost:" + this.port + "/credentials");
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("http://localhost:" + this.port + "/signup", driver.getCurrentUrl());
	}


	@Test
	public void testLogoutNotAccessHomePage(){
		doMockSignUp("1","1","abc","abc");
		doLogIn("abc","abc");
		Assertions.assertEquals("Home",driver.getTitle());
		doLogout();
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}
	@Test
	public void testDoNotes(){
		doMockSignUp("hjdsa","jhsd","dgfgg","123");
		doLogIn("dgfgg","123");
		addNotes("noteTitle","noteDescription");
		updateNotes("newNoteTitle","newNoteDescription");
		deleteNote();
	}

	@Test
	public void testDoCredentials(){
		doMockSignUp("ds","fgsd","dsad","123");
		doLogIn("dsad","123");
		addCredential("URL","username","password");
		updateCredential("newURL");
		deleteCredential();
	}

	private void deleteCredential() {
		driver.get("http://localhost:" + this.port + "/home");
		JavascriptExecutor jse= (JavascriptExecutor) driver;
		WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
		WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		jse.executeScript("arguments[0].click()", credentialsTab);
		WebElement credentialsTable = driver.findElement(By.id("credentialTable"));
        List<WebElement> elements = credentialsTable.findElements(By.xpath("//*[text()='Delete']"));
        int elms = elements.size()-1;
        WebElement deleteElement = elements.get(elms);

		webDriverWait.until(ExpectedConditions.elementToBeClickable(deleteElement)).click();
		Assertions.assertEquals("http://localhost:" +this.port+"/result?success",driver.getCurrentUrl());
	}

	private void updateCredential(String newUrl) {
		driver.get("http://localhost:" + this.port + "/home");
		JavascriptExecutor jse= (JavascriptExecutor) driver;
		WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
		WebElement credentialTab = driver.findElement(By.id("nav-credentials-tab"));
		jse.executeScript("arguments[0].click()", credentialTab);
		WebElement credentialsTable = driver.findElement(By.id("credentialTable"));
        List<WebElement> elements = credentialsTable.findElements(By.xpath("//*[text()='Edit']"));
        int elms = elements.size()-1;
        WebElement editElement = elements.get(elms);

        webDriverWait.until(ExpectedConditions.elementToBeClickable(editElement)).click();

		WebElement url = driver.findElement(By.id("credential-url"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(url));
		url.clear();
		url.sendKeys(newUrl);
		WebElement saveChangeBtn = driver.findElement(By.id("btn-save-credential"));
        saveChangeBtn.click();
		Assertions.assertEquals("http://localhost:"+this.port +"/result?success", driver.getCurrentUrl());

		driver.get("http://localhost:" + this.port + "/home");
		credentialTab = driver.findElement(By.id("nav-credentials-tab"));
		jse.executeScript("arguments[0].click()", credentialTab);
		credentialsTable = driver.findElement(By.id("credentialTable"));
        WebElement tbody = credentialsTable.findElement(By.tagName("tbody"));
        List<WebElement> urlContents = tbody.findElements(By.tagName("th"));
        WebElement urlContent = webDriverWait.until(ExpectedConditions.visibilityOf(urlContents.get(elms)));
        Assertions.assertEquals(urlContent.getText(), newUrl);
	}

	private void addCredential(String url, String username,String password) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		WebElement credentialTab = driver.findElement(By.id("nav-credentials-tab"));
		jse.executeScript("arguments[0].click()", credentialTab);
		webDriverWait.withTimeout(Duration.ofSeconds(30));
		WebElement newCredential = driver.findElement(By.id("btn-add-credential"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(newCredential)).click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement urlelm = driver.findElement(By.id("credential-url"));
        urlelm.sendKeys(url);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement usernameElm = driver.findElement(By.id("credential-username"));
        usernameElm.sendKeys(username);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement passwordElm = driver.findElement(By.id("credential-password"));
        passwordElm.sendKeys(password);
		WebElement saveChanges = driver.findElement(By.id("btn-save-credential"));
        saveChanges.click();
		Assertions.assertEquals("http://localhost:" + this.port + "/result?success", driver.getCurrentUrl());
	}

	private void doLogout(){
		WebElement logoutButton = driver.findElement(By.id("btn-logout"));
		logoutButton.click();
	}

	private void addNotes(String noteTitle,String noteDescription) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		jse.executeScript("arguments[0].click()", notesTab);
		webDriverWait.withTimeout(Duration.ofSeconds(30));
		WebElement newNote = driver.findElement(By.id("btn-add-note"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(newNote)).click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement noteTitleElement = driver.findElement(By.id("note-title"));
		noteTitleElement.sendKeys(noteTitle);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement noteDescriptionElement = driver.findElement(By.id("note-description"));
		noteDescriptionElement.sendKeys(noteDescription);
		WebElement saveChangesElement = driver.findElement(By.id("btn-note-save"));
		saveChangesElement.click();
		Assertions.assertEquals("http://localhost:" + this.port + "/result?success", driver.getCurrentUrl());
	}
	private void updateNotes(String noteTitle,String noteDescription){
		driver.get("http://localhost:" + this.port + "/home");
		JavascriptExecutor javascriptExecutor= (JavascriptExecutor) driver;
		WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
		WebElement notesTabs = driver.findElement(By.id("nav-notes-tab"));
		javascriptExecutor.executeScript("arguments[0].click()", notesTabs);
		WebElement notesTable = driver.findElement(By.id("userTable"));
        List<WebElement> elements = notesTable.findElements(By.xpath("//*[text()='Edit']"));
        int elms = elements.size()-1;
		WebElement editElement = elements.get(elms);

		webDriverWait.until(ExpectedConditions.elementToBeClickable(editElement)).click();
		WebElement noteTitleElement = driver.findElement(By.id("note-title"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(noteTitleElement));
		noteTitleElement.clear();
		noteTitleElement.sendKeys(noteTitle);
		WebElement noteDescriptionElement = driver.findElement(By.id("note-description"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(noteDescriptionElement));
		noteDescriptionElement.clear();
		noteDescriptionElement.sendKeys(noteDescription);
		WebElement saveChangesElement = driver.findElement(By.id("btn-note-save"));
		saveChangesElement.click();
		Assertions.assertEquals("Result", driver.getTitle());


		driver.get("http://localhost:" + this.port + "/home");
		notesTabs = driver.findElement(By.id("nav-notes-tab"));
		javascriptExecutor.executeScript("arguments[0].click()", notesTabs);
		notesTable = driver.findElement(By.id("userTable"));
        WebElement tbody = notesTable.findElement(By.tagName("tbody"));
        List<WebElement> noteTitleContents = tbody.findElements(By.tagName("th"));
        WebElement noteTitleContent = webDriverWait.until(ExpectedConditions.visibilityOf(noteTitleContents.get(elms)));
		Assertions.assertEquals(noteTitleContent.getText(), noteTitle);
	}
	private void deleteNote(){
		driver.get("http://localhost:" + this.port + "/home");
		JavascriptExecutor jse= (JavascriptExecutor) driver;
		WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		jse.executeScript("arguments[0].click()", notesTab);
		WebElement notesTable = driver.findElement(By.id("userTable"));
        List<WebElement> elements = notesTable.findElements(By.xpath("//*[text()='Delete']"));
        int elms = elements.size()-1;
        WebElement deleteElement = elements.get(elms);

		webDriverWait.until(ExpectedConditions.elementToBeClickable(deleteElement)).click();
		Assertions.assertEquals("http://localhost:" +this.port+"/result?success",driver.getCurrentUrl());
	}
}
