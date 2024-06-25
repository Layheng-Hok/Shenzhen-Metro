public interface DataImport {
    void readData(int volume);
    void writeData(int method, DatabaseManipulation dm);
}
