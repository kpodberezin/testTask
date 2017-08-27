import com.codeborne.selenide.Selenide;
import com.google.inject.Inject;
import mailTask.module.MailWebDriverModule;
import mailTask.step.BaseSteps;
import mailTask.step.LoginSteps;
import mailTask.step.MailListSteps;
import mailTask.step.MailSteps;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

@Features("Задание от crystas")
@Stories("Работа с почтой")
@Guice(modules = {MailWebDriverModule.class})
public class TaskTests {

    @Inject
    private BaseSteps baseSteps;

    @Inject
    private LoginSteps loginSteps;

    @Inject
    private MailListSteps mailListSteps;

    @Inject
    private MailSteps mailSteps;


    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        baseSteps.openStartPage();
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        Selenide.close();
    }

    @Test
    @Title("Тестовое задание")
    public void taskTest() {
        loginSteps.shouldBeLoginPage();
        loginSteps.login("?", "?", "?");
        mailListSteps.goToInboxMail();
        mailListSteps.searchAndOpenMail("?",
                "?");
        mailSteps.shouldBeFromThemeText("?",
                "?",
                "?");
    }
}
