public class Main {
    public static void main(String[] args) {
        StationImport stationImport = new StationImport();
        stationImport.importData();
        LineImport lineImport = new LineImport();
        lineImport.importData();
        CardImport cardImport = new CardImport();
        cardImport.importData();
        PassengerImport passengerImport = new PassengerImport();
        passengerImport.importData();
        RideImport rideImport = new RideImport();
        rideImport.importData();
    }
}
