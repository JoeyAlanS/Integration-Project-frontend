package dto;

public class LineupDTO {
    private short id;

    private String lineName;

    public LineupDTO(String lineName, Short id) {
        this.lineName = lineName;
        this.id = id;
    }

    public LineupDTO(LineupDTO lineName) {
        this.lineName = String.valueOf(lineName);
    }

    public String toString() {
        return lineName;
    }

    public void setLineName(LineupDTO selectedLine) {
        this.lineName = String.valueOf(selectedLine);
    }

    public String getLineName() {
        return this.lineName;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }
}