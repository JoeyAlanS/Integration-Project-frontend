package services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.CategoryDTO;
import dto.ModelDTO;
import org.junit.jupiter.api.BeforeEach;
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
public class ModelServiceTest {

    private ModelService service;
    private Client client;
    private WebTarget webTarget;
    private Invocation.Builder builder;
    private Response response;

    @BeforeEach
    public void setUp() {
        client = mock(Client.class);
        webTarget = mock(WebTarget.class);
        builder = mock(Invocation.Builder.class);
        response = mock(Response.class);

        service = new ModelService();
        service.setClient(client);

        when(client.target(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.get()).thenReturn(response);
    }

    @Test
    public void getAllModelsAndConvertToStringTest() {
        // Given
        String jsonResponse = "[{\"id\":1,\"modelName\":\"Cronos 6001-A\",\"category\":\"Cronos Old\"},{\"id\": 2,\"modelName\":\"Cronos 6003\",\"category\":\"Cronos Old\"},{\"id\":3,\"modelName\":\"Cronos 7023\",\"category\":\"Cronos Old\"},{\"id\":4,\"modelName\":\"Cronos 6021L\",\"category\":\"Cronos L\"},{\"id\":5,\"modelName\":\"Cronos 7023L\",\"category\":\"Cronos L\"},{\"id\":6,\"modelName\":\"Cronos 6001-NG\",\"category\":\"Cronos NG\"},{\"id\":7,\"modelName\":\"Cronos 6003-NG\",\"category\":\"Cronos NG\"},{\"id\":8,\"modelName\":\"Cronos 6021-NG\",\"category\":\"Cronos NG\"},{\"id\":9,\"modelName\":\"Cronos 6031-NG\",\"category\":\"Cronos NG\"},{\"id\":10,\"modelName\":\"Cronos 7021-NG\",\"category\":\"Cronos NG\"},{\"id\":11,\"modelName\":\"Cronos 7023-NG\",\"category\":\"Cronos NG\"},{\"id\":12,\"modelName\":\"Ares 7021\",\"category\":\"Ares TB\"},{\"id\":13,\"modelName\":\"Ares 7031\",\"category\":\"Ares TB\"},{\"id\":14,\"modelName\":\"Ares 7023\",\"category\":\"Ares TB\"},{\"id\":15,\"modelName\":\"Ares 8023 15\",\"category\":\"Ares THS\"},{\"id\":16,\"modelName\":\"Ares 8023 200\",\"category\":\"Ares THS\"},{\"id\":17,\"modelName\":\"Ares 8023 2,5\",\"category\":\"Ares THS\"}]";
        CategoryDTO mockCategory = new CategoryDTO("Ares%20TB", (short) 1);
        Gson mockGson = new Gson();
        when(response.readEntity(String.class)).thenReturn(jsonResponse);
        Type mockModelListType = new TypeToken<List<ModelDTO>>() {
        }.getType();
        List<ModelDTO> mockList = mockGson.fromJson(response.readEntity(String.class), mockModelListType);

        // When
        List<ModelDTO> result = service.getAllModels(mockCategory);

        //Then
        assertNotNull(result);
        assertEquals(17, result.size());
        assertEquals(result.toString(), mockList.toString());
        verify(client).target(eq("http://localhost:8080/api/models" + "/" + mockCategory));
        verify(webTarget).request(MediaType.APPLICATION_JSON);
        verify(builder).get();
    }

}