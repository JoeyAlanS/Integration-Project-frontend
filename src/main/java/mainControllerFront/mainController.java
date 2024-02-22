package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeView;

import java.util.List;

public class MainController {
    private void fetchAndDisplayModels(String selectedLine) {
        List<String> models = backendService.getModelsByLine(selectedLine);
        
    }
}
