package ru.innopolis;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Title;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Класс - тест считывает параметры из файла, валюты, подставляет их в поля валют,
 * проверяет появление/изменение графиков.
 */
@Title("Тест графиков")
@Description("Класс - тест считывает параметры из файла, валюты, подставляет их в поля валют, проверяет появление/изменение графиков.")
@RunWith(value = Parameterized.class)
public class ViewCurrTest {

    private static final Logger LOGGER = Logger.getLogger(ViewCurrTest.class);
    private static WebDriver driver;
    private static String fileParam = "src\\main\\resources\\viewcurrtest.csv";
    private String currFrom;
    private String currIn;
    private static Map<String, String> currName;

    public ViewCurrTest(String currFrom, String currIn) {
        this.currFrom = currFrom;
        this.currIn = currIn;
    }

    static {
        currName = new HashMap<>();
        currName.put("CHF", "Швейцарский франк");
        currName.put("EUR", "Евро");
        currName.put("GBP", "Фунт стерлингов Соединенного Королевства");
        currName.put("JPY", "Японская иена");
        currName.put("USD", "Доллар США");
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

    /**
     * Тестирование появление графика изменения валюты к рублю в зависимости от выбранной валюты в полях "из" и "в"
     */
    @Title("Ввод валют, сравнение графиков")
    @Test
    public void viewTest() {

        // Выбор валюты откуда конвертируется сумма
        Actions actionsCurrFrom = new Actions(driver);
        WebElement webElemMenuFrom = driver.findElement(By.cssSelector(".rates-aside__filter-block > div:nth-child(3) > div:nth-child(2) > div:nth-child(2)"));
        actionsCurrFrom.moveToElement(webElemMenuFrom).click().build().perform();

        String elemMenuFrom = null;

        switch (currFrom) {
            case "RUB":
                elemMenuFrom = ".//*[@id='main']/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div[3]/div/div/aside/div/div/div/div[2]/div/div[1]/div[3]/div[2]/div/div/span[1]";
                break;
            case "CHF":
                elemMenuFrom = ".//*[@id='main']/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div[3]/div/div/aside/div/div/div/div[2]/div/div[1]/div[3]/div[2]/div/div/span[2]";
                break;
            case "EUR":
                elemMenuFrom = ".//*[@id='main']/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div[3]/div/div/aside/div/div/div/div[2]/div/div[1]/div[3]/div[2]/div/div/span[3]";
                break;
            case "GBP":
                elemMenuFrom = ".//*[@id='main']/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div[3]/div/div/aside/div/div/div/div[2]/div/div[1]/div[3]/div[2]/div/div/span[4]";
                break;
            case "JPY":
                elemMenuFrom = ".//*[@id='main']/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div[3]/div/div/aside/div/div/div/div[2]/div/div[1]/div[3]/div[2]/div/div/span[5]";
                break;
            case "USD":
                elemMenuFrom = ".//*[@id='main']/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div[3]/div/div/aside/div/div/div/div[2]/div/div[1]/div[3]/div[2]/div/div/span[6]";
                break;
        }

        WebElement webElemMenuFromChoise = driver.findElement(By.xpath(elemMenuFrom));
        actionsCurrFrom.moveToElement(webElemMenuFromChoise).click().build().perform();


        // Выбор валюты куда нужно конвертировать
        Actions actionsCurrIn = new Actions(driver);
        WebElement webElemMenuIn = driver.findElement(By.cssSelector(".rates-aside__filter-block > div:nth-child(4) > div:nth-child(2) > div:nth-child(2)"));
        actionsCurrIn.moveToElement(webElemMenuIn).click().build().perform();

        String elemMenuIn = null;

        switch (currIn) {
            case "RUB":
                elemMenuIn = ".//*[@id='main']/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div[3]/div/div/aside/div/div/div/div[2]/div/div[1]/div[4]/div[2]/div/div/span[1]";
                break;
            case "CHF":
                elemMenuIn = ".//*[@id='main']/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div[3]/div/div/aside/div/div/div/div[2]/div/div[1]/div[4]/div[2]/div/div/span[2]";
                break;
            case "EUR":
                elemMenuIn = ".//*[@id='main']/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div[3]/div/div/aside/div/div/div/div[2]/div/div[1]/div[4]/div[2]/div/div/span[3]";
                break;
            case "GBP":
                elemMenuIn = ".//*[@id='main']/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div[3]/div/div/aside/div/div/div/div[2]/div/div[1]/div[4]/div[2]/div/div/span[4]";
                break;
            case "JPY":
                elemMenuIn = ".//*[@id='main']/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div[3]/div/div/aside/div/div/div/div[2]/div/div[1]/div[4]/div[2]/div/div/span[5]";
                break;
            case "USD":
                elemMenuIn = ".//*[@id='main']/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div[2]/div/div[3]/div/div/aside/div/div/div/div[2]/div/div[1]/div[4]/div[2]/div/div/span[6]";
                break;


        }

        WebElement webElemMenuInChose = driver.findElement(By.xpath(elemMenuIn));
        actionsCurrIn.moveToElement(webElemMenuInChose).click().build().perform();


        //Поиск  название валюты в элементе график валюты, выбранной в поле "из" и сравнение с названием получившегося графика
        WebElement webGrafFrom = driver.findElement(By.cssSelector(".rates-details-graphs > div:nth-child(1) > h2:nth-child(4)"));
        String textGrFrom = webGrafFrom.getText();
        Assert.assertEquals(textGrFrom, currName.get(currFrom));


        //Поиск  название валюты в элементе график валюты, выбранной в поле "в" и сравнение с названием получившегося графика
        WebElement webGrafIn = driver.findElement(By.cssSelector(".rates-details-graphs > div:nth-child(2) > h2:nth-child(4)"));
        String textGrIn = webGrafIn.getText();
        Assert.assertEquals(textGrIn, currName.get(currIn));

    }

    /**
     * Метод, выполняющийся после теста. Закрывает браузер.
     */
    @After
    public void afterTest() {
        driver.quit();
    }

}
