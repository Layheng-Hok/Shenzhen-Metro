import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PriceImport implements DataImport {
    private static List<RoutePricing> routePricings = new ArrayList<>();
    private static HashMap<String, Float> routePricingMap = new HashMap<>();

    public static class RoutePricing {
        String startStation;
        String endStation;
        float price;

        public RoutePricing(String startStation, String endStation, float price) {
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

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
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

    @Override
    public void readData(DatabaseManipulation dm) {
        HashMap<String, String> chineseEnglishStationNameMap = dm.getStationNames();

        String filePath = "src/main/resources/Price.xlsx";
        try (FileInputStream inputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            List<String[]> leftStations = readStations(sheet, 0, 4, 376);
            List<String[]> topStations = readStationsHorizontal(sheet, 1, 3, 3);

            for (String[] leftStation : leftStations) {
                for (String[] topStation : topStations) {
                    int rowIndex = Integer.parseInt(leftStation[0]);
                    int colIndex = Integer.parseInt(topStation[0]);
                    float price = Float.parseFloat(getPrice(sheet, rowIndex, colIndex));
                    String startStation = chineseEnglishStationNameMap.get(leftStation[3]);
                    String endStation = chineseEnglishStationNameMap.get(topStation[3]);
                    if (leftStation[3].equals("深圳北站"))
                        startStation = "Shenzhen North Station";
                    if (topStation[3].equals("深圳北站"))
                        endStation = "Shenzhen North Station";
                    String route = startStation + " -> " + endStation;

                    if (routePricingMap.containsKey(route)) {
                        float prevPrice = routePricingMap.get(route);
                        if (price != prevPrice)
                            System.out.println("error");
                    } else {
                        routePricingMap.put(route, price);
                        routePricings.add(new RoutePricing(startStation, endStation, price));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeData(DatabaseManipulation dm) {
        try {
            dm.addAllRoutePricings(routePricings);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    private static List<String[]> readStations(Sheet sheet, int column, int startRow, int endRow) {
        List<String[]> stations = new ArrayList<>();
        for (int rowIndex = startRow; rowIndex <= endRow; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                String line = getStringValue(row.getCell(column));
                String num = getStringValue(row.getCell(column + 1));
                String name = getStringValue(row.getCell(column + 2));
                stations.add(new String[]{Integer.toString(rowIndex), line, num, name});
            }
        }
        return stations;
    }

    private static List<String[]> readStationsHorizontal(Sheet sheet, int startRow, int endRow, int startCol) {
        List<String[]> stations = new ArrayList<>();
        if (sheet.getRow(startRow) != null) {
            int maxCols = sheet.getRow(startRow).getLastCellNum();
            for (int colIndex = startCol; colIndex < maxCols; colIndex++) {
                String[] stationInfo = new String[3];
                for (int rowIndex = startRow, i = 0; rowIndex <= endRow; rowIndex++, i++) {
                    Row currentRow = sheet.getRow(rowIndex);
                    if (currentRow != null) {
                        Cell cell = currentRow.getCell(colIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        stationInfo[i] = getStringValue(cell);
                    }
                }
                stations.add(new String[]{Integer.toString(colIndex), stationInfo[0], stationInfo[1], stationInfo[2]});
            }
        }
        return stations;
    }

    private static String getPrice(Sheet sheet, int rowIndex, int colIndex) {
        Row row = sheet.getRow(rowIndex);
        if (row != null) {
            Cell cell = row.getCell(colIndex);
            return getStringValue(cell);
        }
        return "N/A";
    }

    private static String getStringValue(Cell cell) {
        if (cell == null) return "Empty";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.format("%.0f", cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case BLANK:
                return "Empty";
            default:
                return "Unknown Type";
        }
    }
}