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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Класс - тест считывает параметры из файла и нажимает кнопку распечать, проверяя появление окна распечатывания.
 */
@Title("Тест распечатывания графиков по валютам")
@RunWith(value = Parameterized.class)
public class CurrDateTest {

    private static final Logger LOGGER = Logger.getLogger(CurrDateTest.class);
    private static WebDriver driver;
    private static String fileParam = "src\\main\\resources\\currdatetest.csv";
    private String currIn;
    private String withDate;
    private String atDay;

    public CurrDateTest(String currIn, String withDay, String atDay) {
        this.currIn = currIn;
        this.withDate = withDay;
        this.atDay = atDay;
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
     * Фактически из файла берет только валюту, подставляя в поле "в" и проверяет появление окна печати
     */
    @Title("Тест печати по валюте")
    @Description("Класс - тест проверяет печать по заданной валюте.")
    @Test
    public void changeDate(){

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate stopWithDate = LocalDate.of(2002,01,01);
        LocalDate stopAtDate = LocalDate.now();

        LocalDate withLocalDate = LocalDate.parse((CharSequence) withDate,dateTimeFormatter);
        LocalDate atLocalDate = LocalDate.parse((CharSequence) atDay,dateTimeFormatter);

        //Проверка на диапазон дат (нижняя граница - 01.01.2002, верхняя - текущая дата),
        // если выходит за диапазон, то выставлются эти крайние значения и пишется в лог
        if (withLocalDate.isBefore(stopWithDate) ) {
            LOGGER.info("Считанная дата  из файла ниже 01.01.2002, установленна нижняя дата!");
            withLocalDate = stopWithDate;
        }

        if (atLocalDate.isAfter(stopAtDate)){
            LOGGER.info("Считанная дата из файла выше текущей, установленна текущая!");
            atLocalDate = stopAtDate;
        }

        // Выбор валюты
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

        //Установка дат в календаре не получилась, в данные поля просто копировать - вставить нельзя, даже с просто браузера
        //похоже это сделано специально, можно только выбрать вручную.
        //Даты оставляю по-умолчанию.

        //Жму кнопку "Распечатать"
        WebElement webElementEntBut = driver.findElement(By.cssSelector(".rates-details__link-text"));
        webElementEntBut.click();

        //Проверяю появление окна распечатывания
        WebElement webElementPrint = driver.findElement(By.cssSelector(".details-item.print-visible"));
        Assert.assertTrue(webElementPrint.isEnabled());

    }

    /**
     * Метод, выполняющийся после теста. Закрывает браузер.
     */
    @After
    public void afterTest() {
        driver.quit();
    }

}
