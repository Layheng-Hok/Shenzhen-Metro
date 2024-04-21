import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class StationImport implements DataImport {
    public static class Station {
        private String englishName;
        private String chineseName;
        private String district;
        private String intro;

        public Station(String englishName, String chineseName, String district, String intro) {
            this.englishName = englishName;
            this.chineseName = chineseName;
            this.district = district;
            this.intro = intro;

        }

        public String getEnglishName() {
            return englishName;
        }

        public void setEnglishName(String englishName) {
            this.englishName = englishName;
        }

        public String getChineseName() {
            return chineseName;
        }

        public void setChineseName(String chineseName) {
            this.chineseName = chineseName;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        @Override
        public String toString() {
            return "Station{" +
                    "englishName='" + englishName + '\'' +
                    ", chineseName='" + chineseName + '\'' +
                    ", district='" + district + '\'' +
                    ", intro='" + intro + '\'' +
                    '}';
        }
    }

    public static class BusInfo {
        private String busLine;
        private String busName;

        public BusInfo(String busLine, String busName) {
            this.busLine = busLine;
            this.busName = busName;
        }

        public String getBusName() {
            return busName;
        }

        public void setBusName(String busName) {
            this.busName = busName;
        }

        public String getBusLine() {
            return busLine;
        }

        public void setBusLine(String busLine) {
            this.busLine = busLine;
        }

        @Override
        public String toString() {
            return "BusInfo{" +
                    "busLine='" + busLine + '\'' +
                    ", busName='" + busName + '\'' +
                    '}';
        }
    }

    @Override
    public void importData() {
        List<Station> stations = new ArrayList<>();
        List<BusInfo> busInfos = new ArrayList<>();
        try {
            String jsonStrings = Files.readString(Path.of("resources/stations.json"));
            JSONObject stationsJson = JSONObject.parseObject(jsonStrings, Feature.OrderedField);
            for (String englishName : stationsJson.keySet()) {
                JSONObject stationJson = stationsJson.getJSONObject(englishName);
                String chineseName = stationJson.getString("chinese_name");
                String district = stationJson.getString("district");
                String intro = stationJson.getString("intro");
                Station station = new Station(englishName, chineseName, district, intro);
                stations.add(station);

                JSONArray busInfoArray = JSONArray.parseArray(stationJson.getString("bus_info"));
                for (Object busInfoObject : busInfoArray) {
                    JSONObject busInfoJson = (JSONObject) busInfoObject;
                    // System.out.println("\tchukou: " + busInfoJson.getString("chukou"));
                    JSONArray busOutInfoArray = busInfoJson.getJSONArray("busOutInfo");
                    for (Object busOutObject : busOutInfoArray) {
                        JSONObject busOutInfo = (JSONObject) busOutObject;
                        String busName = busOutInfo.getString("busName");
                        String[] busLines = busOutInfo.getString("busInfo").split("、");
                        if (busLines.length == 1)
                            busLines = busOutInfo.getString("busInfo").split(",");
                        if (busLines.length == 1)
                            busLines = busOutInfo.getString("busInfo").split("，");
                        if (busLines.length == 1)
                            busLines = busOutInfo.getString("busInfo").split(".");
                        if (busLines.length == 1)
                            busLines = busOutInfo.getString("busInfo").split(" ");
                        for (String busLine : busLines)
                            if (!busLine.isEmpty())
                                busInfos.add(new BusInfo(busLine, busName));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            DatabaseManipulation dm = new DatabaseManipulation();
            dm.openDatasource();
            for (Station station : stations)
                dm.addOneStation(station);
            for (BusInfo busInfo : busInfos)
                dm.addOneBusInfo(busInfo);
            dm.closeDatasource();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
