import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LineImport implements DataImport {
    private static List<Line> lines = new ArrayList<>();
    private static List<LineDetail> lineDetails = new ArrayList<>();

    public static class Line {
        private String lineName;
        private Time startTime;
        private Time endTime;
        private String intro;
        private double mileage;
        private String color;
        private Date firstOpening;
        private String url;

        public Line(String lineName, Time startTime, Time endTime, String intro, double mileage, String color, Date firstOpening, String url) {
            this.lineName = lineName;
            this.startTime = startTime;
            this.endTime = endTime;
            this.intro = intro;
            this.mileage = mileage;
            this.color = color;
            this.firstOpening = firstOpening;
            this.url = url;
        }

        public String getLineName() {
            return lineName;
        }

        public void setLineName(String lineName) {
            this.lineName = lineName;
        }

        public Time getStartTime() {
            return startTime;
        }

        public void setStartTime(Time startTime) {
            this.startTime = startTime;
        }

        public Time getEndTime() {
            return endTime;
        }

        public void setEndTime(Time endTime) {
            this.endTime = endTime;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public double getMileage() {
            return mileage;
        }

        public void setMileage(double mileage) {
            this.mileage = mileage;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public Date getFirstOpening() {
            return firstOpening;
        }

        public void setFirstOpening(Date firstOpening) {
            this.firstOpening = firstOpening;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "Line{" +
                    "lineName='" + lineName + '\'' +
                    ", startTime='" + startTime + '\'' +
                    ", endTime='" + endTime + '\'' +
                    ", intro='" + intro + '\'' +
                    ", mileage=" + mileage +
                    ", color='" + color + '\'' +
                    ", firstOpening='" + firstOpening + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    public static class LineDetail {
        private String lineName;
        private String stationName;
        private int stationOrder;


        public LineDetail(String lineName, String stationName, int stationOrder) {
            this.lineName = lineName;
            this.stationName = stationName;
            this.stationOrder = stationOrder;
        }

        public String getLineName() {
            return lineName;
        }

        public void setLineName(String lineName) {
            this.lineName = lineName;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public int getStationOrder() {
            return stationOrder;
        }

        public void setStationOrder(int stationOrder) {
            this.stationOrder = stationOrder;
        }

        @Override
        public String toString() {
            return "LineDetail{" +
                    "lineName='" + lineName + '\'' +
                    ", stationName='" + stationName + '\'' +
                    ", stationOrder=" + stationOrder +
                    '}';
        }
    }

    @Override
    public void readData(int volume) {
        try {
            String jsonStrings = Files.readString(Path.of("resources/lines.json"));
            JSONObject linesJson = JSONObject.parseObject(jsonStrings, Feature.OrderedField);
            for (String lineName : linesJson.keySet()) {
                JSONObject lineJson = linesJson.getJSONObject(lineName);
                JSONArray stationsArray = JSONArray.parseArray(lineJson.getString("stations"));
                String intro = lineJson.getString("intro");
                double mileage = lineJson.getDouble("mileage");
                String color = lineJson.getString("color");
                Date firstOpening = (Date) lineJson.getSqlDate("first_opening");
                String url = lineJson.getString("url");

                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                Time startTime = null;
                Time endTime = null;
                try {
                    startTime = new Time(timeFormat.parse(lineJson.getString("start_time")).getTime());
                    endTime = new Time(timeFormat.parse(lineJson.getString("end_time")).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                int stationOrder = 0;
                for (Object stationName : stationsArray)
                    lineDetails.add(new LineDetail(lineName, stationName.toString(), ++stationOrder));

                lines.add(new Line(lineName, startTime, endTime, intro, mileage, color, firstOpening, url));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void writeData(int method, DatabaseManipulation dm) {
        try {
            if (method == 1) {
                for (Line line : lines)
                    dm.addOneLine(line);
                for (LineDetail lineDetail : lineDetails)
                    dm.addOneLineDetail(lineDetail);
            } else if (method == 2) {
                dm.addAllLines(lines);
                dm.addAllLineDetails(lineDetails);
            } else if (method == 3) {
                dm.generateLineSqlScript(lines);
                dm.generateLineDetailSqlScript(lineDetails);
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
