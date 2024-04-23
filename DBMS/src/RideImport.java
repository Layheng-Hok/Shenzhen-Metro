import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RideImport implements DataImport {
    public static class Ride {
        private String user;
        private String startStation;
        private String endStation;
        private int price;
        private Timestamp startTime;
        private Timestamp endTime;

        public Ride(String user, String startStation, String endStation, int price, Timestamp startTime, Timestamp endTime) {
            this.user = user;
            this.startStation = startStation;
            this.endStation = endStation;
            this.price = price;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
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

        @Override
        public String toString() {
            return "Ride{" +
                    "user='" + user + '\'' +
                    ", startStation='" + startStation + '\'' +
                    ", endStation='" + endStation + '\'' +
                    ", price=" + price +
                    ", startTime=" + startTime +
                    ", endTime=" + endTime +
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
        private int pricingId;

        public RideByIdNum(String userNum, Timestamp startTime, Timestamp endTime, int pricingId) {
            this.userNum = userNum;
            this.startTime = startTime;
            this.endTime = endTime;
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

        public int getPricingId() {
            return pricingId;
        }

        public void setPricingId(int pricingId) {
            this.pricingId = pricingId;
        }
    }

    public static class RideByCardNum {
        private String userNum;
        private Timestamp startTime;
        private Timestamp endTime;
        private int pricingId;

        public RideByCardNum(String userNum, Timestamp startTime, Timestamp endTime, int pricingId) {
            this.userNum = userNum;
            this.startTime = startTime;
            this.endTime = endTime;
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

        public int getPricingId() {
            return pricingId;
        }

        public void setPricingId(int pricingId) {
            this.pricingId = pricingId;
        }
    }

    @Override
    public void importData(byte method) {
        List<Ride> rides = Util.readJsonArray(Path.of("resources/ride.json"), Ride.class);
        List<RoutePricing> routePricings = new ArrayList<>();
        HashMap<String, Integer> routeIdMap = new HashMap<>();
        List<RideByIdNum> ridesByIdNum = new ArrayList<>();
        List<RideByCardNum> ridesByCardNum = new ArrayList<>();

        int routeId = 0;
        for (Ride ride : rides) {
            RoutePricing routePricing = new RoutePricing(ride.startStation, ride.endStation, ride.price);
            String route = ride.startStation + " -> " + ride.endStation;
            if (!routeIdMap.containsKey(route)) {
                routePricings.add(routePricing);
                routeIdMap.put(route, ++routeId);
            }
            if (String.valueOf(ride.user).length() == 9)
                ridesByCardNum.add(new RideByCardNum(ride.getUser(), ride.getStartTime(), ride.getStartTime(), routeIdMap.get(ride.startStation + " -> " + ride.endStation)));
            else
                ridesByIdNum.add(new RideByIdNum(ride.getUser(), ride.getStartTime(), ride.getStartTime(), routeIdMap.get(ride.startStation + " -> " + ride.endStation)));
        }


        try {
            DatabaseManipulation dm = new DatabaseManipulation();
            dm.openDatasource();
            if (method == 1) {
                for (RoutePricing routePricing : routePricings)
                    dm.addOneRoutePricing(routePricing);
                for (RideByIdNum rideByIdNum : ridesByIdNum)
                    dm.addOneRideByIdNum(rideByIdNum);
                for (RideByCardNum rideByCardNum : ridesByCardNum)
                    dm.addOneRideByCardNum(rideByCardNum);
            } else if (method == 2) {
                dm.addAllRoutePricings(routePricings);
                dm.addAllRidesByIdNum(ridesByIdNum);
                dm.addAllRidesByCardNum(ridesByCardNum);
            }
            dm.closeDatasource();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}