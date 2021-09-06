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
    protected static final String TOKEN = "Bearer 34c12dd98998a249ad7ca9bed3439155ceae6f80c9777a3193175f7ef8fe1283";


    @BeforeMethod
    public void beforeMethod() {
        softAssertions = SoftAssertProvider.getSoftAssert();
    }

    protected final RetrofitResourceFactory retrofitResourceFactory;

    public TestBase() {
        retrofitResourceFactory = new RetrofitResourceFactory("https://gorest.co.in");
    }
}