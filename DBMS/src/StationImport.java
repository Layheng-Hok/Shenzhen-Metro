import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.xml.sax.ext.DeclHandler;

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
    }

    @Override
    public void importData() {
        List<Station> stations = new ArrayList<>();
        try {
            String jsonStrings = Files.readString(Path.of("resources/stations.json"));
            JSONObject jsonObject = JSONObject.parseObject(jsonStrings, Feature.OrderedField);
            for (String englishName : jsonObject.keySet()) {
                JSONObject stationJson = jsonObject.getJSONObject(englishName);
                String chineseName = stationJson.getString("chinese_name");
                String district = stationJson.getString("district");
                String intro = stationJson.getString("intro");
                Station station = new Station(englishName, chineseName, district, intro);
                stations.add(station);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            DatabaseManipulation dm = new DatabaseManipulation();
            dm.openDatasource();
            for (Station station : stations)
                 dm.addOneStation(station);
                dm.closeDatasource();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
