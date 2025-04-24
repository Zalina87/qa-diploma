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

    public static String getYear(int shift) {
        LocalDate year = LocalDate.now().plusYears(shift);
        return year.format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getMonth(int shift) {
        LocalDate year = LocalDate.now().plusMonths(shift);
        return year.format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getUserName() {
        return faker.name().fullName();
    }

    public static String getCvc() {
        return faker.number().digits(3);
    }
    public static CardInfo getApprovedCard() {
        return new CardInfo(
                "4444 4444 4444 4441",
                getMonth(3),
                getYear(1),
                getUserName(),
                getCvc()
        );
    }

    public static CardInfo getDeclinedCard() {
        return new CardInfo(
                "4444 4444 4444 4442",
                getMonth(3),
                getYear(1),
                getUserName(),
                getCvc()
        );
    }
}
