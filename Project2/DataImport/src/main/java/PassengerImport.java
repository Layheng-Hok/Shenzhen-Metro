import java.nio.file.Path;

import java.util.List;

public class PassengerImport implements DataImport {
    private static List<Passenger> passengers;

    public static class Passenger {
        private String idNumber;
        private String name;
        private long phoneNumber;
        private char gender;
        private String district;

        public Passenger(String name, String idNumber, long phoneNumber, char gender, String district) {
            this.idNumber = idNumber;
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.gender = gender;
            this.district = district;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(long phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public char getGender() {
            return gender;
        }

        public void setGender(char gender) {
            this.gender = gender;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        @Override
        public String toString() {
            return "Passenger{" +
                    "idNumber='" + idNumber + '\'' +
                    ", name='" + name + '\'' +
                    ", phoneNumber=" + phoneNumber +
                    ", gender=" + gender +
                    ", district='" + district + '\'' +
                    '}';
        }
    }

    @Override
    public void readData(DatabaseManipulation dm) {
        passengers = Util.readJsonArray(Path.of("src/main/resources/passenger.json"), Passenger.class);
    }

    @Override
    public void writeData(DatabaseManipulation dm) {
        try {
            dm.addAllPassengers(passengers);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
