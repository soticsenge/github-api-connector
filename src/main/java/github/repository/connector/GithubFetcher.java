package github.repository.connector;

import github.repository.connector.models.RepoInfo;

import java.util.List;

public interface GithubFetcher {
    List<RepoInfo> listRepos(String queryParam) throws Exception;
}
