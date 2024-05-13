import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class StationImport implements DataImport {
    private static List<Station> stations = new ArrayList<>();
    private static List<BusInfo> busInfos = new ArrayList<>();
    private static HashMap<String, Long> busInfosMap = new HashMap<>();
    private static Util.UniqueOrderedSet<BusExitInfo> busExitInfos = new Util.UniqueOrderedSet<>();
    private static List<LandmarkInfo> landmarkInfos = new ArrayList<>();
    private static HashMap<String, Long> landmarkInfosMap = new HashMap<>();
    private static Util.UniqueOrderedSet<LandmarkExitInfo> landmarkExitInfos = new Util.UniqueOrderedSet<>();

    public static class Station {
        private String englishName;
        private String chineseName;
        private String district;
        private String intro;
        private String status;

        public Station(String englishName, String chineseName, String district, String intro) {
            this.englishName = englishName;
            this.chineseName = chineseName;
            this.district = district;
            this.intro = intro;
            this.status = "Operational";
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "Station{" +
                    "englishName='" + englishName + '\'' +
                    ", chineseName='" + chineseName + '\'' +
                    ", district='" + district + '\'' +
                    ", intro='" + intro + '\'' +
                    ", status='" + status + '\'' +
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BusExitInfo that = (BusExitInfo) o;
            return busInfoId == that.busInfoId && Objects.equals(stationName, that.stationName) && Objects.equals(exit, that.exit);
        }

        @Override
        public int hashCode() {
            return Objects.hash(stationName, exit, busInfoId);
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LandmarkExitInfo that = (LandmarkExitInfo) o;
            return landmarkId == that.landmarkId && Objects.equals(stationName, that.stationName) && Objects.equals(exit, that.exit);
        }

        @Override
        public int hashCode() {
            return Objects.hash(stationName, exit, landmarkId);
        }
    }

    @Override
    public void readData(DatabaseManipulation dm) {
        try {
            String jsonStrings = Files.readString(Path.of("src/main/resources/stations.json"));
            JSONObject stationsJson = JSONObject.parseObject(jsonStrings, Feature.OrderedField);
            long busInfoId = 0;
            long landmarkId = 0;

            int i = 0;
            for (String englishName : stationsJson.keySet()) {
                i++;
                JSONObject stationJson = stationsJson.getJSONObject(englishName);
                String chineseName = stationJson.getString("chinese_name");
                String district = stationJson.getString("district");
                String intro = stationJson.getString("intro");
                Station station = new Station(englishName, chineseName, district, intro);
                if (i == 1 || i == 10 || i == 100 || i == 200 || i == 300)
                    station.setStatus("Under construction");
                if (i == 5 || i == 15 || i == 50 || i == 150 || i == 305)
                    station.setStatus("Closed");
                stations.add(station);

                JSONArray busInfoArray = JSONArray.parseArray(stationJson.getString("bus_info"));
                for (Object busInfoObject : busInfoArray) {
                    JSONObject busInfoJson = (JSONObject) busInfoObject;
                    String exitsInStr = busInfoJson.getString("chukou");
                    String[] exits = exitsInStr.split("/");
                    if (exits.length == 1)
                        exits = exitsInStr.split("、");
                    if (exits.length == 1)
                        exits = exitsInStr.split(",");
                    if (exits.length == 1)
                        exits = exitsInStr.split("，");
                    if (exits.length == 1)
                        exits = exitsInStr.split(";");
                    if (exits.length == 1)
                        exits = exitsInStr.split("；");
                    if (exits.length == 1)
                        exits = exitsInStr.split(" ");
                    if (exits.length == 1)
                        exits = new String[]{exitsInStr};
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
                            busLines = busLinesInStr.split(";");
                        if (busLines.length == 1)
                            busLines = busLinesInStr.split("；");
                        if (busLines.length == 1)
                            busLines = busLinesInStr.split(" ");
                        if (busLines.length == 1)
                            busLines = busLinesInStr.split(" ");
                        if (busLines.length == 1)
                            busLines = new String[]{busLinesInStr};
                        for (String exit : exits) {
                            if (!exit.isEmpty() && !Util.containOnlySeparators(exit)) {
                                for (String busLine : busLines) {
                                    String busNameAndLine = busName + " " + busLine;
                                    if (!busLine.isEmpty() && !Util.containOnlySeparators(exit)) {
                                        if (!busInfosMap.containsKey(busNameAndLine)) {
                                            busInfosMap.put(busNameAndLine, ++busInfoId);
                                            busInfos.add(new BusInfo(busLine, busName));
                                        }
                                        busExitInfos.add(new BusExitInfo(englishName, exit, busInfosMap.get(busNameAndLine)));
                                    }
                                }
                            }
                        }
                    }
                }

                JSONArray landmarkInfoArray = JSONArray.parseArray(stationJson.getString("out_info"));
                for (Object landmarkInfoObject : landmarkInfoArray) {
                    JSONObject landmarkInfoJson = (JSONObject) landmarkInfoObject;
                    String exitsInStr = landmarkInfoJson.getString("outt");
                    String[] exits = exitsInStr.split("/");
                    if (exits.length == 1)
                        exits = exitsInStr.split("、");
                    if (exits.length == 1)
                        exits = exitsInStr.split(",");
                    if (exits.length == 1)
                        exits = exitsInStr.split("，");
                    if (exits.length == 1)
                        exits = exitsInStr.split(";");
                    if (exits.length == 1)
                        exits = exitsInStr.split("；");
                    if (exits.length == 1)
                        exits = exitsInStr.split(" ");
                    if (exits.length == 1)
                        exits = new String[]{exitsInStr};
                    String landmarksInStr = landmarkInfoJson.getString("textt");
                    String[] landmarks = landmarksInStr.split("、");
                    if (landmarks.length == 1)
                        landmarks = landmarksInStr.split(",");
                    if (landmarks.length == 1)
                        landmarks = landmarksInStr.split("，");
                    if (landmarks.length == 1)
                        landmarks = landmarksInStr.split(";");
                    if (landmarks.length == 1)
                        landmarks = landmarksInStr.split("；");
                    if (landmarks.length == 1)
                        landmarks = landmarksInStr.split(" ");
                    if (landmarks.length == 1)
                        landmarks = landmarksInStr.split(" ");
                    if (landmarks.length == 1)
                        landmarks = new String[]{landmarksInStr};
                    for (String exit : exits) {
                        if (!exit.isEmpty() && !Util.containOnlySeparators(exit)) {
                            for (String landmark : landmarks) {
                                if (!landmark.isEmpty() && !Util.containOnlySeparators(landmark)) {
                                    if (!landmarkInfosMap.containsKey(landmark)) {
                                        landmarkInfosMap.put(landmark, ++landmarkId);
                                        landmarkInfos.add(new LandmarkInfo(landmark));
                                    }
                                    landmarkExitInfos.add(new LandmarkExitInfo(englishName, exit, landmarkInfosMap.get(landmark)));
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeData(DatabaseManipulation dm) {
        try {
            dm.addAllStations(stations);
            dm.addAllBusInfos(busInfos);
            dm.addAllBusExitInfos(busExitInfos);
            dm.addAllLandmarkInfos(landmarkInfos);
            dm.addAllLandmarkExitInfos(landmarkExitInfos);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
