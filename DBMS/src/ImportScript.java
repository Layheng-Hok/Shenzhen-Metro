import java.util.Scanner;

public class ImportScript {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose your database server (an int input):\n" +
                "[1] PostgreSQL\n" +
                "[2] MySQL\n" +
                "-> ");
        int database = scanner.nextInt();
        System.out.println();
        System.out.print("Choose your import method (an int input):\n" +
                "[1] import one by one\n" +
                "[2] import by batch\n" +
                "[3] generate a sql script\n" +
                "-> ");
        int method = scanner.nextInt();
        System.out.println();
        System.out.print("Specify your import volume (an int input, range: 0%-100%, standard: 20, 50, 100):\n" +
                "-> ");
        int volume = scanner.nextInt();

        DatabaseManipulation dm = new DatabaseManipulation(database);
        dm.openDatasource();

        StationImport stationImport = new StationImport();
        LineImport lineImport = new LineImport();
        CardImport cardImport = new CardImport();
        PassengerImport passengerImport = new PassengerImport();
        RideImport rideImport = new RideImport();

        stationImport.readData(100);
        lineImport.readData(100);
        cardImport.readData(100);
        passengerImport.readData(100);
        rideImport.readData(volume);

        stationImport.writeData(method, dm);
        lineImport.writeData(method, dm);
        cardImport.writeData(method, dm);
        passengerImport.writeData(method, dm);
        rideImport.writeData(method, dm);

        dm.closeDatasource();
    }
}
