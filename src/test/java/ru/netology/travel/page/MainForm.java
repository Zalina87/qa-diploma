package ru.netology.travel.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$;

public class MainForm {
    private final SelenideElement paymentButton = $$("button").findBy(Condition.text("Купить"));
    private final SelenideElement creditButton = $$("button").findBy(Condition.text("Купить в кредит"));

    public PaymentForm goToPayment() {
        paymentButton.click();
        return new PaymentForm();
    }

    public PaymentForm goToCredit() {
        creditButton.click();
        return new PaymentForm();
    }
}
