package common.selenide;

import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.LogEvent;
import com.codeborne.selenide.logevents.LogEventListener;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.yandex.qatools.allure.Allure;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.events.StepFailureEvent;
import ru.yandex.qatools.allure.events.StepFinishedEvent;
import ru.yandex.qatools.allure.events.StepStartedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class AllureSelenideListener implements LogEventListener {

    private final List<EventFormatter> formatters = getDefaultFormatters();

    @Override
    public void onEvent(LogEvent event) {
        getFormatter(event).ifPresent(formatter -> {
            String title = formatter.format(event);
            Allure.LIFECYCLE.fire(new StepStartedEvent(event.toString()).withTitle(title));
            if (event.getStatus().equals(LogEvent.EventStatus.FAIL)) {
                makeScreenshot();
                Allure.LIFECYCLE.fire(new StepFailureEvent().withThrowable(event.getError()));
            }
            Allure.LIFECYCLE.fire(new StepFinishedEvent());
        });
    }

    private Optional<EventFormatter> getFormatter(LogEvent event) {
        return formatters.stream()
                .filter(f -> f.isApplicable(event))
                .findFirst();
    }

    private List<EventFormatter> getDefaultFormatters() {
        List<EventFormatter> formatters = new ArrayList<>();
        formatters.add(new EventFormatter(
                Pattern.compile("\\$\\(open\\) (?<url>.*)"),
                "Открываем страницу \"${url}\""
        ));
        formatters.add(new EventFormatter(
                Pattern.compile("\\$\\((?<element>.*)\\) click\\(\\)"),
                "Кликаем на элемент \"${element}\""
        ));
        formatters.add(new EventFormatter(
                Pattern.compile("\\$\\((?<element>.*)\\) double click\\(\\)"),
                "Даблкликаем на элемент \"${element}\""
        ));
        formatters.add(new EventFormatter(
                Pattern.compile("\\$\\((?<element>.*)\\) press paste\\((?<value>.*)\\)"),
                "Вставляем в элемент \"${element}\" значение [${value}] из буфера обмена"
        ));
        formatters.add(new EventFormatter(
                Pattern.compile("\\$\\((?<element>.*)\\) press backspace\\(\\)"),
                "Очищаем элемент \"${element}\" по нажатию Backspace"
        ));
        formatters.add(new EventFormatter(
                Pattern.compile("\\$\\((?<element>.*)\\) hover\\(\\)"),
                "Наводим курсор мышки на элемент \"${element}\""
        ));
        formatters.add(new EventFormatter(
                Pattern.compile("\\$\\((?<element>.*)\\) wait until\\(\\[(?<condition>.*),(?<time>.*)\\]\\)"),
                "Ждем, пока элемент \"${element}\" будет в состоянии \"${condition}\" в течении [${time} млс]"
        ));
        formatters.add(new EventFormatter(
                Pattern.compile("\\$\\((?<element>.*)\\) wait while\\(\\[(?<condition>.*),(?<time>.*)\\]\\)"),
                "Ждем, пока элемент \"${element}\" не будет в состоянии \"${condition}\" в течении [${time} млс]"
        ));
        formatters.add(new EventFormatter(
                Pattern.compile("\\$\\((?<element>.*)\\) should\\((?<condition>.*)\\)"),
                "Проверяем, что элемент \"${element}\" в состоянии \"${condition}\""
        ));
        formatters.add(new EventFormatter(
                Pattern.compile("\\$\\((?<element>.*)\\) should matched\\(\\[(?<message>.*),(?<condition>.*)\\]\\)"),
                "Проверяем, что элемент \"${element}\" ${condition}"
        ));
        formatters.add(new EventFormatter(
                Pattern.compile("\\$\\((?<element>.*)\\) should matched\\((?<condition>.*)\\)"),
                "Проверяем, что элемент \"${element}\" ${condition}"
        ));
        formatters.add(new EventFormatter(
                Pattern.compile("\\$\\((?<element>.*)\\) should have\\((?<condition>.*)\\)"),
                "Проверяем, что элемент \"${element}\" в состоянии \"${condition}\""
        ));
        formatters.add(new EventFormatter(
                Pattern.compile("\\$\\((?<element>.*)\\) set text\\((?<value>.*)\\)"),
                "Вводим в элемент \"${element}\" значение [${value}]"
        ));
        formatters.add(new EventFormatter(
                Pattern.compile("\\$\\((?<element>.*)\\) click \\(()\\)"),
                "Кликаем на элемент \"${element}\" "
        ));
        formatters.add(new EventFormatter(
                Pattern.compile("\\$\\(confirm\\) (?<value>.*)"),
                "Алерт содержит текст \"${value}\""
        ));
        formatters.add(new EventFormatter(
                Pattern.compile("\\$\\(assertThat\\) (?<value>.*)"),
                "${value}"
        ));
        formatters.add(new EventFormatter(
                Pattern.compile("\\$\\((?<element>.*)\\) select option containing text\\((?<value>.*)\\)"),
                "Выбираем из выпадающего списка \"${element}\" значение \"${value}\""
        ));
        return formatters;
    }

    @Attachment(value = "Скриншот страницы", type = "image/png")
    private byte[] makeScreenshot() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    private class EventFormatter {

        private final Pattern pattern;

        private final String replacement;

        EventFormatter(Pattern pattern, String replacement) {
            this.replacement = replacement;
            this.pattern = pattern;
        }

        boolean isApplicable(LogEvent event) {
            return pattern.matcher(event.toString()).find();
        }


        String format(LogEvent event) {
            return pattern.matcher(event.toString()).replaceAll(replacement);
        }
    }
}
