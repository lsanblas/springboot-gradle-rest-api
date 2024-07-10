package dev.example.restapi.controller;

import dev.example.restapi.model.error.ErrorDetail;
import dev.example.restapi.model.error.ErrorResponse;
import dev.example.restapi.model.user.UserRequest;
import dev.example.restapi.model.user.UserResponse;
import dev.example.restapi.service.UserService;
import dev.example.restapi.service.VersionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserControllerQueryVersioning {

    private final UserService userService;
    private final VersionService versionService;

    @Value("${spring.table.pagination}")
    private String defaulPageSize;

    @Value("${spring.application.host.base}")
    private String baseHost;

    @PostMapping("/users")
    ResponseEntity<?> createUser(@RequestBody UserRequest userRequest, HttpServletRequest request, @RequestParam("api-version") String version) {
        if(versionService.isVersionSupported(version)){
            UserResponse userResponse = userService.create(userRequest);
            return ResponseEntity.created(URI.create(baseHost + request.getRequestURI() + "/" + userResponse.id())).body(userResponse);
        }
        return ResponseEntity.badRequest().body(new ErrorResponse(new ErrorDetail("UnsupportedApiVersionValue" , "Unsupported api-version", null, null, null)));
    }

    @GetMapping("/users")
    ResponseEntity<?> getUsers(@RequestParam("offset") Optional<Integer> page, @RequestParam("limit") Optional<Integer> size, @RequestParam("api-version") String version) {
        if(versionService.isVersionSupported(version)){
            int currentPage = page.orElse(1);
            int pageSize = size.orElse(Integer.parseInt(defaulPageSize));
            return ResponseEntity.ok(userService.getAll(PageRequest.of(currentPage - 1, pageSize)));
        }
        return ResponseEntity.badRequest().body(new ErrorResponse(new ErrorDetail("UnsupportedApiVersionValue" , "Unsupported api-version", null, null, null)));
    }

    @GetMapping("/users/{id}")
    ResponseEntity<?> getUser(@PathVariable("id") String id, @RequestParam("api-version") String version) {
        if(versionService.isVersionSupported(version)){
            return ResponseEntity.ok(userService.get(id));
        }
        return ResponseEntity.badRequest().body(new ErrorResponse(new ErrorDetail("UnsupportedApiVersionValue" , "Unsupported api-version", null, null, null)));
    }

    @PutMapping("/users/{id}")
    ResponseEntity<?> updateUser(@RequestBody UserRequest userRequest, @PathVariable("id") String id, HttpServletRequest request, @RequestParam("api-version") String version) {
        if(versionService.isVersionSupported(version)){
            return userService.update(userRequest, id, request);
        }
        return ResponseEntity.badRequest().body(new ErrorResponse(new ErrorDetail("UnsupportedApiVersionValue" , "Unsupported api-version", null, null, null)));
    }

    @PatchMapping("/users/{id}")
    ResponseEntity<?> partialUpdate(@RequestBody UserRequest userRequest, @PathVariable("id") String id, @RequestParam("api-version") String version) {
        if(versionService.isVersionSupported(version)){
            return ResponseEntity.ok(userService.partialUpdate(userRequest, id));
        }
        return ResponseEntity.badRequest().body(new ErrorResponse(new ErrorDetail("UnsupportedApiVersionValue" , "Unsupported api-version", null, null, null)));
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<?> deleteUser(@PathVariable("id") String id, @RequestParam("api-version") String version) {
        if(versionService.isVersionSupported(version)){
            userService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().body(new ErrorResponse(new ErrorDetail("UnsupportedApiVersionValue" , "Unsupported api-version", null, null, null)));
    }
}
