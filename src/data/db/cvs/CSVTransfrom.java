package data.db.cvs;

public interface CSVTransfrom<T> {
    T transformFromCSV(String[] data);
    String transformToCSV();

}