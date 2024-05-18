public class ImportScript {
    public static void main(String[] args) {
        DatabaseManipulation dm = new DatabaseManipulation();
        dm.openDatasource();

        long startTime = System.currentTimeMillis();
        System.out.println("Importing stations...");
        StationImport stationImport = new StationImport();
        stationImport.readData(dm);
        stationImport.writeData(dm);

        System.out.println("Importing lines...");
        LineImport lineImport = new LineImport();
        lineImport.readData(dm);
        lineImport.writeData(dm);

        System.out.println("Importing cards...");
        CardImport cardImport = new CardImport();
        cardImport.readData(dm);
        cardImport.writeData(dm);

        System.out.println("Importing passengers...");
        PassengerImport passengerImport = new PassengerImport();
        passengerImport.readData(dm);
        passengerImport.writeData(dm);

        System.out.println("Importing prices...");
        PriceImport priceImport = new PriceImport();
        priceImport.readData(dm);
        priceImport.writeData(dm);

        System.out.println("Importing rides...");
        RideImport rideImport = new RideImport();
        rideImport.readData(dm);
        rideImport.writeData(dm);

        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " ms");

        System.out.println("Total statements executed: " + dm.getStatementCounts() + " statements");
        dm.closeDatasource();
    }
}
