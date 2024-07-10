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
public class UserControllerMediaTypeVersioning {
    private final UserService userService;
    private final VersionService versionService;

    @Value("${spring.table.pagination}")
    private String defaulPageSize;

    @Value("${spring.application.host.base}")
    private String baseHost;

    @PostMapping("/users_m")
    ResponseEntity<?> createUser(@RequestBody UserRequest userRequest, HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        String version = versionService.extractVersion(accept);
        if(versionService.isVersionSupported(version)){
            UserResponse userResponse = userService.create(userRequest);
            return ResponseEntity.created(URI.create(baseHost + request.getRequestURI() + "/" + userResponse.id())).body(userResponse);
        }
        return ResponseEntity.badRequest().body(new ErrorResponse(new ErrorDetail("UnsupportedApiVersionValue" , "Unsupported api-version", null, null, null)));
    }

    @GetMapping("/users_m")
    ResponseEntity<?> getUsers(@RequestParam("offset") Optional<Integer> page, @RequestParam("limit") Optional<Integer> size, HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        String version = versionService.extractVersion(accept);
        if(versionService.isVersionSupported(version)){
            int currentPage = page.orElse(1);
            int pageSize = size.orElse(Integer.parseInt(defaulPageSize));
            return ResponseEntity.ok(userService.getAll(PageRequest.of(currentPage - 1, pageSize)));
        }
        return ResponseEntity.badRequest().body(new ErrorResponse(new ErrorDetail("UnsupportedApiVersionValue" , "Unsupported api-version", null, null, null)));
    }

    @GetMapping("/users_m/{id}")
    ResponseEntity<?> getUser(@PathVariable("id") String id, HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        String version = versionService.extractVersion(accept);
        if(versionService.isVersionSupported(version)){
            return ResponseEntity.ok(userService.get(id));
        }
        return ResponseEntity.badRequest().body(new ErrorResponse(new ErrorDetail("UnsupportedApiVersionValue" , "Unsupported api-version", null, null, null)));
    }

    @PutMapping("/users_m/{id}")
    ResponseEntity<?> updateUser(@RequestBody UserRequest userRequest, @PathVariable("id") String id, HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        String version = versionService.extractVersion(accept);
        if(versionService.isVersionSupported(version)){
            return userService.update(userRequest, id, request);
        }
        return ResponseEntity.badRequest().body(new ErrorResponse(new ErrorDetail("UnsupportedApiVersionValue" , "Unsupported api-version", null, null, null)));
    }

    @PatchMapping("/users_m/{id}")
    ResponseEntity<?> partialUpdate(@RequestBody UserRequest userRequest, @PathVariable("id") String id, HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        String version = versionService.extractVersion(accept);
        if(versionService.isVersionSupported(version)){
            return ResponseEntity.ok(userService.partialUpdate(userRequest, id));
        }
        return ResponseEntity.badRequest().body(new ErrorResponse(new ErrorDetail("UnsupportedApiVersionValue" , "Unsupported api-version", null, null, null)));
    }

    @DeleteMapping("/users_m/{id}")
    ResponseEntity<?> deleteUser(@PathVariable("id") String id, HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        String version = versionService.extractVersion(accept);
        if(versionService.isVersionSupported(version)){
            userService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().body(new ErrorResponse(new ErrorDetail("UnsupportedApiVersionValue" , "Unsupported api-version", null, null, null)));
    }
}
