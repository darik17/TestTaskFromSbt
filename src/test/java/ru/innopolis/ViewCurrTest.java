package ru.innopolis;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

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
}
