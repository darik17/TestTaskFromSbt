package ru.innopolis;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import ru.yandex.qatools.allure.annotations.Title;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 27.06.2017.
 */
@Title("Тест измения графикы валюты по датам")
@RunWith(value = Parameterized.class)
public class CurrDateTest {

    private static final Logger LOGGER = Logger.getLogger(CurrDateTest.class);
    private static WebDriver driver;
    private static String fileParam = "src\\main\\resources\\currdatetest.csv";
    private String currIn;
    private String withDate;
    private String atDate;

    public CurrDateTest(String currIn, String withDate, String atDate) {
        this.currIn = currIn;
        this.withDate = withDate;
        this.atDate = atDate;
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

    @Test
    public void changeDate(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate stopWithDate = LocalDate.of(2002,01,01);
        LocalDate stopAtDate = LocalDate.now();

        LocalDate withLocalDate = LocalDate.parse((CharSequence) withDate,dateTimeFormatter);
        LocalDate atLocalDate = LocalDate.parse((CharSequence) atDate,dateTimeFormatter);

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




    }


}
