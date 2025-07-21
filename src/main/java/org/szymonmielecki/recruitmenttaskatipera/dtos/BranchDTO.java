package org.szymonmielecki.recruitmenttaskatipera.dtos;

public record BranchDTO(
        String branch_name,
        byte[] last_commit_hash
) {
}
