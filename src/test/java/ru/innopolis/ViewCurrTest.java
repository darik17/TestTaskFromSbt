package ru.innopolis;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 27.06.2017.
 */

@RunWith(value = Parameterized.class)
public class ViewCurrTest {

    private static final Logger LOGGER = Logger.getLogger(ViewCurrTest.class);
    private static WebDriver driver;
    private static String fileParam = "src\\main\\resources\\viewcurrtest.csv";
    private String currFrom;
    private String currIn;

    public ViewCurrTest(String currFrom, String currIn) {
        this.currFrom = currFrom;
        this.currIn = currIn;
    }

    @Parameterized.Parameters
    public static Collection<String[]> readDataFromFile() {
        List<String[]> list = new ArrayList<>();
        String temStr;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileParam))) {

            while ((temStr = reader.readLine()) != null) {
                String[] tempMas = temStr.split(",");
                list.add(tempMas);
            }

        } catch (IOException e) {
            LOGGER.error("Ошибка чтения файла", e);
        }
        return list;
    }

    /**
     * Метод, исполняющийся до начала теста, задающий настройки chrome webdriver, также переход
     * на сайт для  тестирования.
     */
    @Before
    public void seleniumSet() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.navigate().to("https://www.sberbank.ru/ru/quotes/converter");
    }


}
