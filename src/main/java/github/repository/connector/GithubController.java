package github.repository.connector;

import github.repository.connector.models.RepoInfo;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.hateos.JsonError;
import io.micronaut.http.hateos.Link;
import org.json.JSONException;

import java.util.List;


@Controller()
public class GithubController {

    private GithubFetcher githubFetcher;

    public GithubController(GithubFetcher gitHubFetcher) {
        this.githubFetcher = gitHubFetcher;
    }

    @Get("/repos")
    public List<RepoInfo> listRepos(@QueryValue("q") String queryParam) throws Exception {
        return this.githubFetcher.listRepos(queryParam);
    }

    @Error
    public HttpResponse<JsonError> jsonError(HttpRequest request, JSONException jsonParseException) {
        JsonError error = new JsonError(jsonParseException.getMessage())
                .link(Link.SELF, Link.of(request.getUri()));

        return HttpResponse.<JsonError>status(HttpStatus.BAD_GATEWAY, "Invalid JSON from client.")
                .body(error);
    }

    @Error
    public HttpResponse<JsonError> generalError(HttpRequest request, Exception exception) {
        JsonError error = new JsonError(exception.getMessage())
                .link(Link.SELF, Link.of(request.getUri()));

        return HttpResponse.<JsonError>status(HttpStatus.BAD_GATEWAY, "Unexpected error occured.")
                .body(error);
    }
}
