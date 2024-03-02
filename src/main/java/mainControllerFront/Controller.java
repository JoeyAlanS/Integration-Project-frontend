package mainControllerFront;

import model.Linesup;
import model.Models;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;
import java.util.Map;

public class Controlle{

    @FXML
    private TitledPane titledPaneModels;

    @FXML
    private TreeView<String> treeView;

    @FXML
    private ComboBox<String> comboBoxLines;

    @FXML
    private Accordion accordion;

    private final Linesup linesup = new Linesup();
    private final Models models = new Models();

    public void initialize() {
        titledPaneModels.setDisable(true);

        comboBoxLines.setItems(linesup.initializeLines());
        comboBoxLines.setOnAction(event -> handleLineSelection());
    }

    private void handleLineSelection() {
        String selectedLine = comboBoxLines.getValue();
        if (selectedLine != null) {
            titledPaneModels.setDisable(false);
            displayModels(selectedLine);
        }
    }

    private void displayModels(String selectedLine) {
        Map<String, List<String>> subcategories = models.initializeModels().get(selectedLine);
        if (subcategories != null) {
            TreeItem<String> rootItem = new TreeItem<>(selectedLine);
            for (String subcategory : subcategories.keySet()) {
                TreeItem<String> subcategoryItem = new TreeItem<>(subcategory);
                List<String> models = subcategories.get(subcategory);
                for (String model : models) {
                    subcategoryItem.getChildren().add(new TreeItem<>(model));
                }
                rootItem.getChildren().add(subcategoryItem);
            }
            treeView.setRoot(rootItem);
            expandModels();
        }
    }

    private void expandModels() {
        titledPaneModels.setExpanded(true);
        accordion.setExpandedPane(titledPaneModels);
    }
}
