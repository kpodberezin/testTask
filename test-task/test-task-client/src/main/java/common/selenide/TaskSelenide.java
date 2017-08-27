package common.selenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.ex.DialogTextMismatch;
import com.codeborne.selenide.impl.BySelectorCollection;
import com.codeborne.selenide.impl.ElementFinder;
import com.codeborne.selenide.logevents.SelenideLog;
import com.codeborne.selenide.logevents.SelenideLogger;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.logevents.LogEvent.EventStatus.PASS;
import static common.selenide.TaskConfiguration.RencreditTimeout.MIDDLE_TIMEOUT;
import static common.selenide.TaskConfiguration.RencreditTimeout.SMALL_TIMEOUT;

public class TaskSelenide extends Selenide {

    public static TaskElement $(By seleniumSelector) {
        return ElementFinder.wrap(TaskElement.class, null, seleniumSelector, 0);
    }

    public static TaskElementsCollection $$(By seleniumSelector) {
        return new TaskElementsCollection(new BySelectorCollection(seleniumSelector));
    }

    public static String alertConfirm() {
        return alertConfirm("", SMALL_TIMEOUT.getValue());
    }

    public static String alertConfirm(String expectedDialogText) {
        return alertConfirm(expectedDialogText, MIDDLE_TIMEOUT.getValue());
    }

    public static String alertConfirmContainsText(String expectedDialogText) {
        return alertConfirmContainsText(expectedDialogText, MIDDLE_TIMEOUT.getValue());
    }

    public static String alertConfirm(String expectedDialogText, long timeout) {
        SelenideLog log = SelenideLogger.beginStep("alertConfirm", expectedDialogText);
        if (!doDismissModalDialogs() && isAlertExist(timeout)) {
            Alert alert = switchTo().alert();
            String actualDialogText = alert.getText();
            alert.accept();
            checkDialogText(expectedDialogText, actualDialogText);
            SelenideLogger.commitStep(log, PASS);
            return actualDialogText;
        } else {
            return null;
        }
    }

    public static String alertConfirmContainsText(String expectedDialogText, long timeout) {
        SelenideLog log = SelenideLogger.beginStep("alertConfirm", expectedDialogText);
        if (!doDismissModalDialogs() && isAlertExist(timeout)) {
            Alert alert = switchTo().alert();
            String actualDialogText = alert.getText();
            alert.accept();
            checkDialogContainsText(expectedDialogText, actualDialogText);
            SelenideLogger.commitStep(log, PASS);
            return actualDialogText;
        } else {
            return null;
        }
    }

    private static boolean isAlertExist(long timeout) {
        try {
            return Wait().withTimeout(timeout, TimeUnit.MILLISECONDS).until(ExpectedConditions.alertIsPresent()) != null;
        } catch (TimeoutException e) {
            return false;
        }
    }

    private static boolean doDismissModalDialogs() {
        return !WebDriverRunner.supportsModalDialogs() || Configuration.dismissModalDialogs;
    }

    private static void checkDialogText(String expectedDialogText, String actualDialogText) {
        if (!expectedDialogText.isEmpty() && !expectedDialogText.equals(actualDialogText)) {
            Screenshots.takeScreenShot(Selenide.class.getName(), Thread.currentThread().getName());
            throw new DialogTextMismatch(actualDialogText, expectedDialogText);
        }
    }

    private static void checkDialogContainsText(String expectedDialogText, String actualDialogText) {
        if (!expectedDialogText.isEmpty() && !expectedDialogText.contains(actualDialogText)) {
            Screenshots.takeScreenShot(Selenide.class.getName(), Thread.currentThread().getName());
            throw new DialogTextMismatch(actualDialogText, expectedDialogText);
        }
    }

    public static void assertThat(String actual, boolean assertion) {
        if (!assertion) {
            throw new AssertionError("Не " + actual);
        } else {
            SelenideLog log = SelenideLogger.beginStep("assertThat", actual);
            SelenideLogger.commitStep(log, PASS);
        }
    }

    public static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
        if (!matcher.matches(actual)) {
            Description description = new StringDescription();
            description.appendText(reason)
                    .appendText("\nОжидалось: ")
                    .appendDescriptionOf(matcher)
                    .appendText("\n     Но: ");
            matcher.describeMismatch(actual, description);

            throw new AssertionError(description.toString());
        } else {
            SelenideLog log = SelenideLogger.beginStep("assertThat", reason);
            SelenideLogger.commitStep(log, PASS);
        }
    }

    public static void pressEscape() {
        actions().sendKeys(Keys.ESCAPE).build().perform();
    }

    public static void pressDoubleEscape() {
        actions().sendKeys(Keys.ESCAPE).sendKeys(Keys.ESCAPE).build().perform();
    }
}
