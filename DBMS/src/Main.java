import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        byte method = new Scanner(System.in).nextByte();
        StationImport stationImport = new StationImport();
        stationImport.writeData(method);
        LineImport lineImport = new LineImport();
        lineImport.writeData(method);
        CardImport cardImport = new CardImport();
        cardImport.writeData(method);
        PassengerImport passengerImport = new PassengerImport();
        passengerImport.writeData(method);
        RideImport rideImport = new RideImport();
        rideImport.writeData(method);
    }
}
