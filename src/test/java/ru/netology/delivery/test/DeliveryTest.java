package ru.netology.delivery.test;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.Duration;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.delivery.data.DataGenerator.*;

public class DeliveryTest {
    private Faker faker;


    @Test
    void shouldSuccessfulPlanAndReplanMeeting() {
        open("http://localhost:9999");
        var validUser = Registration.genUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = genDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = genDate(daysToAddForSecondMeeting);

        $("[data-test-id='city']input").setValue(genCity());
        $("[data-test-id='date']input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date']input").setValue(firstMeetingDate);
        $("[data-test-id='name']input").setValue(genName());
        $("[data-test-id='phone']input").setValue(genPhone());
        $("[data-test-id='agreement']input").click();
        $(byText("Запланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'].notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + firstMeetingDate))
                .shouldBe(visible);
        $("[data-test-id='date']input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date']input").setValue(secondMeetingDate);
        $(byText("Запланировать")).click();
        $("[data-test-id='success-notification'].notification__content")
                .shouldHave(exactText("У вас уже запланирована встреча на другую дату. Перепланировать? "))
                .shouldBe(visible);
        $("[data-test-id='replan-notification']button").click();
        $("[data-test-id='success-notification'].notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + secondMeetingDate))
                .shouldBe(visible);


    }
}
