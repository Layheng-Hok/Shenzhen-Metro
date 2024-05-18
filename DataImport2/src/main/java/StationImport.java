import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class StationImport implements DataImport {
    private static List<Station> stations = new ArrayList<>();
    private static Util.UniqueOrderedSet<BusExitInfo> busExitInfos = new Util.UniqueOrderedSet<>();
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

    public static class BusExitInfo {
        private String stationName;
        private String exit;
        private String busLine;
        private String busName;

        public BusExitInfo(String stationName, String exit, String busLine, String busName) {
            this.stationName = stationName;
            this.exit = exit;
            this.busLine = busLine;
            this.busName = busName;
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

        public String getBusLine() {
            return busLine;
        }

        public void setBusLine(String busLine) {
            this.busLine = busLine;
        }

        public String getBusName() {
            return busName;
        }

        public void setBusName(String busName) {
            this.busName = busName;
        }

        @Override
        public String toString() {
            return "BusExitInfo{" +
                    "stationName='" + stationName + '\'' +
                    ", exit='" + exit + '\'' +
                    ", busLine='" + busLine + '\'' +
                    ", busName='" + busName + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BusExitInfo that = (BusExitInfo) o;
            return Objects.equals(stationName, that.stationName) && Objects.equals(exit, that.exit) && Objects.equals(busLine, that.busLine) && Objects.equals(busName, that.busName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(stationName, exit, busLine, busName);
        }
    }

    public static class LandmarkExitInfo {
        private String stationName;
        private String exit;
        private String landmark;

        public LandmarkExitInfo(String stationName, String exit, String landmark) {
            this.stationName = stationName;
            this.exit = exit;
            this.landmark = landmark;
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

        public String getLandmark() {
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        @Override
        public String toString() {
            return "LandmarkExitInfo{" +
                    "stationName='" + stationName + '\'' +
                    ", exit='" + exit + '\'' +
                    ", landmark='" + landmark + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LandmarkExitInfo that = (LandmarkExitInfo) o;
            return Objects.equals(stationName, that.stationName) && Objects.equals(exit, that.exit) && Objects.equals(landmark, that.landmark);
        }

        @Override
        public int hashCode() {
            return Objects.hash(stationName, exit, landmark);
        }
    }

    @Override
    public void readData(DatabaseManipulation dm) {
        try {
            String jsonStrings = Files.readString(Path.of("src/main/resources/stations.json"));
            JSONObject stationsJson = JSONObject.parseObject(jsonStrings, Feature.OrderedField);

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
                                    if (!busLine.isEmpty() && !Util.containOnlySeparators(exit))
                                        busExitInfos.add(new BusExitInfo(englishName, exit, busLine, busName));
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
                            for (String landmark : landmarks)
                                if (!landmark.isEmpty() && !Util.containOnlySeparators(landmark))
                                    landmarkExitInfos.add(new LandmarkExitInfo(englishName, exit, landmark));
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
            dm.addAllBusExitInfos(busExitInfos);
            dm.addAllLandmarkExitInfos(landmarkExitInfos);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
