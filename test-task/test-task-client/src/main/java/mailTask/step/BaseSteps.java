package mailTask.step;

import com.codeborne.selenide.Selenide;
import com.google.inject.Inject;
import mailTask.config.MailWebConfig;
import ru.yandex.qatools.allure.annotations.Step;

public class BaseSteps {
    private MailWebConfig config;

    @Inject
    public BaseSteps(MailWebConfig config) {
        this.config = config;
    }

    @Step("Открытие Mail")
    public void openStartPage() {
        Selenide.open(getConfig().url());
    }

    MailWebConfig getConfig() {
        return config;
    }
}

