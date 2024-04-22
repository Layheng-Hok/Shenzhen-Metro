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

    public static class BusExitInfo {
        private String stationName;
        private String exit;
        private long busInfoId;

        public BusExitInfo(String stationName, String exit, long busInfoId) {
            this.stationName = stationName;
            this.exit = exit;
            this.busInfoId = busInfoId;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getExit() {
            return exit;
        }

        public void setExit(String exit) {
            this.exit = exit;
        }

        public long getBusInfoId() {
            return busInfoId;
        }

        public void setBusInfoId(long busInfoId) {
            this.busInfoId = busInfoId;
        }

        @Override
        public String toString() {
            return "BusExitInfo{" +
                    "stationName='" + stationName + '\'' +
                    ", exit='" + exit + '\'' +
                    ", busInfoId=" + busInfoId +
                    '}';
        }
    }

    public static class LandmarkInfo {
        private String landmark;

        public LandmarkInfo(String landmark) {
            this.landmark = landmark;
        }

        public String getLandmark() {
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        @Override
        public String toString() {
            return "LandmarkInfo{" +
                    "landmark='" + landmark + '\'' +
                    '}';
        }
    }

    public static class LandmarkExitInfo {
        private String stationName;
        private String exit;
        private long landmarkId;

        public LandmarkExitInfo(String stationName, String exit, long landmarkId) {
            this.stationName = stationName;
            this.exit = exit;
            this.landmarkId = landmarkId;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getExit() {
            return exit;
        }

        public void setExit(String exit) {
            this.exit = exit;
        }

        public long getLandmarkId() {
            return landmarkId;
        }

        public void setLandmarkId(long landmarkId) {
            this.landmarkId = landmarkId;
        }

        @Override
        public String toString() {
            return "LandmarkExitInfo{" +
                    "stationName='" + stationName + '\'' +
                    ", exit='" + exit + '\'' +
                    ", landmarkId=" + landmarkId +
                    '}';
        }
    }

    @Override
    public void importData() {
        List<Station> stations = new ArrayList<>();
        List<BusInfo> busInfos = new ArrayList<>();
        List<BusExitInfo> busExitInfos = new ArrayList<>();
        List<LandmarkInfo> landmarkInfos = new ArrayList<>();
        List<LandmarkExitInfo> landmarkExitInfos = new ArrayList<>();

        try {
            String jsonStrings = Files.readString(Path.of("resources/stations.json"));
            JSONObject stationsJson = JSONObject.parseObject(jsonStrings, Feature.OrderedField);
            long busInfoId = 0;
            long landmarkId = 0;

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
                    String exit = busInfoJson.getString("chukou");
                    JSONArray busOutInfoArray = busInfoJson.getJSONArray("busOutInfo");
                    for (Object busOutObject : busOutInfoArray) {
                        JSONObject busOutInfo = (JSONObject) busOutObject;
                        String busName = busOutInfo.getString("busName");
                        String busLinesInStr = busOutInfo.getString("busInfo");
                        String[] busLines = busLinesInStr.split("、");
                        if (busLines.length == 1)
                            busLines = busLinesInStr.split(",");
                        if (busLines.length == 1)
                            busLines = busLinesInStr.split("，");
                        if (busLines.length == 1)
                            busLines = busLinesInStr.split(".");
                        if (busLines.length == 1)
                            busLines = busLinesInStr.split(" ");
                        if (busLines.length == 1)
                            busLines = busLinesInStr.split(" ");
                        for (String busLine : busLines) {
                            if (!busLine.isEmpty()) {
                                busInfos.add(new BusInfo(busLine, busName));
                                busExitInfos.add(new BusExitInfo(englishName, exit, ++busInfoId));
                            }
                        }
                    }
                }

                JSONArray landmarkInfoArray = JSONArray.parseArray(stationJson.getString("out_info"));
                for (Object landmarkInfoObject : landmarkInfoArray) {
                    JSONObject landmarkInfoJson = (JSONObject) landmarkInfoObject;
                    String exit = landmarkInfoJson.getString("outt");
                    String landmarksInStr = landmarkInfoJson.getString("textt");
                    String[] landmarks = landmarksInStr.split("、");
                    if (landmarks.length == 1)
                        landmarks = landmarksInStr.split(",");
                    if (landmarks.length == 1)
                        landmarks = landmarksInStr.split("，");
                    if (landmarks.length == 1)
                        landmarks = landmarksInStr.split(".");
                    if (landmarks.length == 1)
                        landmarks = landmarksInStr.split(" ");
                    if (landmarks.length == 1)
                        landmarks = landmarksInStr.split(" ");
                    for (String landmark : landmarks) {
                        if (!landmark.isEmpty()) {
                            landmarkInfos.add(new LandmarkInfo(landmark));
                            landmarkExitInfos.add(new LandmarkExitInfo(englishName, exit, ++landmarkId));
                        }
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
            for (BusExitInfo busExitInfo : busExitInfos)
                dm.addOneBusExitInfo(busExitInfo);
            for (LandmarkInfo landmarkInfo : landmarkInfos)
                dm.addOneLandmarkInfo(landmarkInfo);
            for (LandmarkExitInfo landmarkExitInfo : landmarkExitInfos)
                dm.addOneLandmarkExitInfo(landmarkExitInfo);
            dm.closeDatasource();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
