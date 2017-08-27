package mailTask.element;

import common.workarounds.ExBy;
import org.openqa.selenium.By;

public class TypeMailForm {
    public By inputLink = ExBy.xpath(".//a[@href='/messages/inbox/']", "Ссылка на входящие письма");
}
