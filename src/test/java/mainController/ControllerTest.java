package mainController;

import dto.CategoryDTO;
import dto.LineupDTO;
import dto.ModelDTO;
import services.CategoryService;
import services.LineupService;
import services.ModelService;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ControllerTest extends ApplicationTest {

    private Controller mainController;

    @Before
    public void setUp() {
        mainController = Mockito.spy(Controller.class);
        mainController.titledLineup = new TitledPane();
        mainController.comboBox = new ComboBox<>();
        mainController.titledModels = new TitledPane();
        mainController.treeView = new TreeView<>();
        mainController.accordion = new Accordion();
        mainController.lineupService = Mockito.mock(LineupService.class);
        mainController.categoryService = Mockito.mock(CategoryService.class);
        mainController.modelsService = Mockito.mock(ModelService.class);
    }

    @Test
    public void initializeControllerTest() {
        // Given
        Mockito.doNothing().when(mainController).comboBoxSelect();
        mainController.titledLineup.setDisable(true);

        // When
        mainController.initialize(null, null);

        // Then
        assertEquals(mainController.titledLineup, mainController.accordion.getExpandedPane());
        assertTrue(mainController.titledModels.isDisable());
        verify(mainController).comboBoxSelect();
    }

    @Test
    public void comboBoxSelectTest01() {
        // Given
        List<LineupDTO> mockList = new ArrayList<>();
        LineupDTO mockLine = new LineupDTO("Ares", (short) 1);
        Mockito.when(mainController.lineupService.getAllLineup()).thenReturn(mockList);
        mainController.comboBoxSelect();

        // When
        mainController.openTreeView(mockLine);

        // Then
        assertEquals(FXCollections.observableArrayList(mockList), mainController.comboBox.getItems());
        verify(mainController.lineupService).getAllLineup();
    }

    @Test
    public void comboBoxSelectTest02() {
        // Given
        List<LineupDTO> mockList = new ArrayList<>();
        mockList.add(new LineupDTO("Ares", (short) 1));
        mockList.add(new LineupDTO("Cronos", (short) 2));
        Mockito.when(mainController.lineupService.getAllLineup()).thenReturn(mockList);
        mainController.comboBoxSelect();
        mainController.comboBox.setValue(mainController.comboBox.getItems().get(0));

        // When
        mainController.openTreeView(mockList.get(0));

        // Then
        verify(mainController, times(2)).openTreeView(mainController.comboBox.getItems().get(0));
    }

    @Test
    public void comboBoxSelectTest03() {
        // Given
        List<LineupDTO> mockList = new ArrayList<>();
        mockList.add(new LineupDTO("Ares", (short) 1));
        mockList.add(new LineupDTO("Cronos", (short) 2));
        Mockito.when(mainController.lineupService.getAllLineup()).thenReturn(mockList);
        mainController.comboBoxSelect();
        mainController.comboBox.setValue(mainController.comboBox.getItems().get(1));

        // When
        mainController.openTreeView(mockList.get(0));

        // Then
        verify(mainController).openTreeView(mainController.comboBox.getItems().get(1));
    }

    @Test
    public void comboBoxSelectTest04() {
        // Given
        List<LineupDTO> mockList = new ArrayList<>();
        mockList.add(new LineupDTO("Ares", (short) 1));
        Mockito.when(mainController.lineupService.getAllLineup()).thenReturn(mockList);
        mainController.comboBoxSelect();

        // When
        mainController.openTreeView(mockList.get(0));

        // Then
        assertFalse(mainController.comboBox.getItems().isEmpty());
    }

    @Test
    public void openTreeViewTest01() {
        // Given
        LineupDTO mockLine = new LineupDTO("Ares", (short) 1);
        CategoryDTO mockCategory = new CategoryDTO("Ares TB", (short) 1);
        ModelDTO mockModel = new ModelDTO("Ares 7021", (short) 1);
        List<CategoryDTO> mockCategoryList = new ArrayList<>();
        mockCategoryList.add(mockCategory);
        List<ModelDTO> mockModelList = new ArrayList<>();
        TreeItem<ModelDTO> mockTreeModel = new TreeItem<>(mockModel);
        TreeItem mockTreeCategory = new TreeItem<>(mockCategory);
        TreeItem mockTreeView = new TreeItem<>(mockLine);
        mockTreeView.getChildren().add(mockTreeCategory);
        mockTreeCategory.getChildren().add(mockTreeModel);
        mainController.openTreeView(mockLine);

        // When
        mainController.titledLineup.setExpanded(false);
        mainController.titledModels.setDisable(false);
        mainController.titledModels.setExpanded(true);
        when(mainController.categoryService.getAllCategories(mockLine)).thenReturn(mockCategoryList);
        when(mainController.modelsService.getAllModels(mockCategory)).thenReturn(mockModelList);
        mockTreeView.setExpanded(true);


        // Then
        assertFalse(mainController.titledLineup.isExpanded());
        assertFalse(mainController.titledModels.isDisable());
        assertTrue(mainController.titledModels.isExpanded());
        assertTrue(mainController.treeView.isVisible());
        assertTrue(mockTreeView.isExpanded());
        assertEquals(mainController.treeView.getTreeItem(0).getValue(), mockLine);
        assertEquals(mockTreeView.getChildren().toString(), "[TreeItem [ value: " + mockCategory + " ]]");
        assertEquals(mockTreeCategory.getChildren().toString(), "[TreeItem [ value: " + mockModel + " ]]");
        assertEquals(mainController.treeView.getExpandedItemCount(), 1);
    }

    @Test
    public void openTreeViewTest02() {
        // Given
        LineupDTO mockLine = new LineupDTO("Cronos", (short) 2);
        CategoryDTO mockCategory = new CategoryDTO("Cronos Old", (short) 3);
        ModelDTO mockModel = new ModelDTO("Cronos 6001-A", (short) 1);
        List<CategoryDTO> mockCategoryList = new ArrayList<>();
        mockCategoryList.add(mockCategory);
        List<ModelDTO> mockModelList = new ArrayList<>();
        TreeItem mockTreeModel = new TreeItem<>(mockModel);
        TreeItem mockTreeCategory = new TreeItem<>(mockCategory);
        TreeItem mockTreeView = new TreeItem<>(mockLine);
        mockTreeView.getChildren().add(mockTreeCategory);
        mockTreeCategory.getChildren().add(mockTreeModel);
        mainController.openTreeView(mockLine);

        // When
        mainController.titledLineup.setExpanded(true);
        mainController.titledModels.setDisable(true);
        mainController.titledModels.setExpanded(true);
        when(mainController.categoryService.getAllCategories(mockLine)).thenReturn(mockCategoryList);
        when(mainController.modelsService.getAllModels(mockCategory)).thenReturn(mockModelList);
        mockTreeView.setExpanded(true);

        // Then
        assertTrue(mainController.titledLineup.isExpanded());
        assertTrue(mainController.titledModels.isDisable());
        assertTrue(mainController.titledModels.isExpanded());
        assertTrue(mainController.treeView.isVisible());
        assertTrue(mockTreeView.isExpanded());
        assertEquals(mainController.treeView.getTreeItem(0).getValue(), mockLine);
        assertEquals(mockTreeView.getChildren().toString(), "[TreeItem [ value: " + mockCategory + " ]]");
        assertEquals(mockTreeCategory.getChildren().toString(), "[TreeItem [ value: " + mockModel + " ]]");
        assertEquals(mainController.treeView.getExpandedItemCount(), 1);
    }

    @Test
    public void openTreeViewTest03() {
        // Given
        LineupDTO mockLine = new LineupDTO("Cronos", (short) 2);
        CategoryDTO mockCategory = new CategoryDTO("Cronos Old", (short) 3);
        ModelDTO mockModel = new ModelDTO("Cronos 6001-A", (short) 1);
        List<CategoryDTO> mockCategoryList = new ArrayList<>();
        mockCategoryList.add(mockCategory);
        List<ModelDTO> mockModelList = new ArrayList<>();
        TreeItem mockTreeModel = new TreeItem<>(mockModel);
        TreeItem mockTreeCategory = new TreeItem<>(mockCategory);
        TreeItem mockTreeView = new TreeItem<>(mockLine);
        mockTreeView.getChildren().add(mockTreeCategory);
        mockTreeCategory.getChildren().add(mockTreeModel);
        mainController.openTreeView(mockLine);

        // When
        mainController.titledLineup.setExpanded(false);
        mainController.titledModels.setDisable(false);
        mainController.titledModels.setExpanded(true);
        when(mainController.categoryService.getAllCategories(mockLine)).thenReturn(mockCategoryList);
        when(mainController.modelsService.getAllModels(mockCategory)).thenReturn(mockModelList);
        mockTreeView.setExpanded(true);

        // Then
        assertFalse(mainController.titledLineup.isExpanded());
        assertFalse(mainController.titledModels.isDisable());
        assertTrue(mainController.titledModels.isExpanded());
        assertTrue(mainController.treeView.isVisible());
        assertTrue(mockTreeView.isExpanded());
        assertEquals(mainController.treeView.getTreeItem(0).getValue(), mockLine);
        assertEquals(mockTreeView.getChildren().toString(), "[TreeItem [ value: " + mockCategory + " ]]");
        assertEquals(mockTreeCategory.getChildren().toString(), "[TreeItem [ value: " + mockModel + " ]]");
        assertEquals(mainController.treeView.getExpandedItemCount(), 1);
    }
}