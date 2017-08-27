package mailTask.element;

import common.workarounds.ExBy;
import org.openqa.selenium.By;

public class MailListForm {

    public By nextListDiv = ExBy.id(".//div[@data-name='next']", "Кнопка Следующая страница");
    public By finishListDiv = ExBy.id(".//div[@aria-disabled='disabled']", "Последняя страница");

    public By mail(String theme, String from) {
        return ExBy.xpath(".//div[text()='" + from + "']/..//div[text()='" + theme + "']", "Письмо в списке");
    }
}
