package mainController;

import dto.CategoryDTO;
import dto.LineupDTO;
import dto.ModelDTO;
import services.CategoryService;
import services.LineupService;
import services.ModelService;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import org.junit.After;
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

    @After
    public void tearDown() {
        mainController.titledModels = null;
        mainController.titledLineup = null;
        mainController.comboBox = null;
        mainController.treeView = null;
        mainController.accordion = null;
        mainController = null;
    }

    @Test
    public void testInitialize() {
        // Given
        Mockito.doNothing().when(mainController).comboBoxSelect();

        // When
        mainController.initialize(null, null);

        // Then
        assertEquals(mainController.accordion.getExpandedPane(), mainController.titledLineup);
        assertTrue(mainController.titledModels.isDisable());
        assertTrue(mainController.comboBox.getItems().isEmpty());
        assertNull(mainController.treeView.getRoot());
    }

    @Test
    public void comboBoxSelectTest() {
        // Given
        List<LineupDTO> mockList = new ArrayList<>();
        mockList.add(new LineupDTO("Ares", (short) 1));
        mockList.add(new LineupDTO("Cronos", (short) 2));
        Mockito.when(mainController.lineupService.getAllLineup()).thenReturn(mockList);

        // When
        mainController.comboBoxSelect();

        // Then
        assertEquals(FXCollections.observableArrayList(mockList), mainController.comboBox.getItems());
        verify(mainController.lineupService).getAllLineup();
    }

    @Test
    public void comboBoxSelectEmptyTest() {
        // Given
        List<LineupDTO> mockList = new ArrayList<>();
        Mockito.when(mainController.lineupService.getAllLineup()).thenReturn(mockList);

        // When
        mainController.comboBoxSelect();

        // Then
        assertTrue(mainController.comboBox.getItems().isEmpty());
        verify(mainController.lineupService).getAllLineup();
    }

    @Test
    public void openTreeViewTest() {
        // Given
        LineupDTO mockLine = new LineupDTO("Ares", (short) 1);
        CategoryDTO mockCategory1 = new CategoryDTO("Ares TB", (short) 1);
        CategoryDTO mockCategory2 = new CategoryDTO("Ares LB", (short) 2);
        ModelDTO mockModel1 = new ModelDTO("Ares 7021", (short) 1);
        ModelDTO mockModel2 = new ModelDTO("Ares 7022", (short) 2);
        List<CategoryDTO> mockCategoryList = new ArrayList<>();
        mockCategoryList.add(mockCategory1);
        mockCategoryList.add(mockCategory2);
        List<ModelDTO> mockModelList1 = new ArrayList<>();
        List<ModelDTO> mockModelList2 = new ArrayList<>();
        mockModelList1.add(mockModel1);
        mockModelList2.add(mockModel2);

        when(mainController.categoryService.getAllCategories(mockLine)).thenReturn(mockCategoryList);
        when(mainController.modelsService.getAllModels(mockCategory1)).thenReturn(mockModelList1);
        when(mainController.modelsService.getAllModels(mockCategory2)).thenReturn(mockModelList2);

        // When
        mainController.openTreeView(mockLine);

        // Then
        assertFalse(mainController.titledLineup.isExpanded());
        assertFalse(mainController.titledModels.isDisable());
        assertTrue(mainController.titledModels.isExpanded());
        assertTrue(mainController.treeView.isVisible());

        TreeItem lineupTreeItem = mainController.treeView.getTreeItem(0);
        assertEquals(lineupTreeItem.getValue(), mockLine);

        List<TreeItem> categoryItems = lineupTreeItem.getChildren();
        assertEquals(2, categoryItems.size());
        assertEquals(categoryItems.get(0).getValue(), mockCategory1);
        assertEquals(categoryItems.get(1).getValue(), mockCategory2);

        List<TreeItem> modelItems1 = categoryItems.get(0).getChildren();
        List<TreeItem> modelItems2 = categoryItems.get(1).getChildren();
        assertEquals(1, modelItems1.size());
        assertEquals(1, modelItems2.size());
        assertEquals(modelItems1.get(0).getValue(), mockModel1);
        assertEquals(modelItems2.get(0).getValue(), mockModel2);
    }

    @Test
    public void openTreeViewEmptyTest() {
        // Given
        LineupDTO mockLine = new LineupDTO("Ares", (short) 1);
        List<CategoryDTO> mockCategoryList = new ArrayList<>();

        when(mainController.categoryService.getAllCategories(mockLine)).thenReturn(mockCategoryList);

        // When
        mainController.openTreeView(mockLine);

        // Then
        assertTrue(mainController.treeView.getRoot().getChildren().isEmpty());
    }
}
