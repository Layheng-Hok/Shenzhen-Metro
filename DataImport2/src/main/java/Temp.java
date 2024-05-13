public class Temp {
    public static void main(String[] args) {
        DatabaseManipulation dm = new DatabaseManipulation();
        dm.openDatasource();

        StationImport stationImport = new StationImport();
        stationImport.readData(dm);
        stationImport.writeData(dm);

        PriceImport priceImport = new PriceImport();
        priceImport.readData(dm);
        priceImport.writeData(dm);

        dm.closeDatasource();
    }
}
