import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RideImport implements DataImport {
    private static List<RoutePricing> routePricings = new ArrayList<>();
    private static List<Ride> rides = new ArrayList<>();
    private static List<RideByIdNum> ridesByIdNum = new ArrayList<>();
    private static List<RideByCardNum> ridesByCardNum = new ArrayList<>();


    public static class Ride {
        private String user;
        private String authType;
        private String startStation;
        private String endStation;
        private int price;
        private Timestamp startTime;
        private Timestamp endTime;
        private String rideClass;

        public Ride(String user, String startStation, String endStation, int price, Timestamp startTime, Timestamp endTime) {
            this.user = user;
            this.authType = String.valueOf(user).length() == 9 ? "Travel card" : "National ID";
            this.startStation = startStation;
            this.endStation = endStation;
            this.price = price;
            this.startTime = startTime;
            this.endTime = endTime;
            this.rideClass = "Economy";
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getAuthType() {
            return authType;
        }

        public void setAuthType(String authType) {
            this.authType = authType;
        }

        public String getStartStation() {
            return startStation;
        }

        public void setStartStation(String startStation) {
            this.startStation = startStation;
        }

        public String getEndStation() {
            return endStation;
        }

        public void setEndStation(String endStation) {
            this.endStation = endStation;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public Timestamp getStartTime() {
            return startTime;
        }

        public void setStartTime(Timestamp startTime) {
            this.startTime = startTime;
        }

        public Timestamp getEndTime() {
            return endTime;
        }

        public void setEndTime(Timestamp endTime) {
            this.endTime = endTime;
        }

        public String getRideClass() {
            return rideClass;
        }

        public void setRideClass(String rideClass) {
            this.rideClass = rideClass;
        }

        @Override
        public String toString() {
            return "Ride{" +
                    "user='" + user + '\'' +
                    ", authType='" + authType + '\'' +
                    ", startStation='" + startStation + '\'' +
                    ", endStation='" + endStation + '\'' +
                    ", price=" + price +
                    ", startTime=" + startTime +
                    ", endTime='" + endTime + '\'' +
                    ", rideClass='" + rideClass + '\'' +
                    '}';
        }
    }

    public static class RoutePricing {
        private String startStation;
        private String endStation;
        private int price;

        public RoutePricing(String startStation, String endStation, int price) {
            this.startStation = startStation;
            this.endStation = endStation;
            this.price = price;
        }

        public String getStartStation() {
            return startStation;
        }

        public void setStartStation(String startStation) {
            this.startStation = startStation;
        }

        public String getEndStation() {
            return endStation;
        }

        public void setEndStation(String endStation) {
            this.endStation = endStation;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "RoutePricing{" +
                    "startStation='" + startStation + '\'' +
                    ", endStation='" + endStation + '\'' +
                    ", price=" + price +
                    '}';
        }
    }

    public static class RideByIdNum {
        private String userNum;
        private Timestamp startTime;
        private Timestamp endTime;
        private String rideClass;
        private int pricingId;

        public RideByIdNum(String userNum, Timestamp startTime, Timestamp endTime, int pricingId) {
            this.userNum = userNum;
            this.startTime = startTime;
            this.endTime = endTime;
            this.rideClass = "Economy";
            this.pricingId = pricingId;
        }

        public String getUserNum() {
            return userNum;
        }

        public void setUserNum(String userNum) {
            this.userNum = userNum;
        }

        public Timestamp getStartTime() {
            return startTime;
        }

        public void setStartTime(Timestamp startTime) {
            this.startTime = startTime;
        }

        public Timestamp getEndTime() {
            return endTime;
        }

        public void setEndTime(Timestamp endTime) {
            this.endTime = endTime;
        }

        public String getRideClass() {
            return rideClass;
        }

        public void setRideClass(String rideClass) {
            this.rideClass = rideClass;
        }

        public int getPricingId() {
            return pricingId;
        }

        public void setPricingId(int pricingId) {
            this.pricingId = pricingId;
        }

        @Override
        public String toString() {
            return "RideByIdNum{" +
                    "userNum='" + userNum + '\'' +
                    ", startTime=" + startTime +
                    ", endTime='" + endTime + '\'' +
                    ", rideClass='" + rideClass + '\'' +
                    ", pricingId=" + pricingId +
                    '}';
        }
    }

    public static class RideByCardNum {
        private String userNum;
        private Timestamp startTime;
        private Timestamp endTime;
        private String rideClass;
        private int pricingId;

        public RideByCardNum(String userNum, Timestamp startTime, Timestamp endTime, int pricingId) {
            this.userNum = userNum;
            this.startTime = startTime;
            this.endTime = endTime;
            this.rideClass = "Economy";
            this.pricingId = pricingId;
        }

        public String getUserNum() {
            return userNum;
        }

        public void setUserNum(String userNum) {
            this.userNum = userNum;
        }

        public Timestamp getStartTime() {
            return startTime;
        }

        public void setStartTime(Timestamp startTime) {
            this.startTime = startTime;
        }

        public Timestamp getEndTime() {
            return endTime;
        }

        public void setEndTime(Timestamp endTime) {
            this.endTime = endTime;
        }

        public String getRideClass() {
            return rideClass;
        }

        public void setRideClass(String rideClass) {
            this.rideClass = rideClass;
        }

        public int getPricingId() {
            return pricingId;
        }

        public void setPricingId(int pricingId) {
            this.pricingId = pricingId;
        }

        @Override
        public String toString() {
            return "RideByCardNum{" +
                    "userNum='" + userNum + '\'' +
                    ", startTime=" + startTime +
                    ", endTime='" + endTime + '\'' +
                    ", rideClass='" + rideClass + '\'' +
                    ", pricingId=" + pricingId +
                    '}';
        }
    }

    @Override
    public void readData(DatabaseManipulation dm) {
        rides = Util.readJsonArray(Path.of("src/main/resources/ride.json"), Ride.class);
        HashMap<String, Integer> routePricingIdMap = dm.getRoutePricingIdMap();

        for (Ride ride : rides) {
            if (String.valueOf(ride.user).length() == 9)
                ridesByCardNum.add(new RideByCardNum(ride.getUser(), ride.getStartTime(), ride.getEndTime(), routePricingIdMap.get(ride.startStation + " -> " + ride.endStation)));
            else
                ridesByIdNum.add(new RideByIdNum(ride.getUser(), ride.getStartTime(), ride.getEndTime(), routePricingIdMap.get(ride.startStation + " -> " + ride.endStation)));
        }

    }

    @Override
    public void writeData(DatabaseManipulation dm) {
        try {
            dm.addAllRides(rides);
            dm.addAllRidesByIdNum(ridesByIdNum);
            dm.addAllRidesByCardNum(ridesByCardNum);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}