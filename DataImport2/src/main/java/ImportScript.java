public class ImportScript {
    public static void main(String[] args) {
        DatabaseManipulation dm = new DatabaseManipulation();
        dm.openDatasource();

        long startTime = System.currentTimeMillis();
        StationImport stationImport = new StationImport();
        stationImport.readData(dm);
        stationImport.writeData(dm);

        LineImport lineImport = new LineImport();
        lineImport.readData(dm);
        lineImport.writeData(dm);

        CardImport cardImport = new CardImport();
        cardImport.readData(dm);
        cardImport.writeData(dm);

        PassengerImport passengerImport = new PassengerImport();
        passengerImport.readData(dm);
        passengerImport.writeData(dm);

        PriceImport priceImport = new PriceImport();
        priceImport.readData(dm);
        priceImport.writeData(dm);

        RideImport rideImport = new RideImport();
        rideImport.readData(dm);
        rideImport.writeData(dm);

        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " ms");

        System.out.println("Total statements executed: " + dm.getStatementCounts() + " statements");
        dm.closeDatasource();
    }
}
