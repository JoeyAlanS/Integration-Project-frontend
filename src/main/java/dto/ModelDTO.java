package dto;

public class ModelDTO {
    public String category;
    private short id;
    private String modelName;

    public ModelDTO(String modelName, Short id) {
        this.modelName = modelName;
        this.id = id;
    }

    public String toString() {
        return modelName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}