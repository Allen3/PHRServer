package utils;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Created by Allen on 2015/7/26.
 */
public class DatabaseUtil {

    private static String driverName = null;
    private static String connUrl = null;
    private static String user = null;
    private static String password = null;
    private static Connection conn = null;
    private static ResultSet resultSet = null;
    private static Statement statement = null;

    // Loading DB configuration properties.
    static {
        InputStream inputStream = null;
        try {
            inputStream = DatabaseUtil.class.getResourceAsStream("..\\config.properties"); //Check directory.
            Properties properties = new Properties();
            properties.load(inputStream);

            driverName = properties.getProperty("driverName");
            connUrl = properties.getProperty("connUrl");
            user = properties.getProperty("user");
            password = properties.getProperty("password");

            System.out.println("start to SQL driver");
            inputStream.close();
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    /**
     * Loading JDBC driver dynamically and connect the database.
     *
     * @return
     * @throws ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName(driverName);
        conn = DriverManager.getConnection(connUrl, user, password);
        return conn;
    }

    /**
     * Making SQL query.
     *
     * @param sql
     * @return
     * @throws ClassNotFoundException
     */
    public static ResultSet queryData(String sql) throws ClassNotFoundException {
        statement = null;
        try {
            conn = getConnection();
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }


    /**
     * Making SQL update.
     *
     * @param sql
     * @return
     * @throws ClassNotFoundException
     */
    public static boolean update(String sql) throws ClassNotFoundException {
        int rowCnt4DML = 0;
        try {
            conn = getConnection();
            statement = conn.createStatement();
            rowCnt4DML = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // ????
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    resultSet = null;
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    statement = null;
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    conn = null;
                }
            }

        }
        if (rowCnt4DML > 0) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Shut the connection to database.
     */
    public static void closeConnection() {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                resultSet = null;
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                statement = null;
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                conn = null;
            }
        }
    }
}
