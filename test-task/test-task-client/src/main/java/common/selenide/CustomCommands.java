package common.selenide;

import com.codeborne.selenide.*;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Selenide.actions;
import static common.selenide.TaskSelenide.$;

public class CustomCommands {

    private CustomCommands() {
    }

    @SuppressWarnings("unchecked")
    static Command shouldMatched() {
        return (proxy, locator, args) -> {
            String message = null;
            Matcher<WebElement> matcher = null;

            if (args[0] instanceof String) {
                message = (String) args[0];
                matcher = (Matcher<WebElement>) args[1];
            }

            if (args[0] instanceof Matcher) {
                message = "";
                matcher = (Matcher<WebElement>) args[0];
            }

            if (!matcher.matches(proxy)) {
                StringDescription description = new StringDescription();
                description.appendText(message);

                description.appendText("\nОжидалось: ")
                        .appendText("элемент ").appendValue(locator.getSearchCriteria()).appendText(" ")
                        .appendDescriptionOf(matcher);

                description.appendText("\n       но: ");
                matcher.describeMismatch(proxy, description);

                throw new AssertionError(description.toString());
            }
            return proxy;
        };
    }

    static Command setText() {
        return (proxy, locator, args) -> {
            TaskElement element = (TaskElement) proxy;

            String text = (String) args[0];
            int i = 0;
            do {
                element.setValue(text);
                Selenide.sleep(Configuration.pollingInterval);
                i++;
            }
            while (!element.getValue().replace(" ", "").equalsIgnoreCase(text) && i < 5);

            return proxy;
        };
    }

    static Command pressPaste() {
        return (proxy, locator, args) -> {
            TaskElement element = (TaskElement) proxy;

            String text = (String) args[0];
            int i = 0;
            do {
                element.clear();
                element.sendKeys(Keys.CONTROL + "v");
                Selenide.sleep(Configuration.pollingInterval);
                i++;
            }
            while (!element.getValue().replace(" ", "").equalsIgnoreCase(text) && i < 5);

            return proxy;
        };
    }

    static Command pressBackspace() {
        return (proxy, locator, args) -> {
            TaskElement element = (TaskElement) proxy;
            element.sendKeys(Keys.BACK_SPACE);
            Selenide.sleep(Configuration.pollingInterval);
            return proxy;
        };
    }

    static Command clickAfterLoad() {
        return (proxy, locator, args) -> {
            TaskElement element = (TaskElement) proxy;
            element.should(Condition.exist);
            element.should(Condition.visible);
            element.should(Condition.enabled);
            element.click();
            Selenide.sleep(Configuration.pollingInterval);
            return proxy;
        };
    }

    static Command selectOptionAfterLoad() {
        return (proxy, locator, args) -> {
            TaskElement element = (TaskElement) proxy;
            element.should(Condition.exist);
            element.should(Condition.visible);
            element.should(Condition.enabled);
            element.selectOption((String) args[0]);
            Selenide.sleep(Configuration.pollingInterval);
            return proxy;
        };
    }

    static Command clickJs() {
        return (proxy, locator, args) -> {
            TaskElement element = (TaskElement) proxy;
            ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("return arguments[0].click();", element);
            Selenide.sleep(Configuration.pollingInterval);
            return proxy;
        };
    }

    static Command blur() {
        return (proxy, locator, args) -> {
            TaskElement element = (TaskElement) proxy;
            actions().clickAndHold().moveToElement($(element)).perform();
            Selenide.sleep(Configuration.pollingInterval);
            return proxy;
        };
    }
}
