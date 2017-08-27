package mailTask.element;

import common.workarounds.ExBy;
import org.openqa.selenium.By;

public class MailForm {
    public By theme = ExBy.className("b-letter__head__subj__text", "Тема письма");
    public By from = ExBy.xpath(".//div[@data-mnemo='from']/span/span[1]", "Отправитель");

    public By textMail(String mailText) {
        return ExBy.xpath(".//div[contains(text(),'" + mailText + "')]", "Текст письма");
    }
}
