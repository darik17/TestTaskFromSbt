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
import ru.yandex.qatools.allure.annotations.Title;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Класс - тест, проверяющий конвертер валют на сайте сбербанка.
 * На сайте вносится проверяемая сумма, выбирается валюта с какой будем конвертировать и валюта
 * куда будем конвертировать. Запускается конвертация и проверяется наличие результата.
 */
@Title("Тест ковертора")
@RunWith(value = Parameterized.class)
public class ConvertionTest {

    private static final Logger LOGGER = Logger.getLogger(ConvertionTest.class);
    private static WebDriver driver;
    private static String fileParam = "src\\main\\resources\\convertiontest.csv";
    private String val;
    private String currFrom;
    private String currIn;

    public ConvertionTest(String val, String currFrom, String currIn) {
        this.val = val;
        this.currFrom = currFrom;
        this.currIn = currIn;
    }

    /**
     * Метод считывает данные с файла и возвращает в виде параметров для каждого набора
     *
     * @return val, currFrom, currIn
     */
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
     * Тестирование конвертора на сайте, параметры берутся из файла
     */
    @Title("Ввод суммы и валют")
    @Test
    public void convertTest() {
        //Очистка поля ввода и ввод суммы
        WebElement webAmount = driver.findElement(By.cssSelector(".rates-aside__filter-block-line-right input"));
        webAmount.click();
        webAmount.clear();
        webAmount.click();
        webAmount.clear();
        webAmount.sendKeys(val);


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


        // Нажатие на кнопку Показать
        WebElement elemButton = driver.findElement(By.className("rates-button"));
        elemButton.click();


        // Проверка что элемент появился
        WebElement elemResult = driver.findElement(By.cssSelector(".rates-converter-result"));
        Assert.assertTrue(elemResult.isEnabled());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LOGGER.error("Ошибка потока", e);
        }
    }

    /**
     * Метод, выполняющийся после теста. Закрывает браузер.
     */
    @After
    public void afterTest() {
        driver.quit();
    }

}
