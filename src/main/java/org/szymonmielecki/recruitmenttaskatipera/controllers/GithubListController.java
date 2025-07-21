package org.szymonmielecki.recruitmenttaskatipera.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.szymonmielecki.recruitmenttaskatipera.dtos.RepositoryDTO;
import org.szymonmielecki.recruitmenttaskatipera.exceptions.UserNotFoundException;
import org.szymonmielecki.recruitmenttaskatipera.services.GithubListService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
public class GithubListController {
    private final GithubListService service;

    public GithubListController(GithubListService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<?> getUserRepositories(
            @RequestParam String username){

        try {
            List<RepositoryDTO> result = service.getUserRepositories(username);
            return ResponseEntity.ok(result);
        } catch (UserNotFoundException e) {
            Map<String, Object> error = Map.of(
                "status", 404,
                "message", "User not found: " + username
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "status", 500,
                "message", "Internal server error: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
