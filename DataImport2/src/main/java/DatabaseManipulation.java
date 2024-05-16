import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class DatabaseManipulation {
    private Connection con = null;
    private String className = "com.mysql.cj.jdbc.Driver";
    private String host = "localhost";
    private String db = "cs307_project2";
    private String user = "root";
    private String pwd = "";
    private String port = "3306";
    private String url = "jdbc:mysql://" + host + ":" + port + "/" + db;
    private long statementCounts = 0;

    public void openDatasource() {
        try {
            Class.forName(className);
        } catch (Exception e) {
            System.err.println("Cannot find the database driver. Check CLASSPATH.");
            System.exit(1);
        }

        try {
            con = DriverManager.getConnection(url, user, pwd);
        } catch (SQLException e) {
            System.err.println("Database connection failed.");
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
                System.err.println("Cannot find the current database connection.");
                System.exit(1);
            }
        }
    }

    public void addAllStations(List<StationImport.Station> stations) {
        String sql = "INSERT INTO station (english_name, chinese_name, district, intro, status) " +
                "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            for (StationImport.Station station : stations) {
                preparedStatement.setString(1, station.getEnglishName());
                preparedStatement.setString(2, station.getChineseName());
                preparedStatement.setString(3, station.getDistrict());
                preparedStatement.setString(4, station.getIntro());
                preparedStatement.setString(5, station.getStatus());
                preparedStatement.addBatch();
                statementCounts++;
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAllBusInfos(List<StationImport.BusInfo> busInfos) {
        String sql = "INSERT INTO bus_info (bus_line, bus_name) " +
                "VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            for (StationImport.BusInfo busInfo : busInfos) {
                preparedStatement.setString(1, busInfo.getBusLine());
                preparedStatement.setString(2, busInfo.getBusName());
                preparedStatement.addBatch();
                statementCounts++;
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAllBusExitInfos(Util.UniqueOrderedSet<StationImport.BusExitInfo> busExitInfos) {
        String sql = "INSERT INTO bus_exit_info (station_name, exit_gate, bus_info_id) " +
                "VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            for (StationImport.BusExitInfo busExitInfo : busExitInfos) {
                preparedStatement.setString(1, busExitInfo.getStationName());
                preparedStatement.setString(2, busExitInfo.getExit());
                preparedStatement.setLong(3, busExitInfo.getBusInfoId());
                preparedStatement.addBatch();
                statementCounts++;
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAllLandmarkInfos(List<StationImport.LandmarkInfo> landmarkInfos) {
        String sql = "INSERT INTO landmark_info (landmark) " +
                "VALUES (?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            for (StationImport.LandmarkInfo landmarkInfo : landmarkInfos) {
                preparedStatement.setString(1, landmarkInfo.getLandmark());
                preparedStatement.addBatch();
                statementCounts++;
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAllLandmarkExitInfos(Util.UniqueOrderedSet<StationImport.LandmarkExitInfo> landmarkExitInfos) {
        String sql = "INSERT INTO landmark_exit_info (station_name, exit_gate, landmark_id) " +
                "VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            for (StationImport.LandmarkExitInfo landmarkExitInfo : landmarkExitInfos) {
                preparedStatement.setString(1, landmarkExitInfo.getStationName());
                preparedStatement.setString(2, landmarkExitInfo.getExit());
                preparedStatement.setLong(3, landmarkExitInfo.getLandmarkId());
                preparedStatement.addBatch();
                statementCounts++;
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAllLines(List<LineImport.Line> lines) {
        String sql = "INSERT INTO line (line_name, start_time, end_time, intro, mileage, color, first_opening, url) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            for (LineImport.Line line : lines) {
                preparedStatement.setString(1, line.getLineName());
                preparedStatement.setTime(2, line.getStartTime());
                preparedStatement.setTime(3, line.getEndTime());
                preparedStatement.setString(4, line.getIntro());
                preparedStatement.setDouble(5, line.getMileage());
                preparedStatement.setString(6, line.getColor());
                preparedStatement.setDate(7, line.getFirstOpening());
                preparedStatement.setString(8, line.getUrl());
                preparedStatement.addBatch();
                statementCounts++;
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAllLineDetails(List<LineImport.LineDetail> lineDetails) {
        String sql = "INSERT INTO line_detail (line_name, station_name, station_order) " +
                "VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            for (LineImport.LineDetail lineDetail : lineDetails) {
                preparedStatement.setString(1, lineDetail.getLineName());
                preparedStatement.setString(2, lineDetail.getStationName());
                preparedStatement.setInt(3, lineDetail.getStationOrder());
                preparedStatement.addBatch();
                statementCounts++;
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAllCards(List<CardImport.Card> cards) {
        String sql = "INSERT INTO card (code, money, create_time) " +
                "VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            for (CardImport.Card card : cards) {
                preparedStatement.setString(1, card.getCode());
                preparedStatement.setDouble(2, card.getMoney());
                preparedStatement.setTimestamp(3, card.getCreateTime());
                preparedStatement.addBatch();
                statementCounts++;
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAllPassengers(List<PassengerImport.Passenger> passengers) {
        String sql = "INSERT INTO passenger (id_num, name, phone_num, gender, district) " +
                "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            for (PassengerImport.Passenger passenger : passengers) {
                preparedStatement.setString(1, passenger.getIdNumber());
                preparedStatement.setString(2, passenger.getName());
                preparedStatement.setLong(3, passenger.getPhoneNumber());
                preparedStatement.setString(4, Character.toString(passenger.getGender()));
                preparedStatement.setString(5, passenger.getDistrict());
                preparedStatement.addBatch();
                statementCounts++;
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAllRoutePricings(List<PriceImport.RoutePricing> routePricings) {
        String sql = "INSERT INTO route_pricing (start_station, end_station, price) " +
                "VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            for (PriceImport.RoutePricing routePricing : routePricings) {
                preparedStatement.setString(1, routePricing.getStartStation());
                preparedStatement.setString(2, routePricing.getEndStation());
                preparedStatement.setInt(3, routePricing.getPrice());
                preparedStatement.addBatch();
                statementCounts++;
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAllRidesByIdNum(List<RideImport.RideByIdNum> ridesByIdNum) {
        String sql = "INSERT INTO ride_by_id_num (user_num, start_time, end_time, class, pricing_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            for (RideImport.RideByIdNum rideByIdNum : ridesByIdNum) {
                preparedStatement.setString(1, rideByIdNum.getUserNum());
                preparedStatement.setTimestamp(2, rideByIdNum.getStartTime());
                preparedStatement.setTimestamp(3, rideByIdNum.getEndTime());
                preparedStatement.setString(4, rideByIdNum.getRideClass());
                preparedStatement.setInt(5, rideByIdNum.getPricingId());
                preparedStatement.addBatch();
                statementCounts++;
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void addAllRidesByCardNum(List<RideImport.RideByCardNum> ridesByCardNum) {
        String sql = "INSERT INTO ride_by_card_num (user_num, start_time, end_time, class, pricing_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            for (RideImport.RideByCardNum rideByCardNum : ridesByCardNum) {
                preparedStatement.setString(1, rideByCardNum.getUserNum());
                preparedStatement.setTimestamp(2, rideByCardNum.getStartTime());
                preparedStatement.setTimestamp(3, rideByCardNum.getEndTime());
                preparedStatement.setString(4, rideByCardNum.getRideClass());
                preparedStatement.setInt(5, rideByCardNum.getPricingId());
                preparedStatement.addBatch();
                statementCounts++;
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void addAllRides(List<RideImport.Ride> rides) {
        String sql = "INSERT INTO ride (user_num, auth_type, start_time, end_time, start_station, end_station, class, price) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            for (RideImport.Ride ride : rides) {
                preparedStatement.setString(1, ride.getUser());
                preparedStatement.setString(2, ride.getAuthType());
                preparedStatement.setTimestamp(3, ride.getStartTime());
                preparedStatement.setTimestamp(4, ride.getEndTime());
                preparedStatement.setString(5, ride.getStartStation());
                preparedStatement.setString(6, ride.getEndStation());
                preparedStatement.setString(7, ride.getRideClass());
                preparedStatement.setInt(8, ride.getPrice());
                preparedStatement.addBatch();
                statementCounts++;
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, String> getStationNames() {
        HashMap<String, String> chineseEnglishNameMap = new HashMap<>();
        ResultSet resultSet;
        String sql = "select * from station";
        try {
            Statement statement = con.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                chineseEnglishNameMap.put(resultSet.getString("chinese_name"), resultSet.getString("english_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chineseEnglishNameMap;
    }

    public HashMap<String, Integer> getRoutePricingMap() {
        HashMap<String, Integer> routePricingMap = new HashMap<>();
        ResultSet resultSet;
        String sql = "select * from route_pricing";
        try {
            Statement statement = con.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                routePricingMap.putIfAbsent(resultSet.getString("start_station") + " -> " + resultSet.getString("end_station"), resultSet.getInt("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return routePricingMap;
    }

    public HashMap<String, Integer> getRoutePricingIdMap() {
        HashMap<String, Integer> routePricingIdMap = new HashMap<>();
        ResultSet resultSet;
        String sql = "select * from route_pricing";
        try {
            Statement statement = con.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                routePricingIdMap.putIfAbsent(resultSet.getString("start_station") + " -> " + resultSet.getString("end_station"), resultSet.getInt("pricing_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return routePricingIdMap;
    }

    public long getStatementCounts() {
        return statementCounts;
    }
}
