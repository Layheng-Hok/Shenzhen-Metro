
import java.sql.*;

public class DatabaseManipulation {
    private Connection con = null;
    private ResultSet resultSet;

    private String host = "localhost";
    private String dbname = "cs307_project1";
    private String user = "layhenghok";
    private String pwd = "";
    private String port = "5432";

    public void openDatasource() {
        try {
            Class.forName("org.postgresql.Driver");

        } catch (Exception e) {
            System.err.println("Cannot find the PostgreSQL driver. Check CLASSPATH.");
            System.exit(1);
        }

        try {
            String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
            con = DriverManager.getConnection(url, user, pwd);

        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public void closeDatasource() {
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addOneCard(CardImport.Card card) {
        String sql = "INSERT INTO card (code, money, create_time) " +
                "VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, card.getCode());
            preparedStatement.setDouble(2, card.getMoney());
            preparedStatement.setTimestamp(3, card.getCreateTime());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOnePassenger(PassengerImport.Passenger passenger) {
        String sql = "INSERT INTO passenger (id_number, name, phone_number, gender, district) " +
                "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, passenger.getIdNumber());
            preparedStatement.setString(2, passenger.getName());
            preparedStatement.setLong(3, passenger.getPhoneNumber());
            preparedStatement.setString(4, Character.toString(passenger.getGender()));
            preparedStatement.setString(5, passenger.getDistrict());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOneStation(StationImport.Station station) {
        String sql = "INSERT INTO station (english_name, chinese_name, district, intro) " +
                "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, station.getEnglishName());
            preparedStatement.setString(2, station.getChineseName());
            preparedStatement.setString(3, station.getDistrict());
            preparedStatement.setString(4, station.getIntro());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
