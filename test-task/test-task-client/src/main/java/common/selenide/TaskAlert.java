package common.selenide;

import org.openqa.selenium.Alert;

public interface TaskAlert extends Alert {

    boolean isAlertExist();

}
