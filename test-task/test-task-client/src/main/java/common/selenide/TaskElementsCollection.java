package common.selenide;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.ex.UIAssertionError;
import com.codeborne.selenide.impl.WebElementsCollection;
import com.codeborne.selenide.logevents.SelenideLog;
import com.codeborne.selenide.logevents.SelenideLogger;
import common.hamcrest.RencreditCollectionCondition;

import static com.codeborne.selenide.Configuration.assertionMode;
import static com.codeborne.selenide.Configuration.collectionsTimeout;
import static com.codeborne.selenide.logevents.ErrorsCollector.validateAssertionMode;
import static com.codeborne.selenide.logevents.LogEvent.EventStatus.PASS;

public class TaskElementsCollection extends ElementsCollection {

    private WebElementsCollection collection;

    public TaskElementsCollection(WebElementsCollection collection) {
        super(collection);
        this.collection = collection;
    }

    public ElementsCollection shouldMatched(CollectionCondition... conditions) {
        return should("have", conditions);
    }

    public TaskElementsCollection shouldMatched(RencreditCollectionCondition... conditions) {
        validateAssertionMode();

        SelenideLog log = SelenideLogger.beginStep(collection.description(), "should have", conditions);
        try {
            for (RencreditCollectionCondition condition : conditions) {
                waitUntil(condition, collectionsTimeout);
            }
            SelenideLogger.commitStep(log, PASS);
            return this;
        } catch (Error error) {
            SelenideLogger.commitStep(log, error);
            switch (assertionMode) {
                case SOFT:
                    return this;
                default:
                    throw UIAssertionError.wrap(error, collectionsTimeout);
            }
        } catch (RuntimeException e) {
            SelenideLogger.commitStep(log, e);
            throw e;
        }
    }
}
