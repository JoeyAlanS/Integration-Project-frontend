package services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.CategoryDTO;
import dto.LineupDTO;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class CategoryService {
    private static final String BASE_URL = "http://localhost:8080/api/categories";
    Client client = ClientBuilder.newClient(new ClientConfig());

    public List<CategoryDTO> getAllCategories(LineupDTO selectedLine) {
        if (selectedLine == null) {
            return Collections.emptyList();
        }
        WebTarget myResource = client.target(BASE_URL + "/" + selectedLine.getLineName());
        Invocation.Builder builder = myResource.request(MediaType.APPLICATION_JSON);
        Response response = builder.get();
        Gson gsonCat = new Gson();
        Type categoryListType = new TypeToken<List<CategoryDTO>>() {
        }.getType();
        List<CategoryDTO> catList = gsonCat.fromJson(response.readEntity(String.class), categoryListType);
        return catList;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
