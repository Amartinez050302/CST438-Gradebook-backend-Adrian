package com.cst438;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EndToEndTestSubmitGrades {

    public static final String CHROME_DRIVER_FILE_LOCATION = "C:/chromedriver_win32/chromedriver.exe";
    public static final String URL = "http://localhost:3000"; 
    public static final int SLEEP_DURATION = 1000; 
    public static final String TEST_ASSIGNMENT_NAME = "db design";
    public static final String NEW_GRADE = "99";

    @Test
    public void addCourseTest() throws Exception {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(URL);
            Thread.sleep(SLEEP_DURATION);

           

        } finally {
            driver.quit();
        }
    }

    @Test
    public void addAssignmentTest() throws Exception {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(URL);
            Thread.sleep(SLEEP_DURATION);

           
            WebElement addButton = driver.findElement(By.id("add-assignment-button"));
            addButton.click();
            Thread.sleep(SLEEP_DURATION);

           
            WebElement assignmentNameInput = driver.findElement(By.id("assignment-name"));
            assignmentNameInput.sendKeys("New Assignment");

          
            WebElement saveButton = driver.findElement(By.id("save-assignment-button"));
            saveButton.click();
            Thread.sleep(SLEEP_DURATION);


            WebElement assignmentList = driver.findElement(By.id("assignment-list"));
            
        } finally {
            driver.quit();
        }
    }

    @Test
    public void updateAssignmentTest() throws Exception {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(URL);
            Thread.sleep(SLEEP_DURATION);


        } finally {
            driver.quit();
        }
    }

    @Test
    public void deleteAssignmentTest() throws Exception {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(URL);
            Thread.sleep(SLEEP_DURATION);

          

        } finally {
            driver.quit();
        }
    }
}
