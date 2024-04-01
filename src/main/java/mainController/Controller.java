package mainController;

import DAO.CategoryDAO;
import DAO.LineupDAO;
import DAO.ModelDAO;
import config.HibernateConfig;
import org.hibernate.Session;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.LineupEntity;
import model.CategoryEntity;
import model.ModelEntity;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ComboBox<LineupEntity> comboBox;

    @FXML
    private TreeView<LineupEntity> treeView;

    @FXML
    private TitledPane titledLineup;

    @FXML
    private TitledPane titledModels;

    @FXML
    private Accordion accordion;

    private final Session session = HibernateConfig.buildSessionFactory().openSession();
    private final LineupDAO lineupDAO = new LineupDAO(session);
    private final CategoryDAO categoryDAO = new CategoryDAO(session);
    private final ModelDAO modelDAO = new ModelDAO(session);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accordion.setExpandedPane(titledLineup);
        titledModels.setDisable(true);
        comboBoxSelect();
    }

    private void comboBoxSelect() {
        List<LineupEntity> lineList = lineupDAO.getAllLineups();
        comboBox.setItems(FXCollections.observableArrayList(lineList));
        comboBox.valueProperty().addListener(((observable, oldValue, newValue) -> openTreeView(newValue)));
    }

    private void openTreeView(LineupEntity selectedLine) {
        titledLineup.setExpanded(false);
        titledModels.setDisable(false);
        titledModels.setExpanded(true);

        List<CategoryEntity> categoryList = categoryDAO.getCategoriesForLine(selectedLine);
        TreeItem showTreeView = new TreeItem<>(selectedLine);
        showTreeView.setExpanded(true);

        categoryList.forEach((category) -> {
            TreeItem<CategoryEntity> categoryItem = new TreeItem<>(category);
            showTreeView.getChildren().add(categoryItem);

            List<ModelEntity> ModelEntityList = modelDAO.getModelsForCategory(category);

            ModelEntityList.forEach((model) -> categoryItem.getChildren().add(new TreeItem(model)));
        });
        treeView.setRoot(showTreeView);
    }
}