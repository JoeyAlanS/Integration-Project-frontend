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
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ControllerTest extends ApplicationTest {

    private Controller mainController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
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
        verify(mainController).comboBoxSelect();
    }

    @Test
    public void testComboBox01() {
        // Given
        List<LineupDTO> mockList = new ArrayList<>();
        mockList.add(new LineupDTO("Ares", (short) 1));
        mockList.add(new LineupDTO("Cronos", (short) 2));
        when(mainController.lineupService.getAllLineup()).thenReturn(mockList);

        // When
        mainController.comboBoxSelect();

        // Then
        assertEquals(mockList.toString(), mainController.comboBox.getItems().toString());
        verify(mainController.lineupService).getAllLineup();
    }

    @Test
    public void testComboBox02() {
        // Given
        List<LineupDTO> mockList = new ArrayList<>();
        mockList.add(new LineupDTO("Ares", (short) 1));
        when(mainController.lineupService.getAllLineup()).thenReturn(mockList);
        mainController.comboBoxSelect();

        LineupDTO selectedItem = mockList.get(0);

        // When
        mainController.comboBox.getSelectionModel().select(selectedItem);

        // Then
        verify(mainController, times(1)).openTreeView(selectedItem);
        assertEquals(selectedItem, mainController.comboBox.getValue());
    }

    @Test
    public void testComboBox03() {
        // Given
        List<LineupDTO> mockList = new ArrayList<>();
        mockList.add(new LineupDTO("Ares", (short) 1));
        mockList.add(new LineupDTO("Cronos", (short) 2));
        when(mainController.lineupService.getAllLineup()).thenReturn(mockList);
        mainController.comboBoxSelect();

        LineupDTO newItem = mockList.get(1);

        // When
        mainController.comboBox.getSelectionModel().select(newItem);

        // Then
        verify(mainController, times(1)).openTreeView(newItem);
        assertEquals(newItem, mainController.comboBox.getValue());
    }

    @Test
    public void testComboBox04() {
        // Given
        List<LineupDTO> mockList = new ArrayList<>();
        LineupDTO mockLine = new LineupDTO("Ares", (short) 1);
        mockList.add(mockLine);
        when(mainController.lineupService.getAllLineup()).thenReturn(mockList);

        // When
        mainController.comboBoxSelect();
        mainController.comboBox.setValue(mockLine);

        // Then
        verify(mainController, times(1)).openTreeView(mockLine);
    }

    @Test
    public void testComboBox05() {
        // Given
        List<LineupDTO> mockList = new ArrayList<>();
        mockList.add(new LineupDTO("Ares", (short) 1));
        mockList.add(new LineupDTO("Cronos", (short) 2));
        when(mainController.lineupService.getAllLineup()).thenReturn(mockList);

        // When
        mainController.comboBoxSelect();
        LineupDTO selectedItem = mockList.get(0);
        mainController.comboBox.getSelectionModel().select(selectedItem);

        // Then
        verify(mainController, times(1)).openTreeView(selectedItem);
    }

    @Test
    public void testComboBox06() {
        // Given
        List<LineupDTO> emptyList = new ArrayList<>();
        when(mainController.lineupService.getAllLineup()).thenReturn(emptyList);

        // When
        mainController.comboBoxSelect();

        // Then
        assertEquals(FXCollections.observableArrayList(emptyList), mainController.comboBox.getItems());
        verify(mainController.lineupService).getAllLineup();
    }

    @Test
    public void testComboBox07() {
        // Given
        List<LineupDTO> mockList = new ArrayList<>();
        mockList.add(new LineupDTO("Ares", (short) 1));
        mockList.add(new LineupDTO("Cronos", (short) 2));
        when(mainController.lineupService.getAllLineup()).thenReturn(mockList);

        // When
        mainController.comboBoxSelect();
        LineupDTO selectedItem = mockList.get(0);
        mainController.comboBox.getSelectionModel().select(selectedItem);
        mainController.comboBox.valueProperty().set(null);

        // Then
        verify(mainController, times(1)).openTreeView(null);
        mainController.comboBox.valueProperty().set(selectedItem);
        verify(mainController, times(2)).openTreeView(selectedItem);
    }

    @Test
    public void testOpenTreeView01() {
        // Given
        LineupDTO mockLineup = new LineupDTO("Ares", (short) 2);
        CategoryDTO mockCategory = new CategoryDTO("Ares TB", (short) 3);
        ModelDTO mockModel = new ModelDTO("Ares 7021", (short) 1);
        List<CategoryDTO> mockCategoryList = new ArrayList<>();
        mockCategoryList.add(mockCategory);
        List<ModelDTO> mockModelList = new ArrayList<>();
        mockModelList.add(mockModel);

        when(mainController.categoryService.getAllCategories(mockLineup)).thenReturn(mockCategoryList);
        when(mainController.modelsService.getAllModels(mockCategory)).thenReturn(mockModelList);

        // When
        mainController.openTreeView(mockLineup);

        // Then
        assertEquals(mockLineup, mainController.treeView.getRoot().getValue());
        assertEquals(mockCategory, mainController.treeView.getRoot().getChildren().get(0).getValue());
        assertEquals(mockModel, mainController.treeView.getRoot().getChildren().get(0).getChildren().get(0).getValue());

        assertFalse(mainController.titledLineup.isExpanded());
        assertFalse(mainController.titledModels.isDisable());
        assertTrue(mainController.titledModels.isExpanded());
    }

    @Test
    public void testOpenTreeView02() {
        // Given
        LineupDTO mockLineup = new LineupDTO("Cronos", (short) 2);
        CategoryDTO mockCategory = new CategoryDTO("Cronos Old", (short) 1);
        ModelDTO mockModel = new ModelDTO("Cronos 6001-A", (short) 1);
        List<CategoryDTO> mockCategoryList = new ArrayList<>();
        mockCategoryList.add(mockCategory);
        List<ModelDTO> mockModelList = new ArrayList<>();
        mockModelList.add(mockModel);

        when(mainController.categoryService.getAllCategories(mockLineup)).thenReturn(mockCategoryList);
        when(mainController.modelsService.getAllModels(mockCategory)).thenReturn(mockModelList);

        // When
        mainController.openTreeView(mockLineup);

        // Then
        assertEquals(mockLineup, mainController.treeView.getRoot().getValue());
        assertEquals(mockCategory, mainController.treeView.getRoot().getChildren().get(0).getValue());
        assertEquals(mockModel, mainController.treeView.getRoot().getChildren().get(0).getChildren().get(0).getValue());

        assertFalse(mainController.titledLineup.isExpanded());
        assertFalse(mainController.titledModels.isDisable());
        assertTrue(mainController.titledModels.isExpanded());
    }

    @Test
    public void testOpenTreeView03() {
        // Given
        LineupDTO mockLine = new LineupDTO("Ares", (short) 1);
        List<CategoryDTO> mockCategoryList = new ArrayList<>();

        when(mainController.categoryService.getAllCategories(mockLine)).thenReturn(mockCategoryList);

        // When
        mainController.openTreeView(mockLine);

        // Then
        assertTrue(mainController.treeView.getRoot().getChildren().isEmpty());
    }

    @Test
    public void testOpenTreeView04() {
        // Given
        LineupDTO mockLine = null;

        // When
        mainController.openTreeView(mockLine);

        // Then
        verify(mainController.categoryService, times(0)).getAllCategories(any());
        assertNull(mainController.treeView.getRoot());
    }
}
