package APITests;

public class CSVWriterObject {
    private int id;
    private String description;

    private long result;
    private double result2;

    public CSVWriterObject(int id, String description, long result, double result2) {
        this.id = id;
        this.description = description;
        this.result = result;
        this.result2 = result2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getResult() {
        return result;
    }

    public void setResult(long result) {
        this.result = result;
    }

    public double getResult2() {
        return result2;
    }

    public void setResult2(double result2) {
        this.result2 = result2;
    }
}
