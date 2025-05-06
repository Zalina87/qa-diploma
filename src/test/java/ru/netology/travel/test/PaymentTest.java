package ru.netology.travel.test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Attachment;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.netology.travel.data.DataHelper;
import ru.netology.travel.data.SQLHelper;
import ru.netology.travel.page.MainForm;
import ru.netology.travel.page.PaymentForm;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentTest {
    @BeforeAll
    static void setupAllure() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setup() {
        open("http://localhost:8080/");
    }

    @AfterAll
    static void allureDown() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @Story("Должен принять оплату с валидными данными")
    @DisplayName("Должен принять оплату с валидными данными")
    void shouldSuccessPaymentWithValidCard() {
        MainForm mainForm = new MainForm();
        PaymentForm paymentForm = mainForm.goToPayment();
        paymentForm.shouldShowHeadingPayment();
        paymentForm.fillForm(DataHelper.getApprovedCard());
        int rowsCount = SQLHelper.getPaymentRowsCount(SQLHelper.TABLE_PAYMENT);
        paymentForm.submit();
        paymentForm.shouldShowSuccessNotification();
        assertEquals(1, SQLHelper.getPaymentRowsCount(SQLHelper.TABLE_PAYMENT) - rowsCount);
        assertEquals("APPROVED", SQLHelper.getLatestPaymentStatus(SQLHelper.TABLE_PAYMENT));
        attachScreenshot("shouldSuccessPaymentWithValidCard");
    }

    @Test
    @Story("Должен принять заявку на кредит с валидными данными")
    @DisplayName("Должен принять заявку на кредит с валидными данными")
    void shouldSuccessCreditWithValidCard() {
        MainForm mainForm = new MainForm();
        PaymentForm creditForm = mainForm.goToCredit();
        creditForm.shouldShowHeadingCredit();
        creditForm.fillForm(DataHelper.getApprovedCard());
        int rowsCount = SQLHelper.getPaymentRowsCount(SQLHelper.TABLE_CREDIT);
        creditForm.submit();
        creditForm.shouldShowSuccessNotification();
        assertEquals(1, SQLHelper.getPaymentRowsCount(SQLHelper.TABLE_CREDIT) - rowsCount);
        assertEquals("APPROVED", SQLHelper.getLatestPaymentStatus(SQLHelper.TABLE_CREDIT));
        attachScreenshot("shouldSuccessCreditWithValidCard");
    }

    @Test
    @Story("Должен не принять оплату с номером заблокированной карты")
    @DisplayName("Должен не принять оплату с номером заблокированной карты")
    void shouldErrorPaymentWithDeclinedCard() {
        MainForm mainForm = new MainForm();
        PaymentForm paymentForm = mainForm.goToPayment();
        paymentForm.shouldShowHeadingPayment();
        paymentForm.fillForm(DataHelper.getDeclinedCard());
        int rowsCount = SQLHelper.getPaymentRowsCount(SQLHelper.TABLE_PAYMENT);
        paymentForm.submit();
        paymentForm.shouldShowErrorNotification();
        assertEquals(1, SQLHelper.getPaymentRowsCount(SQLHelper.TABLE_PAYMENT) - rowsCount);
        assertEquals("DECLINED", SQLHelper.getLatestPaymentStatus(SQLHelper.TABLE_PAYMENT));
        attachScreenshot("shouldErrorPaymentWithDeclinedCard");
    }

    @Test
    @Story("Должен не принять оплату в кредит с номером заблокированной карты")
    @DisplayName("Должен не принять оплату в кредит с номером заблокированной карты")
    void shouldErrorCreditWithDeclinedCard() {
        MainForm mainForm = new MainForm();
        PaymentForm creditForm = mainForm.goToCredit();
        creditForm.shouldShowHeadingCredit();
        creditForm.fillForm(DataHelper.getDeclinedCard());
        int rowsCount = SQLHelper.getPaymentRowsCount(SQLHelper.TABLE_CREDIT);
        creditForm.submit();
        creditForm.shouldShowErrorNotification();
        assertEquals(1, SQLHelper.getPaymentRowsCount(SQLHelper.TABLE_CREDIT) - rowsCount);
        assertEquals("DECLINED", SQLHelper.getLatestPaymentStatus(SQLHelper.TABLE_CREDIT));
        attachScreenshot("shouldErrorCreditWithDeclinedCard");
    }

    @Test
    @Story("Должен не принять оплату с не валидным номером карты")
    @DisplayName("Должен не принять оплату с не валидным номером карты")
    void shouldNotSuccessPaymentWithNotValidCard() {
        MainForm mainForm = new MainForm();
        PaymentForm paymentForm = mainForm.goToPayment();
        paymentForm.shouldShowHeadingPayment();
        paymentForm.fillCardNumber("4444 4444 4444 44");
        paymentForm.fillMonth(DataHelper.getValidMonthWithShift(1));
        paymentForm.fillYear(DataHelper.getValidYearWithShift(2));
        paymentForm.fillCardholder(DataHelper.generateValidFullUserName());
        paymentForm.fillCvc(DataHelper.getRandomCvc());
        paymentForm.submit();
        paymentForm.shouldShowErrorSubInputWithInvalidCardNumber();
        attachScreenshot("shouldNotSuccessPaymentWithNotValidCard");
    }

    @Test
    @Story("Должен не принять оплату в кредит с не валидным номером карты")
    @DisplayName("Должен не принять оплату в кредит с не валидным номером карты")
    void shouldNotSuccessCreditWithNotValidCard() {
        MainForm mainForm = new MainForm();
        PaymentForm creditForm = mainForm.goToCredit();
        creditForm.shouldShowHeadingCredit();
        creditForm.fillCardNumber("4444 4444 4444 44");
        creditForm.fillMonth(DataHelper.getValidMonthWithShift(1));
        creditForm.fillYear(DataHelper.getValidYearWithShift(2));
        creditForm.fillCardholder(DataHelper.generateValidFullUserName());
        creditForm.fillCvc(DataHelper.getRandomCvc());
        creditForm.submit();
        creditForm.shouldShowErrorSubInputWithInvalidCardNumber();
        attachScreenshot("shouldNotSuccessCreditWithNotValidCard");
    }

    @Test
    @Story("Должен не принять оплату с не валидным месяцем")
    @DisplayName("Должен не принять оплату с не валидным месяцем")
    void shouldNotSuccessPaymentWithNotValidMonth() {
        MainForm mainForm = new MainForm();
        PaymentForm paymentForm = mainForm.goToPayment();
        paymentForm.shouldShowHeadingPayment();
        paymentForm.fillCardNumber(DataHelper.getApprovedCardNumber());
        paymentForm.fillMonth("13");
        paymentForm.fillYear(DataHelper.getValidYearWithShift(2));
        paymentForm.fillCardholder(DataHelper.generateValidFullUserName());
        paymentForm.fillCvc(DataHelper.getRandomCvc());
        paymentForm.submit();
        paymentForm.shouldShowErrorSubInputWithInvalidMonth();
        attachScreenshot("shouldNotSuccessPaymentWithNotValidMonth");
    }

    @Test
    @Story("Должен не принять оплату в кредит с не валидным месяцем")
    @DisplayName("Должен не принять оплату в кредит с не валидным месяцем")
    void shouldNotSuccessCreditWithNotValidMonth() {
        MainForm mainForm = new MainForm();
        PaymentForm creditForm = mainForm.goToCredit();
        creditForm.shouldShowHeadingCredit();
        creditForm.fillCardNumber(DataHelper.getApprovedCardNumber());
        creditForm.fillMonth("13");
        creditForm.fillYear(DataHelper.getValidYearWithShift(2));
        creditForm.fillCardholder(DataHelper.generateValidFullUserName());
        creditForm.fillCvc(DataHelper.getRandomCvc());
        creditForm.submit();
        creditForm.shouldShowErrorSubInputWithInvalidMonth();
        attachScreenshot("shouldNotSuccessCreditWithNotValidMonth");
    }

    @Test
    @Story("Должен не принять оплату с не валидным годом")
    @DisplayName("Должен не принять оплату с не валидным годом")
    void shouldNotSuccessPaymentWithNotValidYear() {
        MainForm mainForm = new MainForm();
        PaymentForm paymentForm = mainForm.goToPayment();
        paymentForm.shouldShowHeadingPayment();
        paymentForm.fillCardNumber(DataHelper.getApprovedCardNumber());
        paymentForm.fillMonth(DataHelper.getValidMonthWithShift(1));
        paymentForm.fillYear("19");
        paymentForm.fillCardholder(DataHelper.generateValidFullUserName());
        paymentForm.fillCvc(DataHelper.getRandomCvc());
        paymentForm.submit();
        paymentForm.shouldShowErrorSubInputWithInvalidYear();
        attachScreenshot("shouldNotSuccessPaymentWithNotValidYear");
    }

    @Test
    @Story("Должен не принять оплату в кредит с не валидным годом")
    @DisplayName("Должен не принять оплату в кредит с не валидным годом")
    void shouldNotSuccessCreditWithNotValidYear() {
        MainForm mainForm = new MainForm();
        PaymentForm creditForm = mainForm.goToCredit();
        creditForm.shouldShowHeadingCredit();
        creditForm.fillCardNumber(DataHelper.getApprovedCardNumber());
        creditForm.fillMonth(DataHelper.getValidMonthWithShift(1));
        creditForm.fillYear("19");
        creditForm.fillCardholder(DataHelper.generateValidFullUserName());
        creditForm.fillCvc(DataHelper.getRandomCvc());
        creditForm.submit();
        creditForm.shouldShowErrorSubInputWithInvalidYear();
        attachScreenshot("shouldNotSuccessCreditWithNotValidYear");
    }

    //нет предупреждения об ошибке при заполнении поля, приходит просто отказ от банка без уточнения, из-за чего
    @Test
    @Story("Должен не принять оплату с не валидным владельцем")
    @DisplayName("Должен не принять оплату с не валидным владельцем")
    void shouldNotSuccessPaymentWithNotValidCardholder() {
        MainForm mainForm = new MainForm();
        PaymentForm paymentForm = mainForm.goToPayment();
        paymentForm.shouldShowHeadingPayment();
        paymentForm.fillCardNumber(DataHelper.getApprovedCardNumber());
        paymentForm.fillMonth(DataHelper.getValidMonthWithShift(1));
        paymentForm.fillYear(DataHelper.getValidYearWithShift(2));
        paymentForm.fillCardholder("1");
        paymentForm.fillCvc(DataHelper.getRandomCvc());
        paymentForm.submit();
        paymentForm.shouldShowErrorSubInputWithInvalidCardholder();
        attachScreenshot("shouldNotSuccessPaymentWithNotValidCardholder");
    }

    //нет ошибки при запрллнении поля, приходит просто отказ от банка без уточнения, из-за чего
    @Test
    @Story("Должен не принять оплату в кредит с не валидным владельцем")
    @DisplayName("Должен не принять оплату в кредит с не валидным владельцем")
    void shouldNotSuccessCreditWithNotValidCardholder() {
        MainForm mainForm = new MainForm();
        PaymentForm creditForm = mainForm.goToCredit();
        creditForm.shouldShowHeadingCredit();
        creditForm.fillCardNumber(DataHelper.getApprovedCardNumber());
        creditForm.fillMonth(DataHelper.getValidMonthWithShift(1));
        creditForm.fillYear(DataHelper.getValidYearWithShift(2));
        creditForm.fillCardholder("1");
        creditForm.fillCvc(DataHelper.getRandomCvc());
        creditForm.submit();
        creditForm.shouldShowErrorSubInputWithInvalidCardholder();
        attachScreenshot("shouldNotSuccessCreditWithNotValidCardholder");
    }

    @Test
    @Story("Должен не принять оплату с не валидным CVC")
    @DisplayName("Должен не принять оплату с не валидным CVC")
    void shouldNotSuccessPaymentWithNotValidCVC() {
        MainForm mainForm = new MainForm();
        PaymentForm paymentForm = mainForm.goToPayment();
        paymentForm.shouldShowHeadingPayment();
        paymentForm.fillCardNumber(DataHelper.getApprovedCardNumber());
        paymentForm.fillMonth(DataHelper.getValidMonthWithShift(1));
        paymentForm.fillYear(DataHelper.getValidYearWithShift(2));
        paymentForm.fillCardholder(DataHelper.generateValidFullUserName());
        paymentForm.fillCvc("1");
        paymentForm.submit();
        paymentForm.shouldShowErrorSubInputWithInvalidCVC();
        attachScreenshot("shouldNotSuccessPaymentWithNotValidCVC");
    }

    @Test
    @Story("Должен не принять оплату в кредит с не валидным CVC")
    @DisplayName("Должен не принять оплату в кредит с не валидным CVC")
    void shouldNotSuccessCreditWithNotValidCVC() {
        MainForm mainForm = new MainForm();
        PaymentForm creditForm = mainForm.goToCredit();
        creditForm.shouldShowHeadingCredit();
        creditForm.fillCardNumber(DataHelper.getApprovedCardNumber());
        creditForm.fillMonth(DataHelper.getValidMonthWithShift(1));
        creditForm.fillYear(DataHelper.getValidYearWithShift(2));
        creditForm.fillCardholder(DataHelper.generateValidFullUserName());
        creditForm.fillCvc("1");
        creditForm.submit();
        creditForm.shouldShowErrorSubInputWithInvalidCVC();
        attachScreenshot("shouldNotSuccessCreditWithNotValidCVC");
    }

    //!!
    @Test
    @Story("Должен не принять оплату с не заполненной формой")
    @DisplayName("Должен не принять оплату с не заполненной формой")
    void shouldNotSuccessPaymentWithNotCompletedForm() {
        MainForm mainForm = new MainForm();
        PaymentForm paymentForm = mainForm.goToPayment();
        paymentForm.shouldShowHeadingPayment();
        paymentForm.submit();
        paymentForm.shouldShowErrorSubInputWithInvalidCardNumber();
        paymentForm.shouldShowErrorSubInputWithEmptyFieldMonth();
        paymentForm.shouldShowErrorSubInputWithEmptyFieldYear();
        paymentForm.shouldShowErrorSubInputWithEmptyFieldCardholder();
        paymentForm.shouldShowErrorSubInputWithInvalidCVC();
        attachScreenshot("shouldNotSuccessPaymentWithNotCompletedForm");
    }

    @Test
    @Story("Должен не принять оплату в кредит с не заполненной формой")
    @DisplayName("Должен не принять оплату в кредит с не заполненной формой")
    void shouldNotSuccessCreditWithNotCompletedForm() {
        MainForm mainForm = new MainForm();
        PaymentForm creditForm = mainForm.goToCredit();
        creditForm.shouldShowHeadingCredit();
        creditForm.submit();
        creditForm.shouldShowErrorSubInputWithInvalidCardNumber();
        creditForm.shouldShowErrorSubInputWithEmptyFieldMonth();
        creditForm.shouldShowErrorSubInputWithEmptyFieldYear();
        creditForm.shouldShowErrorSubInputWithEmptyFieldCardholder();
        creditForm.shouldShowErrorSubInputWithInvalidCVC();
        attachScreenshot("shouldNotSuccessCreditWithNotCompletedForm");
    }

    @Attachment(value = "{attachName}", type = "image/png")
    byte[] attachScreenshot(String attachName) {
        return ((TakesScreenshot) Selenide.webdriver().object()).getScreenshotAs(OutputType.BYTES);
    }


}
