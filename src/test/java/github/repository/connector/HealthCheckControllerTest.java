package github.repository.connector;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.PropertySource;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.DefaultHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.runtime.server.EmbeddedServer;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class HealthCheckControllerTest {

    static Map<String, Object> conf = Map.of(
            "version", "testVersion"
    );


    private static EmbeddedServer server = ApplicationContext.run(EmbeddedServer.class,
            PropertySource.of("test", conf),"test");
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

    @Test
    public void testHealthCheck() throws Exception {
        HttpRequest request = HttpRequest.GET("/healthCheck");
        JSONObject jsonResponse = new JSONObject(client.toBlocking().retrieve(request));

        assertEquals("returns proper version", "testVersion",jsonResponse.getString("version"));
        assertEquals("returns proper host name","localhost",jsonResponse.getString("currentHost"));
    }

}
