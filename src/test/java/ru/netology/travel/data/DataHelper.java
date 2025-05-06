package ru.netology.travel.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private DataHelper() {
    }

    private static final Faker faker = new Faker(new Locale("ru"));

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CardInfo {
        private String cardNumber;
        private String month;
        private String year;
        private String cardHolder;
        private String cvc;
    }

    public static String getValidYearWithShift(int shift) {
        LocalDate year = LocalDate.now().plusYears(shift);
        return year.format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getValidMonthWithShift(int shift) {
        LocalDate year = LocalDate.now().plusMonths(shift);
        return year.format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String generateValidFullUserName() {
        return faker.name().fullName();
    }

    public static String getRandomCvc() {
        return faker.number().digits(3);
    }

    public static String getApprovedCardNumber() {
        return "4444 4444 4444 4441";
    }

    public static CardInfo getApprovedCard() {
        return new CardInfo(
                getApprovedCardNumber(),
                getValidMonthWithShift(3),
                getValidYearWithShift(1),
                generateValidFullUserName(),
                getRandomCvc()
        );
    }

    public static CardInfo getDeclinedCard() {
        return new CardInfo(
                "4444 4444 4444 4442",
                getValidMonthWithShift(3),
                getValidYearWithShift(1),
                generateValidFullUserName(),
                getRandomCvc()
        );
    }


}
