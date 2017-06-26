package ru.innopolis;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
     * @return val, currFrom, currIn
     */
    @Parameterized.Parameters
    public static Collection<String[]> readDataFromFile(){
        List<String[]> list = new ArrayList<>();
        String temStr;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileParam))){

            while ((temStr = reader.readLine()) != null){
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
    public void seleniumSet(){
        System.setProperty("webdriver.chrome.driver","src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.navigate().to("https://www.sberbank.ru/ru/quotes/converter");
    }
}
