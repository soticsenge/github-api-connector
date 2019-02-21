package github.repository.connector;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.DefaultHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.server.EmbeddedServer;
import org.json.JSONArray;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static io.micronaut.http.HttpStatus.BAD_GATEWAY;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

public class GithubControllerTest {


    private static EmbeddedServer server = ApplicationContext.run(EmbeddedServer.class);
    private static HttpClient client;

    @BeforeClass
    public static void setupServer() {

        client = server
                .getApplicationContext()
                .createBean(DefaultHttpClient.class, server.getURL());
    }

    @AfterClass
    public static void stopServer() {
        if (server != null) {
            server.stop();
        }
        if (client != null) {
            client.stop();
        }
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testListReposWithEmptyAnswer() throws Exception {

        HttpRequest request = HttpRequest.GET("/repos?q=emptyQuery");
        JSONArray jsonResponse = new JSONArray(client.toBlocking().retrieve(request));

        assertEquals("returns empty repo list", 0, jsonResponse.length());
    }

    @Test
    public void testListReposWithNonEmptyAnswer() throws Exception {

        HttpRequest request = HttpRequest.GET("/repos?q=properQuery");
        JSONArray jsonResponse = new JSONArray(client.toBlocking().retrieve(request));

        assertEquals("returns proper repo list", 2, jsonResponse.length());
        assertEquals("returns first repo",
                "{\"owner\":\"owner1\",\"fullName\":\"repo1\",\"description\":\"desc1\",\"language\":\"lang1\",\"url\":\"url1\"}",
                jsonResponse.getString(0));
        assertEquals("returns first repo",
                "{\"owner\":\"owner2\",\"fullName\":\"repo2\",\"description\":\"desc2\",\"language\":\"lang2\",\"url\":\"url2\"}",
                jsonResponse.getString(1));
    }

    @Test
    public void testListReposWithJsonError() {

        exception.expect(HttpClientResponseException.class);
        exception.expectMessage("Invalid JSON.");
        exception.expect(hasProperty("response", hasProperty("status", is(BAD_GATEWAY))));

        HttpRequest request = HttpRequest.GET("/repos?q=invalidAnswerFormatQuery");
        client.toBlocking().exchange(request);
    }

    @Test
    public void testListReposWithRandomError() {


        exception.expect(HttpClientResponseException.class);
        exception.expectMessage("Unexpected error occured.");
        exception.expect(hasProperty("response", hasProperty("status", is(BAD_GATEWAY))));

        HttpRequest request = HttpRequest.GET("/repos?q=unexpectedErrorQuery");
        client.toBlocking().exchange(request);

    }

}
