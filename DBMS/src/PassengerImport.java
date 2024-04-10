import java.nio.file.Path;

import java.sql.Timestamp;
import java.util.List;

public class PassengerImport implements DataImport {
    public static class Card {
        String idNum;
        String name;
        long phoneNum;
        char gender;
        String district;


    }

    @Override
    public void importData() {

    }
}
