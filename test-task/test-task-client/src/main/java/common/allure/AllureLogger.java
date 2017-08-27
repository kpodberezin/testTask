package common.allure;

import org.apache.log4j.Logger;
import ru.yandex.qatools.allure.events.*;
import ru.yandex.qatools.allure.experimental.LifecycleListener;

import java.util.Deque;
import java.util.LinkedList;

import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

public class AllureLogger extends LifecycleListener {

    private static final Logger LOGGER = Logger.getLogger(AllureLogger.class.getSimpleName());

    private Deque<String> eventNames = new LinkedList<>();

    @Override
    public void fire(StepStartedEvent event) {
        eventNames.push(event.getName());
        LOGGER.info(createLoggerMessage(">", defaultIfBlank(event.getTitle(), event.getName())));
    }

    @Override
    public void fire(StepEvent event) {
        if (event instanceof StepFailureEvent) {
            LOGGER.info(createLoggerMessage("X", "шаг сломан"));
        }

        if (event instanceof StepCanceledEvent) {
            LOGGER.info(createLoggerMessage("?", "шаг отменен"));
        }
    }

    @Override
    public void fire(StepFinishedEvent event) {

        LOGGER.info(createLoggerMessage("<", "шаг закончен"));
        eventNames.poll();
    }

    private String createLoggerMessage(String marker, String step) {
        return String.format("%s [ %s ] %s", getNestedStepOffset(), marker, step);
    }

    private String getNestedStepOffset() {
        return new String(new char[eventNames.isEmpty() ? 0 : eventNames.size() - 1]).replaceAll("\0", "   ");
    }

}