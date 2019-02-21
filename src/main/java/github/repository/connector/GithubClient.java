package github.repository.connector;

import github.repository.connector.models.RepoInfo;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static io.micronaut.http.HttpRequest.GET;


@Requires(notEnv = Environment.TEST)
public class GithubClient implements GithubFetcher {

    @Client("https://api.github.com")
    @Inject RxHttpClient client;

    @Override
    public List<RepoInfo> listRepos(String queryParam) throws Exception {
        String response = getExchange(queryParam);
        return parseJsonString(response);
    }

    private List<RepoInfo> parseJsonString(String response) throws JSONException {
        JSONObject resultJson = new JSONObject(response);
        JSONArray items = resultJson.getJSONArray("items");

        return parseJsonArray(items);
    }

    private List<RepoInfo> parseJsonArray(JSONArray items) throws JSONException {
        List<RepoInfo> res = new ArrayList<>();
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            res.add(
                    new RepoInfo(
                            item.getString("full_name"),
                            item.getJSONObject("owner").getString("login"),
                            item.getString("html_url"),
                            item.getString("description"),
                            item.getString("language")
                    ));
        }
        return res;
    }

    private String getExchange(String queryParam) {
        return client
                .toBlocking()
                .retrieve(GET("/search/repositories?q=" + queryParam).header("User-Agent", "remkop-picocli"));
    }
}
