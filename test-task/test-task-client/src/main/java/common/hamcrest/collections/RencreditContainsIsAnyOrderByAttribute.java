package common.hamcrest.collections;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.impl.WebElementsCollection;
import common.hamcrest.RencreditCollectionCondition;
import common.hamcrest.matchers.TextsMismatch;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

public class RencreditContainsIsAnyOrderByAttribute extends RencreditCollectionCondition {
    protected final List<String> expectedTexts;
    protected String attribute = "";

    public RencreditContainsIsAnyOrderByAttribute(String attr, String... expectedTexts) {
        this.expectedTexts = (asList(expectedTexts));
        this.attribute = attr;
    }

    public RencreditContainsIsAnyOrderByAttribute(String attr, List<String> expectedTexts) {
        if (expectedTexts.isEmpty()) {
            throw new IllegalArgumentException("текст отсутствует");
        }
        if (attr.isEmpty()) {
            throw new IllegalArgumentException("аттрибут отсутствует");
        }
        this.expectedTexts = unmodifiableList(expectedTexts);
    }

    @Override
    public boolean apply(List<WebElement> elements) {
        for (String expectedText : expectedTexts) {
            if (elements.stream().filter(webElement -> webElement.getAttribute(attribute).contains(expectedText)).collect(Collectors.toList()).size() != 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void fail(WebElementsCollection collection, List<WebElement> elements, Exception lastError, long timeoutMs) {
        if (elements == null || elements.isEmpty()) {
            ElementNotFound elementNotFound = new ElementNotFound(collection, expectedTexts, lastError);
            elementNotFound.timeoutMs = timeoutMs;
            throw elementNotFound;
        } else {
            throw new TextsMismatch(collection, ElementsCollection.texts(elements), expectedTexts, timeoutMs);
        }
    }

    @Override
    public String toString() {
        return "текст содержит " + expectedTexts;
    }
}
