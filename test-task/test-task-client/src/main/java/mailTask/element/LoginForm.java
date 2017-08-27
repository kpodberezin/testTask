package mailTask.element;

import common.workarounds.ExBy;
import org.openqa.selenium.By;

public class LoginForm {
    public By loginInput = ExBy.id("mailbox__login", "Ввод логина");
    public By domainSelect = ExBy.id("mailbox__login__domain", "Ввод логина");
    public By passwordInput = ExBy.id("mailbox__password", "Ввод пароля");
    public By loginBtn = ExBy.id("mailbox__auth__button", "Кнопка Войти");
}
