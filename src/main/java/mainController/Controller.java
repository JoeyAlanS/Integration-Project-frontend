package mainController;

import dto.CategoryDTO;
import dto.LineupDTO;
import dto.ModelDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import services.LineupService;
import services.CategoryService;
import services.ModelService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    protected ComboBox<LineupDTO> comboBox;

    @FXML
    protected TreeView<LineupDTO> treeView;

    @FXML
    protected TitledPane titledLineup;

    @FXML
    protected TitledPane titledModels;

    @FXML
    protected Accordion accordion;

    LineupService lineupService = new LineupService();
    CategoryService categoryService = new CategoryService();
    ModelService modelsService = new ModelService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accordion.setExpandedPane(titledLineup);
        titledModels.setDisable(true);
        comboBoxSelect();
    }

    protected void comboBoxSelect() {
        List<LineupDTO> lineList = lineupService.getAllLineup();
        comboBox.setItems(FXCollections.observableArrayList(lineList));
        comboBox.valueProperty().addListener(((observable, oldValue, newValue) -> openTreeView(newValue)));
    }

    protected void openTreeView(LineupDTO selectedLine) {
        if (selectedLine == null) {
            treeView.setRoot(null);
            return;
        }

        titledLineup.setExpanded(false);
        titledModels.setDisable(false);
        titledModels.setExpanded(true);

        List<CategoryDTO> categoryList = categoryService.getAllCategories(selectedLine);
        TreeItem showTreeView = new TreeItem<>(selectedLine);
        showTreeView.setExpanded(true);

        categoryList.forEach((category) -> {
            TreeItem<CategoryDTO> categoryItem = new TreeItem<>(category);
            showTreeView.getChildren().add(categoryItem);

            List<ModelDTO> modelsList = modelsService.getAllModels(category);

            modelsList.forEach((model) -> categoryItem.getChildren().add(new TreeItem(model)));
        });
        treeView.setRoot(showTreeView);
    }
}