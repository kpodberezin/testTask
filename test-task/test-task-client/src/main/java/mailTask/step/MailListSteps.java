package mailTask.step;

import mailTask.page.MailListPage;
import ru.yandex.qatools.allure.annotations.Step;

import static common.selenide.TaskSelenide.$;

public class MailListSteps {

    @Step("Переход к входящим письмам")
    public void goToInboxMail() {
        $(new MailListPage().onTypeMailForm().inputLink).clickAfterLoad();
    }

    @Step("Поиск и открытие письма с темой {0} и отправителем {1}")
    public void searchAndOpenMail(String theme, String from) {
        do {
            if ($(new MailListPage().onMailListForm().mail(theme, from)).exists()) {
                $(new MailListPage().onMailListForm().mail(theme, from)).clickAfterLoad();
                break;
            } else {
                $(new MailListPage().onMailListForm().nextListDiv).clickAfterLoad();
            }
        } while (!$(new MailListPage().onMailListForm().finishListDiv).exists());
    }
}
