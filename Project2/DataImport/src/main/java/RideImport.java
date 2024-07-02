import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RideImport implements DataImport {
    private static List<RoutePricing> routePricings = new ArrayList<>();
    private static List<Ride> rides = new ArrayList<>();

    public static class Ride {
        private String user;
        private String authType;
        private String startStation;
        private String endStation;
        private float price;
        private Timestamp startTime;
        private Timestamp endTime;
        private long duration;
        private String rideClass;

        public Ride(String user, String startStation, String endStation, float price, Timestamp startTime, Timestamp endTime) {
            this.user = user;
            this.authType = user.length() == 9 ? "Travel card" : "National ID";
            this.startStation = startStation;
            this.endStation = endStation;
            this.price = price;
            this.startTime = startTime;
            this.endTime = endTime;
            this.duration = Duration.between(startTime.toInstant(), endTime.toInstant()).getSeconds();
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

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
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

        public long getDuration() {
            return duration;
        }

        public void setDuration(long duration) {
            this.duration = duration;
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
                    ", endTime=" + endTime +
                    ", duration=" + duration +
                    ", rideClass='" + rideClass + '\'' +
                    '}';
        }
    }

    public static class RoutePricing {
        private String startStation;
        private String endStation;
        private float price;

        public RoutePricing(String startStation, String endStation, float price) {
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

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
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

    @Override
    public void readData(DatabaseManipulation dm) {
        rides = Util.readJsonArray(Path.of("src/main/resources/ride.json"), Ride.class);
    }

    @Override
    public void writeData(DatabaseManipulation dm) {
        try {
            dm.addAllRides(rides);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}