package services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.LineupDTO;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LineupServiceTest {

    private LineupService service;
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

        service = new LineupService();
        service.setClient(client);

        when(client.target(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.get()).thenReturn(response);
    }

    @Test
    public void getAllLinesConvertToStringTest01() {
        // Given
        String jsonResponse = "[{\"id\":1,\"name\":\"Ares\"},{\"id\":2,\"name\":\"Cronos\"}]";
        Gson gson = new Gson();
        Type lineupListType = new TypeToken<List<LineupDTO>>() {
        }.getType();
        List<LineupDTO> expectedList = gson.fromJson(jsonResponse, lineupListType);

        when(response.readEntity(String.class)).thenReturn(jsonResponse);

        // When
        List<LineupDTO> result = service.getAllLineup();

        // Then
        assertEquals(2, result.size());
        assertEquals(expectedList.toString(), result.toString());
        verify(client).target(eq("http://localhost:8080/api/lines"));
        verify(webTarget).request(MediaType.APPLICATION_JSON);
        verify(builder).get();
    }
}
