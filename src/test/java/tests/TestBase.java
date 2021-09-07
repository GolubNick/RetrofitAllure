package tests;

import assertions.SoftAssertListener;
import assertions.SoftAssertProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import configuration.TestConfig;
import org.apache.commons.text.StringSubstitutor;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import utils.RetrofitResourceFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Listeners(SoftAssertListener.class)
public class TestBase {

    protected final RetrofitResourceFactory retrofitResourceFactory;
    protected SoftAssertions softAssertions;

    private static final TestConfig config = loadTestConfig("config.yaml", TestConfig.class);
    private static final String notSubstitutedParametersRegex = "\\n.*: \\$\\{.*}";

    private final String BASE_URL = config.getRetrofitConfig().getBaseUrl();
    protected final String TOKEN = config.getGeneralConfig().getToken();


    @BeforeMethod
    public void beforeMethod() {
        softAssertions = SoftAssertProvider.getSoftAssert();
    }

    public TestBase() {
        retrofitResourceFactory = new RetrofitResourceFactory(BASE_URL);
    }

    private static TestConfig loadTestConfig(String yaml, Class<TestConfig> configClass) {
        TestConfig config = null;
        try {
            String templateYaml = Files.readString(Path.of(yaml));
            Map<String, String> envs = System.getenv();
            StringSubstitutor sub = new StringSubstitutor(envs);
            String substitutedYaml = sub.replace(templateYaml);
            String resolvedYaml = substitutedYaml.replaceAll(notSubstitutedParametersRegex, "");
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            config = mapper.readValue(resolvedYaml, configClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }
}