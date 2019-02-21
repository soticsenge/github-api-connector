package github.repository.connector;

import github.repository.connector.models.RepoInfo;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.retry.annotation.Fallback;
import org.json.JSONException;

import javax.inject.Singleton;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Requires(env = Environment.TEST)
@Fallback
@Singleton
public class GithubClientStub implements GithubFetcher {

    ArrayList<RepoInfo> testRepos = new ArrayList<>() {{
        add(new RepoInfo("repo1", "owner1", "url1", "desc1", "lang1"));
        add(new RepoInfo("repo2", "owner2", "url2", "desc2", "lang2"));
    }};

    @Override
    public List<RepoInfo> listRepos(@NotBlank String queryParam) throws Exception {
        ArrayList<RepoInfo> result = null;

        if (queryParam.equals("properQuery")) {
            result = testRepos;
        } else if (queryParam.equals("emptyQuery")) {
            result =  new ArrayList<>();
        } else if(queryParam.equals("invalidAnswerFormatQuery")) {
            throw new JSONException("Invalid JSON.");
        } else if(queryParam.equals("unexpectedErrorQuery")) {
            throw new Exception("Unexpected error occured.");
        }
        return result;
    }
}