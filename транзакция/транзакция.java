import java.sql.*;

public class транзакция {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/jdbc";

    static final String USER = "postgres";
    static final String PASS = "1234";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Starting transaction...");
            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            String sql1 = "INSERT INTO employees (id, name, surname, age) VALUES (1, 'Ali', 'Kirici', 32)";
            stmt.executeUpdate(sql1);

            String sql2 = "INSERT INTO employees (id, name, surname, age) VALUES (2, 'Ahmet', 'Kirici', 19)";
            stmt.executeUpdate(sql2);

            System.out.println("Committing transaction...");
            conn.commit();
            System.out.println("Transaction committed.");
        } catch (SQLException se) {
            se.printStackTrace();
            try {
                System.out.println("Transaction is being rolled back...");
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
