public class Main {
    public static void main(String[] args) {
        CardImport cardImport = new CardImport();
        cardImport.importData();
        PassengerImport passengerImport = new PassengerImport();
        passengerImport.importData();
        StationImport stationImport = new StationImport();
        stationImport.importData();
        RideImport rideImport = new RideImport();
        rideImport.importData();
    }
}
