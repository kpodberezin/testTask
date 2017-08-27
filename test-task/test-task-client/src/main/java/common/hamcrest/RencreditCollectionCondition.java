package common.hamcrest;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.impl.WebElementsCollection;
import com.google.common.base.Predicate;
import common.hamcrest.collections.RencreditContainsIsAnyOrder;
import common.hamcrest.collections.RencreditContainsIsAnyOrderByAttribute;
import common.hamcrest.collections.RencreditContainsIsAnyOrderExactMatch;
import common.hamcrest.collections.RencreditListSize;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class RencreditCollectionCondition extends CollectionCondition implements Predicate<List<WebElement>> {

    public static RencreditCollectionCondition size(int expectedSize) {
        return new RencreditListSize(expectedSize);
    }

    public static RencreditCollectionCondition containsInAnyOrderExactMatch(String... array) {
        return new RencreditContainsIsAnyOrderExactMatch(array);
    }

    public static RencreditCollectionCondition containsInAnyOrderExactMatch(List<String> array) {
        return new RencreditContainsIsAnyOrderExactMatch(array);
    }

    public static RencreditCollectionCondition containsInAnyOrder(String... array) {
        return new RencreditContainsIsAnyOrder(array);
    }

    public static RencreditCollectionCondition containsInAnyOrder(List<String> array) {
        return new RencreditContainsIsAnyOrder(array);
    }

    public static RencreditCollectionCondition containsInAnyOrder(String attr, List<String> array) {
        return new RencreditContainsIsAnyOrderByAttribute(attr, array);
    }

    public static RencreditCollectionCondition containsInAnyOrder(String attr, String... array) {
        return new RencreditContainsIsAnyOrderByAttribute(attr, array);
    }

    public abstract void fail(WebElementsCollection collection, List<WebElement> elements, Exception lastError, long timeoutMs);
}
