package ru.netology.travel.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.travel.data.DataHelper;
import ru.netology.travel.page.MainForm;
import ru.netology.travel.page.PaymentForm;

import static com.codeborne.selenide.Selenide.open;

public class PaymentTest {
    @BeforeEach
    void setup() {
        open("http://localhost:8080/");
    }

    @Test
    @DisplayName("Должен принять оплату с валидными данными")
    void shouldSuccessWithValidCard() {
        MainForm mainForm = new MainForm();
        PaymentForm paymentForm = mainForm.goToPayment();
        paymentForm.shouldShowHeadingPayment();
        paymentForm.fillForm(DataHelper.getApprovedCard());
        paymentForm.submit();
        paymentForm.shouldShowSuccessNotification();
    }
}
