package ru.netology.travel.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.travel.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentForm {
    private final SelenideElement headingCredit = $$("h3").findBy(Condition.text("Кредит по данным карты"));
    private final SelenideElement headingPayment = $$("h3").findBy(Condition.text("Оплата по карте"));
    private final SelenideElement numberCard = $("[placeholder = '0000 0000 0000 0000']");
    private final SelenideElement month = $("[placeholder = '08']");
    private final SelenideElement year = $("[placeholder = '22']");
    private final SelenideElement cardHolder = $(byText("Владелец")).parent().$("input");
    private final SelenideElement cVc = $("[placeholder = '999']");
    private final SelenideElement successNotification = $(".notification.notification_status_ok");
    private final SelenideElement errorNotification = $(".notification.notification_status_error");
    private final SelenideElement continueButton = $$("button").findBy(Condition.text("Продолжить"));

    public void fillForm(DataHelper.CardInfo cardInfo) {
        numberCard.setValue(cardInfo.getCardNumber());
        month.setValue(cardInfo.getMonth());
        year.setValue(cardInfo.getYear());
        cardHolder.setValue(cardInfo.getCardHolder());
        cVc.setValue(cardInfo.getCvc());
    }

    public void submit() {
        continueButton.click();
    }

    public void shouldShowSuccessNotification() {
        successNotification
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }

    public void shouldShowErrorNotification() {
        errorNotification
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."));
    }

    public void shouldShowHeadingCredit() {
        headingCredit.shouldBe(Condition.visible);
    }

    public void shouldShowHeadingPayment() {
        headingPayment.shouldBe(Condition.visible);
    }
}
