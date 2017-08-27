package mailTask.step;

import mailTask.page.StartPage;
import ru.yandex.qatools.allure.annotations.Step;

import static common.hamcrest.Matchers.displayed;
import static common.selenide.TaskSelenide.$;

public class LoginSteps {

    @Step("Проверка отображения элементов страницы входа")
    public void shouldBeLoginPage() {
        $(new StartPage().onLoginForm().loginInput).shouldMatched(displayed());
        $(new StartPage().onLoginForm().passwordInput).shouldMatched(displayed());
        $(new StartPage().onLoginForm().loginBtn).shouldMatched(displayed());
    }

    @Step("Ввод логина {0},домена {1} и пароля {2}, Нажатие кнопки Войти")
    public void login(String login, String domain, String password) {
        $(new StartPage().onLoginForm().loginInput).setText(login);
        $(new StartPage().onLoginForm().domainSelect).selectOptionByValue(domain);
        $(new StartPage().onLoginForm().passwordInput).setText(password);
        $(new StartPage().onLoginForm().loginBtn).clickAfterLoad();
    }
}
