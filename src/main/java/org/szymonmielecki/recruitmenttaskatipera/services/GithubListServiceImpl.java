package org.szymonmielecki.recruitmenttaskatipera.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.szymonmielecki.recruitmenttaskatipera.config.GithubConfig;
import org.szymonmielecki.recruitmenttaskatipera.dtos.BranchDTO;
import org.szymonmielecki.recruitmenttaskatipera.dtos.RepositoryDTO;
import org.szymonmielecki.recruitmenttaskatipera.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GithubListServiceImpl implements GithubListService {

    private final GithubConfig githubConfig;
    private final RestTemplate restTemplate;

    public GithubListServiceImpl(GithubConfig githubConfig) {
        this.githubConfig = githubConfig;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public List<RepositoryDTO> getUserRepositories(String username) throws UserNotFoundException {
        if (!checkUserExists(username)) {
            throw new UserNotFoundException();
        }

        String reposUrl = githubConfig.getBaseUrl() + "/users/" + username + "/repos";
        HttpHeaders headers = createHeaders();

        ResponseEntity<Map[]> reposResponse = restTemplate.exchange(
                reposUrl,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Map[].class
        );

        Map[] repositories = reposResponse.getBody();
        if (repositories == null) {
            return List.of();
        }

        return List.of(repositories)
                .stream()
                .filter(repo -> !Boolean.TRUE.equals(repo.get("fork")))
                .map(repo -> {
                    String repoName = (String) repo.get("name");
                    List<BranchDTO> branches = getBranches(username, repoName);
                    return new RepositoryDTO(repoName, username, branches);
                })
                .collect(Collectors.toList());


    }

    private boolean checkUserExists(String username) {
        try {
            String userUrl = githubConfig.getBaseUrl() + "/users/" + username;
            HttpHeaders headers = createHeaders();

            ResponseEntity<Map> response = restTemplate.exchange(
                    userUrl,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    Map.class
            );

            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }

    private List<BranchDTO> getBranches(String username, String repoName) {
        try {
            String branchesUrl = githubConfig.getBaseUrl() + "/repos/" + username + "/" + repoName + "/branches";
            HttpHeaders headers = createHeaders();

            ResponseEntity<Map[]> branchesResponse = restTemplate.exchange(
                    branchesUrl,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    Map[].class
            );

            Map[] branches = branchesResponse.getBody();
            if (branches == null) {
                return List.of();
            }

            return List.of(branches)
                    .stream()
                    .map(branch -> {
                        String branchName = (String) branch.get("name");
                        String commitSha = (String) ((Map) branch.get("commit")).get("sha");
                        return new BranchDTO(branchName, commitSha.getBytes());
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return List.of();
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.github.v3+json");
        headers.set("User-Agent", "RecruitmentTaskAtipera");
        headers.set("Authorization", "Bearer " + githubConfig.getToken());

        return headers;
    }
}
