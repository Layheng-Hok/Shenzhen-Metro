import java.util.Scanner;

public class ImportScript {
    public static void main(String[] args) {
        System.out.print("Choose your import method (an int input):\n" +
                "[1] import one by one\n" +
                "[2] import by batch\n" +
                "[3] generate a sql script\n" +
                "-> ");

        byte method = new Scanner(System.in).nextByte();

        StationImport stationImport = new StationImport();
        LineImport lineImport = new LineImport();
        CardImport cardImport = new CardImport();
        PassengerImport passengerImport = new PassengerImport();
        RideImport rideImport = new RideImport();

        stationImport.readData();
        lineImport.readData();
        cardImport.readData();
        passengerImport.readData();
        rideImport.readData();

        stationImport.writeData(method);
        lineImport.writeData(method);
        cardImport.writeData(method);
        passengerImport.writeData(method);
        rideImport.writeData(method);
    }
}
