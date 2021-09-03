package assertions;

import org.assertj.core.api.SoftAssertions;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;

public class SoftAssertListener implements IHookable {

    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        SoftAssertions softAssert = SoftAssertProvider.getSoftAssert();
        try {
            callBack.runTestMethod(testResult);
            softAssert.assertAll();
        }
        finally {
            SoftAssertProvider.unsetSoftAssert();
        }
    }
}