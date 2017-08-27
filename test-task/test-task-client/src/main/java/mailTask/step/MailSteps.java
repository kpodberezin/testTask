package mailTask.step;

import mailTask.page.MailPage;
import ru.yandex.qatools.allure.annotations.Step;

import static common.hamcrest.Matchers.equalTo;
import static common.selenide.TaskSelenide.$;

public class MailSteps {


    @Step("Проверка содержания писмьма от {0} по теме {1} с текстом {2}")
    public void shouldBeFromThemeText(String from, String theme, String text) {
        $(new MailPage().onMailForm().from).shouldMatched(equalTo(from));
        $(new MailPage().onMailForm().theme).shouldMatched(equalTo(theme));
        $(new MailPage().onMailForm().textMail(text)).shouldMatched(equalTo(text));
    }
}
