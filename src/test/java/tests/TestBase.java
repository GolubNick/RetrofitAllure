package tests;

import assertions.SoftAssertListener;
import assertions.SoftAssertProvider;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import utils.RetrofitResourceFactory;

import java.util.Properties;

@Listeners(SoftAssertListener.class)
public class TestBase {
    protected Properties props;
    protected SoftAssertions softAssertions;


    @BeforeMethod
    public void beforeMethod() {
        softAssertions = SoftAssertProvider.getSoftAssert();
    }

    protected final RetrofitResourceFactory resourceFactory;

    public TestBase() {
        resourceFactory = new RetrofitResourceFactory("https://gorest.co.in");
    }
}
