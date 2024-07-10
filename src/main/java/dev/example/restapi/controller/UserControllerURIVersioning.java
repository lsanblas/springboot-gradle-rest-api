package dev.example.restapi.controller;

import dev.example.restapi.model.user.UserRequest;
import dev.example.restapi.model.user.UserResponse;
import dev.example.restapi.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class UserControllerURIVersioning {

    private final UserService userService;

    @Value("${spring.table.pagination}")
    private String defaulPageSize;

    @Value("${spring.application.host.base}")
    private String baseHost;

    @PostMapping("/users")
    ResponseEntity<?> createUser(@RequestBody UserRequest userRequest, HttpServletRequest request) {
        UserResponse userResponse = userService.create(userRequest);
        return ResponseEntity.created(URI.create(baseHost + request.getRequestURI() + "/" + userResponse.id())).body(userResponse);
    }

    @GetMapping("/users")
    ResponseEntity<?> getUsers(@RequestParam("offset") Optional<Integer> page, @RequestParam("limit") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(Integer.parseInt(defaulPageSize));
        return ResponseEntity.ok(userService.getAll(PageRequest.of(currentPage - 1, pageSize)));
    }

    @GetMapping("/users/{id}")
    ResponseEntity<?> getUser(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @PutMapping("/users/{id}")
    ResponseEntity<?> updateUser(@RequestBody UserRequest userRequest, @PathVariable("id") String id, HttpServletRequest request) {
        return userService.update(userRequest, id, request);
    }

    @PatchMapping("/users/{id}")
    ResponseEntity<?> partialUpdate(@RequestBody UserRequest userRequest, @PathVariable("id") String id, HttpServletRequest request) {
        return ResponseEntity.ok(userService.partialUpdate(userRequest, id));
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<?> deleteUser(@PathVariable("id") String id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
