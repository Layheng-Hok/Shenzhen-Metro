import java.nio.file.Path;

import java.sql.Timestamp;
import java.util.List;

public class CardImport implements DataImport {
    public static class Card {
        private String code;
        private double money;
        private Timestamp createTime;

        public Card(String code, double money, Timestamp createTime) {
            this.code = code;
            this.money = money;
            this.createTime = createTime;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public Timestamp getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Timestamp createTime) {
            this.createTime = createTime;
        }

        @Override
        public String toString() {
            return "Card{" +
                    "code=" + code +
                    ", money=" + money +
                    ", createTime=" + createTime +
                    '}';
        }
    }

    @Override
    public void importData() {
        List<Card> cards = Util.readJsonArray(Path.of("resources/cards.json"), Card.class);
        try {
            DatabaseManipulation dm = new DatabaseManipulation();
            dm.openDatasource();
            for (Card card : cards)
                dm.addOneCard(card);
            dm.closeDatasource();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
