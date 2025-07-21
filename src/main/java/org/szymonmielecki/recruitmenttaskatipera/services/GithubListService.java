package org.szymonmielecki.recruitmenttaskatipera.services;

import org.szymonmielecki.recruitmenttaskatipera.dtos.RepositoryDTO;
import org.szymonmielecki.recruitmenttaskatipera.exceptions.UserNotFoundException;

import java.util.List;

public interface GithubListService {
    List<RepositoryDTO> getUserRepositories(String username) throws UserNotFoundException;
}
