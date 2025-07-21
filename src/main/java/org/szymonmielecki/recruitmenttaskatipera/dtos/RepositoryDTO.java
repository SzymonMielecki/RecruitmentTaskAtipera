package org.szymonmielecki.recruitmenttaskatipera.dtos;

import java.util.List;

public record RepositoryDTO(
        String repository_name,
        String owner_login,
        List<BranchDTO> branches
) {
}
