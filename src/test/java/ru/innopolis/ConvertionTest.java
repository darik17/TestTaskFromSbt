package ru.innopolis;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

/**
 * Created by User on 19.06.2017.
 */
@RunWith(value = Parameterized.class)
public class ConvertionTest {

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
}
