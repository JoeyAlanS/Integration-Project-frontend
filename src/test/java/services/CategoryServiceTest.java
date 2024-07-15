package services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.LineupDTO;
import dto.CategoryDTO;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    private CategoryService service;
    private Client client;
    private WebTarget webTarget;
    private Invocation.Builder builder;
    private Response response;

    @Before
    public void setUp() {
        client = mock(Client.class);
        webTarget = mock(WebTarget.class);
        builder = mock(Invocation.Builder.class);
        response = mock(Response.class);

        service = new CategoryService();
        service.setClient(client);

        when(client.target(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.get()).thenReturn(response);
    }

    @Test
    public void getAllCategoriesAndConvertToStringTest01() {
        // Given
        String jsonResponse = "[{\"id\":2,\"categoryName\":\"Cronos L\",\"line\":\"Cronos\"},{\"id\":3,\"categoryName\":\"Cronos NG\",\"line\": \"Cronos\"},{\"id\": 4,\"categoryName\":\"Ares TB\",\"line\": \"Ares\"},{\"id\":5,\"categoryName\":\"Ares THS\",\"line\":\"Ares\"},{\"id\":1,\"categoryName\":\"Cronos Old\",\"line\": \"Cronos\"}]";
        LineupDTO mockLine = new LineupDTO("Ares", (short) 1);
        Gson mockGson = new Gson();
        when(response.readEntity(String.class)).thenReturn(jsonResponse);
        Type mockCategoryListType = new TypeToken<List<CategoryDTO>>() {
        }.getType();
        List<CategoryDTO> mockCategoryList = mockGson.fromJson(response.readEntity(String.class), mockCategoryListType);

        // When
        List<CategoryDTO> result = service.getAllCategories(mockLine);

        // Then
        assertNotNull(result);
        assertEquals(response.readEntity(String.class), jsonResponse);
        assertEquals(5, result.size());
        assertEquals(result.toString(), mockCategoryList.toString());
        verify(client).target(eq("http://localhost:8080/api/categories" + "/" + mockLine));
        verify(webTarget).request(MediaType.APPLICATION_JSON);
        verify(builder).get();
    }

    @Test
    public void getAllCategoriesAndConvertToStringTest02() {
        // Given
        String jsonResponse = "[{\"id\":2,\"categoryName\":\"Cronos L\",\"line\":\"Cronos\"},{\"id\":3,\"categoryName\":\"Cronos NG\",\"line\": \"Cronos\"},{\"id\": 4,\"categoryName\":\"Ares TB\",\"line\": \"Ares\"},{\"id\":5,\"categoryName\":\"Ares THS\",\"line\":\"Ares\"},{\"id\":1,\"categoryName\":\"Cronos Old\",\"line\": \"Cronos\"}]";
        LineupDTO mockLine = new LineupDTO("1", (short) 1);
        Gson mockGson = new Gson();
        when(response.readEntity(String.class)).thenReturn(jsonResponse);
        Type mockCategoryListType = new TypeToken<List<CategoryDTO>>() {
        }.getType();
        List<CategoryDTO> mockCategoryList = mockGson.fromJson(response.readEntity(String.class), mockCategoryListType);

        // When
        List<CategoryDTO> result = service.getAllCategories(mockLine);

        // Then
        assertNotNull(result);
        assertEquals(response.readEntity(String.class), jsonResponse);
        assertEquals(5, result.size());
        assertEquals(result.toString(), mockCategoryList.toString());
        verify(client).target(eq("http://localhost:8080/api/categories" + "/" + mockLine));
        verify(webTarget).request(MediaType.APPLICATION_JSON);
        verify(builder).get();
    }
}