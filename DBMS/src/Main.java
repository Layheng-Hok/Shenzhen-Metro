import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        byte method = new Scanner(System.in).nextByte();
        StationImport stationImport = new StationImport();
        stationImport.importData(method);
        LineImport lineImport = new LineImport();
        lineImport.importData(method);
        CardImport cardImport = new CardImport();
        cardImport.importData(method);
        PassengerImport passengerImport = new PassengerImport();
        passengerImport.importData(method);
        RideImport rideImport = new RideImport();
        rideImport.importData(method);
    }
}
