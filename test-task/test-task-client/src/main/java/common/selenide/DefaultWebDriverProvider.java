package common.selenide;

import com.codeborne.selenide.WebDriverProvider;
import com.codeborne.selenide.logevents.SelenideLogger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.remote.CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION;
import static org.openqa.selenium.remote.CapabilityType.PAGE_LOAD_STRATEGY;

public class DefaultWebDriverProvider implements WebDriverProvider {

    @Override
    public WebDriver createDriver(DesiredCapabilities desiredCapabilities) {
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("pdfjs.disabled", true);
        chromePrefs.put("disable-popup-blocking", true);
        chromePrefs.put("download.directory_upgrade", true);
        chromePrefs.put("download.extensions_to_open", "");
        chromePrefs.put("download.prompt_for_download", false);
        chromePrefs.put("plugins.always_open_pdf_externally", true);
        chromePrefs.put("download.default_directory", TaskConfiguration.getConfigDefaultDownloadPath());

        ChromeOptions options = new ChromeOptions();
        List<String> args = new ArrayList<>();

        args.add("disable-logging");
        args.add("disable-translate");
        args.add("disable-plugins");
        args.add("disable-extensions");
        args.add("disable-web-security");
        args.add("no-default-browser-check");
        args.add("no-sandbox");
        args.add("no-first-run");
        args.add("silent");
        args.add("start-maximized");
        args.add("user-agent=Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36 autotests");

        options.addArguments(args);
        options.setExperimentalOption("prefs", chromePrefs);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        // capabilities.setCapability("chrome.binary", "C:\\PROGRA~1\\Google\\Chrome\\Application\\chrome.exe");
        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "ignore");
        capabilities.setCapability(ENSURING_CLEAN_SESSION, true);
        capabilities.setCapability(PAGE_LOAD_STRATEGY, "normal");

        WebDriver driver = new ChromeDriver(capabilities);
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
        driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);

        SelenideLogger.addListener("common/hamcrest/allure", new AllureSelenideListener());

        return driver;
    }
}
