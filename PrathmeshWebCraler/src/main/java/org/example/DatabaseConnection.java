package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public  class DatabaseConnection {

    static Connection connection = null;
    public static Connection getConnection() {

        if (connection != null) {
            return connection;
        }
        String db = "searchenginapp";
        String user = "root";
        String pwd = "prathmesh9977039497";
        return getConnection(db, user, pwd);

    }

    private static Connection getConnection(String db, String user, String pwd) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + db + "?user=" + user + "&password=" + pwd);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return connection;
    }
}
