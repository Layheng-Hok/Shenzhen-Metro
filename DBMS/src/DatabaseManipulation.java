
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

    public void addOneBusInfo(StationImport.BusInfo busInfo) {
        String sql = "INSERT INTO bus_info (bus_line, bus_name) " +
                "VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, busInfo.getBusLine());
            preparedStatement.setString(2, busInfo.getBusName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addOneBusExitInfo(StationImport.BusExitInfo busExitInfo) {
        String sql = "INSERT INTO bus_exit_info (station_name, exit, bus_info_id) " +
                "VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, busExitInfo.getStationName());
            preparedStatement.setString(2, busExitInfo.getExit());
            preparedStatement.setLong(3, busExitInfo.getBusInfoId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOneLandmarkInfo(StationImport.LandmarkInfo landmarkInfo) {
        String sql = "INSERT INTO landmark_info (landmark) " +
                "VALUES (?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, landmarkInfo.getLandmark());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOneLandmarkExitInfo(StationImport.LandmarkExitInfo landmarkExitInfo) {
        String sql = "INSERT INTO landmark_exit_info (station_name, exit, landmark_id) " +
                "VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, landmarkExitInfo.getStationName());
            preparedStatement.setString(2, landmarkExitInfo.getExit());
            preparedStatement.setLong(3, landmarkExitInfo.getLandmarkId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOneLine(LineImport.Line line) {
        String sql = "INSERT INTO line (line_name, start_time, end_time, intro, mileage, color, first_opening, url) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, line.getLineName());
            preparedStatement.setTime(2, line.getStartTime());
            preparedStatement.setTime(3, line.getEndTime());
            preparedStatement.setString(4, line.getIntro());
            preparedStatement.setDouble(5, line.getMileage());
            preparedStatement.setString(6, line.getColor());
            preparedStatement.setDate(7, line.getFirstOpening());
            preparedStatement.setString(8, line.getUrl());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOneLineDetail(LineImport.LineDetail lineDetail) {
        String sql = "INSERT INTO line_detail (line_name, station_name, station_order) " +
                "VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, lineDetail.getLineName());
            preparedStatement.setString(2, lineDetail.getStationName());
            preparedStatement.setInt(3, lineDetail.getStationOrder());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOneCard(CardImport.Card card) {
        String sql = "INSERT INTO card (code, money, create_time) " +
                "VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, card.getCode());
            preparedStatement.setDouble(2, card.getMoney());
            preparedStatement.setTimestamp(3, card.getCreateTime());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOnePassenger(PassengerImport.Passenger passenger) {
        String sql = "INSERT INTO passenger (id_num, name, phone_num, gender, district) " +
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

    public void addOneRoutePricing(RideImport.RoutePricing routePricing) {
        String sql = "INSERT INTO route_pricing (start_station, end_station, price) " +
                "VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, routePricing.getStartStation());
            preparedStatement.setString(2, routePricing.getEndStation());
            preparedStatement.setInt(3, routePricing.getPrice());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOneRideByIdNum(RideImport.RideByIdNum rideByIdNum) {
        String sql = "INSERT INTO ride_by_id_num (user_num, start_time, end_time, pricing_id) " +
                "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, rideByIdNum.getUserNum());
            preparedStatement.setTimestamp(2, rideByIdNum.getStartTime());
            preparedStatement.setTimestamp(3, rideByIdNum.getEndTime());
            preparedStatement.setInt(4, rideByIdNum.getPricingId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void addOneRideByCardNum(RideImport.RideByCardNum rideByCardNum) {
        String sql = "INSERT INTO ride_by_card_num (user_num, start_time, end_time, pricing_id) " +
                "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, rideByCardNum.getUserNum());
            preparedStatement.setTimestamp(2, rideByCardNum.getStartTime());
            preparedStatement.setTimestamp(3, rideByCardNum.getEndTime());
            preparedStatement.setInt(4, rideByCardNum.getPricingId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}
