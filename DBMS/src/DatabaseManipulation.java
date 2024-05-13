import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class DatabaseManipulation {
    private Connection con = null;
    private String className = null;
    private String host = null;
    private String db = null;
    private String user = null;
    private String pwd = null;
    private String port = null;
    private String url = null;
    private int database;
    private long statementCounts = 0;

    public DatabaseManipulation(int database) {
        this.database = database;
        if (database == 1) {
            className = "org.postgresql.Driver";
            host = "localhost";
            db = "cs307_project1";
            user = "layhenghok";
            pwd = "";
            port = "5432";
            url = "jdbc:postgresql://" + host + ":" + port + "/" + db;
        } else if (database == 2) {
            className = "com.mysql.cj.jdbc.Driver";
            host = "localhost";
            db = "cs307_project2";
            user = "root";
            pwd = "";
            port = "3306";
            url = "jdbc:mysql://" + host + ":" + port + "/" + db;
        }
    }

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
            statementCounts++;
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
            statementCounts++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addOneBusExitInfo(StationImport.BusExitInfo busExitInfo) {
        String sql = "INSERT INTO bus_exit_info (station_name, exit_gate, bus_info_id) " +
                "VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, busExitInfo.getStationName());
            preparedStatement.setString(2, busExitInfo.getExit());
            preparedStatement.setLong(3, busExitInfo.getBusInfoId());
            preparedStatement.executeUpdate();
            statementCounts++;
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
            statementCounts++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOneLandmarkExitInfo(StationImport.LandmarkExitInfo landmarkExitInfo) {
        String sql = "INSERT INTO landmark_exit_info (station_name, exit_gate, landmark_id) " +
                "VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, landmarkExitInfo.getStationName());
            preparedStatement.setString(2, landmarkExitInfo.getExit());
            preparedStatement.setLong(3, landmarkExitInfo.getLandmarkId());
            preparedStatement.executeUpdate();
            statementCounts++;
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
            statementCounts++;
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
            statementCounts++;
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
            statementCounts++;
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
            statementCounts++;
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
            statementCounts++;
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
            statementCounts++;
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
            statementCounts++;
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void addAllStations(List<StationImport.Station> stations) {
        String sql = "INSERT INTO station (english_name, chinese_name, district, intro) " +
                "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            for (StationImport.Station station : stations) {
                preparedStatement.setString(1, station.getEnglishName());
                preparedStatement.setString(2, station.getChineseName());
                preparedStatement.setString(3, station.getDistrict());
                preparedStatement.setString(4, station.getIntro());
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

    public void addAllRoutePricings(List<RideImport.RoutePricing> routePricings) {
        String sql = "INSERT INTO route_pricing (start_station, end_station, price) " +
                "VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            for (RideImport.RoutePricing routePricing : routePricings) {
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
        String sql = "INSERT INTO ride_by_id_num (user_num, start_time, end_time, pricing_id) " +
                "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            for (RideImport.RideByIdNum rideByIdNum : ridesByIdNum) {
                preparedStatement.setString(1, rideByIdNum.getUserNum());
                preparedStatement.setTimestamp(2, rideByIdNum.getStartTime());
                preparedStatement.setTimestamp(3, rideByIdNum.getEndTime());
                preparedStatement.setInt(4, rideByIdNum.getPricingId());
                preparedStatement.addBatch();
                statementCounts++;
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void addAllRidesByCardNum(List<RideImport.RideByCardNum> ridesByCardNum) {
        String sql = "INSERT INTO ride_by_card_num (user_num, start_time, end_time, pricing_id) " +
                "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            for (RideImport.RideByCardNum rideByCardNum : ridesByCardNum) {
                preparedStatement.setString(1, rideByCardNum.getUserNum());
                preparedStatement.setTimestamp(2, rideByCardNum.getStartTime());
                preparedStatement.setTimestamp(3, rideByCardNum.getEndTime());
                preparedStatement.setInt(4, rideByCardNum.getPricingId());
                preparedStatement.addBatch();
                statementCounts++;
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void generateStationSqlScript(List<StationImport.Station> stations) {
        String fileName = null;
        if (database == 1)
            fileName = "sql/pgsql_import_script.sql";
        else if (database == 2) {
            fileName = "sql/mysql_import_script.sql";
        }

        try {
            assert fileName != null;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
                String sql = "INSERT INTO station (english_name, chinese_name, district, intro) " +
                        "VALUES (?, ?, ?, ?)";
                StringBuilder sb = new StringBuilder("BEGIN;\n-- station\n");
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                for (StationImport.Station station : stations) {
                    preparedStatement.setString(1, station.getEnglishName());
                    preparedStatement.setString(2, station.getChineseName());
                    preparedStatement.setString(3, station.getDistrict());
                    preparedStatement.setString(4, station.getIntro());
                    StringBuilder statement = new StringBuilder(preparedStatement.toString());
                    if (database == 2)
                        statement.replace(0, 43, "");
                    sb.append(statement).append(";\n");
                    statementCounts++;
                }
                bw.write(sb.append("\n").toString());
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateBusInfoSqlScript(List<StationImport.BusInfo> busInfos) {
        String fileName = null;
        if (database == 1)
            fileName = "sql/pgsql_import_script.sql";
        else if (database == 2) {
            fileName = "sql/mysql_import_script.sql";
        }

        try {
            assert fileName != null;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
                String sql = "INSERT INTO bus_info (bus_line, bus_name) " +
                        "VALUES (?, ?)";
                StringBuilder sb = new StringBuilder("-- bus_info\n");
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                for (StationImport.BusInfo busInfo : busInfos) {
                    preparedStatement.setString(1, busInfo.getBusLine());
                    preparedStatement.setString(2, busInfo.getBusName());
                    StringBuilder statement = new StringBuilder(preparedStatement.toString());
                    if (database == 2)
                        statement.replace(0, 43, "");
                    sb.append(statement).append(";\n");
                    statementCounts++;
                }
                bw.write(sb.append("\n").toString());
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateBusExitInfoSqlScript(Util.UniqueOrderedSet<StationImport.BusExitInfo> busExitInfos) {
        String fileName = null;
        if (database == 1)
            fileName = "sql/pgsql_import_script.sql";
        else if (database == 2) {
            fileName = "sql/mysql_import_script.sql";
        }

        try {
            assert fileName != null;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
                String sql = "INSERT INTO bus_exit_info (station_name, exit_gate, bus_info_id) " +
                        "VALUES (?, ?, ?)";
                StringBuilder sb = new StringBuilder("-- bus_exit_info\n");
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                for (StationImport.BusExitInfo busExitInfo : busExitInfos) {
                    preparedStatement.setString(1, busExitInfo.getStationName());
                    preparedStatement.setString(2, busExitInfo.getExit());
                    preparedStatement.setLong(3, busExitInfo.getBusInfoId());
                    StringBuilder statement = new StringBuilder(preparedStatement.toString());
                    if (database == 2)
                        statement.replace(0, 43, "");
                    sb.append(statement).append(";\n");
                    statementCounts++;
                }
                bw.write(sb.append("\n").toString());
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateLandmarkInfoSqlScript(List<StationImport.LandmarkInfo> landmarkInfos) {
        String fileName = null;
        if (database == 1)
            fileName = "sql/pgsql_import_script.sql";
        else if (database == 2) {
            fileName = "sql/mysql_import_script.sql";
        }

        try {
            assert fileName != null;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
                String sql = "INSERT INTO landmark_info (landmark) " +
                        "VALUES (?)";
                StringBuilder sb = new StringBuilder("-- landmark_info\n");
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                for (StationImport.LandmarkInfo landmarkInfo : landmarkInfos) {
                    preparedStatement.setString(1, landmarkInfo.getLandmark());
                    StringBuilder statement = new StringBuilder(preparedStatement.toString());
                    if (database == 2)
                        statement.replace(0, 43, "");
                    sb.append(statement).append(";\n");
                    statementCounts++;
                }
                bw.write(sb.append("\n").toString());
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateLandmarkExitInfoSqlScript(Util.UniqueOrderedSet<StationImport.LandmarkExitInfo> landmarkExitInfos) {
        String fileName = null;
        if (database == 1)
            fileName = "sql/pgsql_import_script.sql";
        else if (database == 2) {
            fileName = "sql/mysql_import_script.sql";
        }

        try {
            assert fileName != null;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
                String sql = "INSERT INTO landmark_exit_info (station_name, exit_gate, landmark_id) " +
                        "VALUES (?, ?, ?)";
                StringBuilder sb = new StringBuilder("-- landmark_info\n");
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                for (StationImport.LandmarkExitInfo landmarkExitInfo : landmarkExitInfos) {
                    preparedStatement.setString(1, landmarkExitInfo.getStationName());
                    preparedStatement.setString(2, landmarkExitInfo.getExit());
                    preparedStatement.setLong(3, landmarkExitInfo.getLandmarkId());
                    StringBuilder statement = new StringBuilder(preparedStatement.toString());
                    if (database == 2)
                        statement.replace(0, 43, "");
                    sb.append(statement).append(";\n");
                    statementCounts++;
                }
                bw.write(sb.append("\n").toString());
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateLineSqlScript(List<LineImport.Line> lines) {
        String fileName = null;
        if (database == 1)
            fileName = "sql/pgsql_import_script.sql";
        else if (database == 2) {
            fileName = "sql/mysql_import_script.sql";
        }

        try {
            assert fileName != null;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
                String sql = "INSERT INTO line (line_name, start_time, end_time, intro, mileage, color, first_opening, url) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                StringBuilder sb = new StringBuilder("-- line\n");
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
                    StringBuilder statement = new StringBuilder(preparedStatement.toString());
                    if (database == 2)
                        statement.replace(0, 43, "");
                    sb.append(statement).append(";\n");
                    statementCounts++;
                }
                bw.write(sb.append("\n").toString());
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void generateLineDetailSqlScript(List<LineImport.LineDetail> lineDetails) {
        String fileName = null;
        if (database == 1)
            fileName = "sql/pgsql_import_script.sql";
        else if (database == 2) {
            fileName = "sql/mysql_import_script.sql";
        }

        try {
            assert fileName != null;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
                String sql = "INSERT INTO line_detail (line_name, station_name, station_order) " +
                        "VALUES (?, ?, ?)";
                StringBuilder sb = new StringBuilder("-- line_detail\n");
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                for (LineImport.LineDetail lineDetail : lineDetails) {
                    preparedStatement.setString(1, lineDetail.getLineName());
                    preparedStatement.setString(2, lineDetail.getStationName());
                    preparedStatement.setInt(3, lineDetail.getStationOrder());
                    StringBuilder statement = new StringBuilder(preparedStatement.toString());
                    if (database == 2)
                        statement.replace(0, 43, "");
                    sb.append(statement).append(";\n");
                    statementCounts++;
                }
                bw.write(sb.append("\n").toString());
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateCardSqlScript(List<CardImport.Card> cards) {
        String fileName = null;
        if (database == 1)
            fileName = "sql/pgsql_import_script.sql";
        else if (database == 2) {
            fileName = "sql/mysql_import_script.sql";
        }

        try {
            assert fileName != null;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
                String sql = "INSERT INTO card (code, money, create_time) " +
                        "VALUES (?, ?, ?)";
                StringBuilder sb = new StringBuilder("-- card\n");
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                for (CardImport.Card card : cards) {
                    preparedStatement.setString(1, card.getCode());
                    preparedStatement.setDouble(2, card.getMoney());
                    preparedStatement.setTimestamp(3, card.getCreateTime());
                    StringBuilder statement = new StringBuilder(preparedStatement.toString());
                    if (database == 2)
                        statement.replace(0, 43, "");
                    sb.append(statement).append(";\n");
                    statementCounts++;
                }
                bw.write(sb.append("\n").toString());
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void generatePassengerSqlScript(List<PassengerImport.Passenger> passengers) {
        String fileName = null;
        if (database == 1)
            fileName = "sql/pgsql_import_script.sql";
        else if (database == 2) {
            fileName = "sql/mysql_import_script.sql";
        }

        try {
            assert fileName != null;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
                String sql = "INSERT INTO passenger (id_num, name, phone_num, gender, district) " +
                        "VALUES (?, ?, ?, ?, ?)";
                StringBuilder sb = new StringBuilder("-- passenger\n");
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                for (PassengerImport.Passenger passenger : passengers) {
                    preparedStatement.setString(1, passenger.getIdNumber());
                    preparedStatement.setString(2, passenger.getName());
                    preparedStatement.setLong(3, passenger.getPhoneNumber());
                    preparedStatement.setString(4, Character.toString(passenger.getGender()));
                    preparedStatement.setString(5, passenger.getDistrict());
                    StringBuilder statement = new StringBuilder(preparedStatement.toString());
                    if (database == 2)
                        statement.replace(0, 43, "");
                    sb.append(statement).append(";\n");
                    statementCounts++;
                }
                bw.write(sb.append("\n").toString());
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateRoutePricingSqlScript(List<RideImport.RoutePricing> routePricings) {
        String fileName = null;
        if (database == 1)
            fileName = "sql/pgsql_import_script.sql";
        else if (database == 2) {
            fileName = "sql/mysql_import_script.sql";
        }

        try {
            assert fileName != null;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
                String sql = "INSERT INTO route_pricing (start_station, end_station, price) " +
                        "VALUES (?, ?, ?)";
                StringBuilder sb = new StringBuilder("-- route_pricing\n");
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                for (RideImport.RoutePricing routePricing : routePricings) {
                    preparedStatement.setString(1, routePricing.getStartStation());
                    preparedStatement.setString(2, routePricing.getEndStation());
                    preparedStatement.setInt(3, routePricing.getPrice());
                    StringBuilder statement = new StringBuilder(preparedStatement.toString());
                    if (database == 2)
                        statement.replace(0, 43, "");
                    sb.append(statement).append(";\n");
                    statementCounts++;
                }
                bw.write(sb.append("\n").toString());
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateRideByIdNumSqlScript(List<RideImport.RideByIdNum> ridesByIdNum) {
        String fileName = null;
        if (database == 1)
            fileName = "sql/pgsql_import_script.sql";
        else if (database == 2) {
            fileName = "sql/mysql_import_script.sql";
        }

        try {
            assert fileName != null;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
                String sql = "INSERT INTO ride_by_id_num (user_num, start_time, end_time, pricing_id) " +
                        "VALUES (?, ?, ?, ?)";
                StringBuilder sb = new StringBuilder("-- ride_by_id_num\n");
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                for (RideImport.RideByIdNum rideByIdNum : ridesByIdNum) {
                    preparedStatement.setString(1, rideByIdNum.getUserNum());
                    preparedStatement.setTimestamp(2, rideByIdNum.getStartTime());
                    preparedStatement.setTimestamp(3, rideByIdNum.getEndTime());
                    preparedStatement.setInt(4, rideByIdNum.getPricingId());
                    StringBuilder statement = new StringBuilder(preparedStatement.toString());
                    if (database == 2)
                        statement.replace(0, 43, "");
                    sb.append(statement).append(";\n");
                    statementCounts++;
                }
                bw.write(sb.append("\n").toString());
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateRideByCardNumSqlScript(List<RideImport.RideByCardNum> ridesByCardNum) {
        String fileName = null;
        if (database == 1)
            fileName = "sql/pgsql_import_script.sql";
        else if (database == 2) {
            fileName = "sql/mysql_import_script.sql";
        }

        try {
            assert fileName != null;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
                String sql = "INSERT INTO ride_by_card_num (user_num, start_time, end_time, pricing_id) " +
                        "VALUES (?, ?, ?, ?)";
                StringBuilder sb = new StringBuilder("-- ride_by_card_num\n");
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                for (RideImport.RideByCardNum rideByCardNum : ridesByCardNum) {
                    preparedStatement.setString(1, rideByCardNum.getUserNum());
                    preparedStatement.setTimestamp(2, rideByCardNum.getStartTime());
                    preparedStatement.setTimestamp(3, rideByCardNum.getEndTime());
                    preparedStatement.setInt(4, rideByCardNum.getPricingId());
                    StringBuilder statement = new StringBuilder(preparedStatement.toString());
                    if (database == 2)
                        statement.replace(0, 43, "");
                    sb.append(statement).append(";\n");
                    statementCounts++;
                }
                if (database == 1)
                    bw.write(sb.append("END;\n").toString());
                else if (database == 2)
                    bw.write(sb.append("COMMIT;\n").toString());
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long getStatementCounts() {
        return statementCounts;
    }
}
