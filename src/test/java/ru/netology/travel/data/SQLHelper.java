package ru.netology.travel.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();
    public static final String TABLE_PAYMENT = "payment_entity";
    public static final String TABLE_CREDIT = "credit_request_entity";

    private SQLHelper() {
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "user", "user");
    }

    @SneakyThrows
    public static int getPaymentRowsCount(String tableName) {
        try (Connection connection = getConnection()) {
            String sql = "SELECT COUNT(*) FROM " + tableName;
            Long count = runner.query(connection, sql, new ScalarHandler<>());
            return count.intValue();
        }
    }

    @SneakyThrows
    public static String getLatestPaymentStatus(String tableName) {
        try (Connection connection = getConnection()) {
            String sql = "SELECT status FROM " + tableName + " ORDER BY created DESC LIMIT 1";
            String status = runner.query(connection, sql, new ScalarHandler<>());
            return status;
        }
    }
}
