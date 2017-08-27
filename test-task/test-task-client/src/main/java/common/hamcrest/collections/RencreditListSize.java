package common.hamcrest.collections;

import com.codeborne.selenide.impl.WebElementsCollection;
import common.hamcrest.RencreditCollectionCondition;
import common.hamcrest.matchers.ListSizeMismatch;
import org.openqa.selenium.WebElement;

import java.util.List;


public class RencreditListSize extends RencreditCollectionCondition {

    protected final int expectedSize;

    public RencreditListSize(int expectedSize) {
        this.expectedSize = expectedSize;
    }

    @Override
    public boolean apply(List<WebElement> elements) {
        return elements.size() == expectedSize;
    }

    @Override
    public void fail(WebElementsCollection collection, List<WebElement> elements, Exception lastError, long timeoutMs) {
        throw new ListSizeMismatch("=", expectedSize, collection, elements, lastError, timeoutMs);
    }

    @Override
    public String toString() {
        return String.format("size(%s)", expectedSize);
    }
}
