package ru.innopolis;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.allure.annotations.Title;

import java.util.Date;

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
    Date withDate;
    Date atDate;

    public CurrDateTest(String currIn, Date withDate, Date atDate) {
        this.currIn = currIn;
        this.withDate = withDate;
        this.atDate = atDate;
    }
}
