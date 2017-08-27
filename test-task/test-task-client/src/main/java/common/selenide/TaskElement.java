package common.selenide;

import com.codeborne.selenide.SelenideElement;
import org.hamcrest.Matcher;

public interface TaskElement extends SelenideElement {

    TaskElement shouldMatched(String message, Matcher matcher);

    TaskElement shouldMatched(Matcher matcher);

    SelenideElement setText(String text);

    SelenideElement pressPaste(String text);

    SelenideElement pressBackspace();

    SelenideElement clickAfterLoad();

    SelenideElement selectOptionAfterLoad(String option);

    SelenideElement clickJs();

    SelenideElement blur();
}
