package dev.example.restapi.service;

import dev.example.restapi.exception.UserNotFoundException;
import dev.example.restapi.mapper.UserMapper;
import dev.example.restapi.model.user.UserEntity;
import dev.example.restapi.model.user.UserRequest;
import dev.example.restapi.model.user.UserResponse;
import dev.example.restapi.model.user.UserType;
import dev.example.restapi.respository.UserEntityRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserMapper userMapper;
    private final UserEntityRepository userRepository;

    @Value("${spring.application.host.base}")
    private String baseHost;

    public UserResponse create(UserRequest userRequest) {
        UserEntity userEntity = userMapper.UserRequesttoUserEntity(userRequest);
        UUID uuid = UUID.randomUUID();
        userEntity.setUUID(uuid.toString());
        userEntity.setCreatedDate(LocalDateTime.now());
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return userMapper.UserEntitytoUserResponse(savedUserEntity);
    }

    public UserResponse get(String uuid) {
        UserEntity userEntity = userRepository.findByUUID(uuid).orElseThrow(() -> new UserNotFoundException(uuid));
        return userMapper.UserEntitytoUserResponse(userEntity);
    }

    public List<UserResponse> getAll(Pageable pageable) {
        Page<UserEntity> userEntities = userRepository.findAll(pageable);
        return userEntities.stream().map(userMapper::UserEntitytoUserResponse).toList();
    }

    public ResponseEntity<?> update(UserRequest userRequest, String uuid, HttpServletRequest request) {
        boolean newEntity = false;
        UserEntity userEntity = userRepository.findByUUID(uuid).orElse(new UserEntity());
        if(userEntity.getUUID() == null){
            newEntity = true;
        }
        userEntity.setUUID(uuid);
        userEntity.setName(userRequest.name());
        userEntity.setEmail(userRequest.email());
        userEntity.setUserType(UserType.valueOf(userRequest.userType()));
        userEntity.setCreatedDate(LocalDateTime.now());
        UserEntity updatedUserEntity = userRepository.save(userEntity);
        if(newEntity){
            return ResponseEntity.created(URI.create(baseHost + request.getRequestURI()+"/"+updatedUserEntity.getUUID()))
                    .body(userMapper.UserEntitytoUserResponse(updatedUserEntity));
        }
        return ResponseEntity.ok(userMapper.UserEntitytoUserResponse(updatedUserEntity));
    }

    public UserResponse partialUpdate(UserRequest userRequest, String uuid) {
        UserEntity userEntity = userRepository.findByUUID(uuid).orElseThrow(() -> new UserNotFoundException(uuid));
        if(userRequest.name() != null && !userRequest.name().isEmpty()){
            userEntity.setName(userRequest.name());
        }
        if(userRequest.email() != null && !userRequest.email().isEmpty()){
            userEntity.setEmail(userRequest.email());
        }
        if(userRequest.userType() != null && !userRequest.userType().isEmpty()){
            userEntity.setUserType(UserType.valueOf(userRequest.userType()));
        }
        UserEntity updatedUserEntity = userRepository.save(userEntity);
        return userMapper.UserEntitytoUserResponse(updatedUserEntity);
    }

    public void delete(String uuid) {
        UserEntity userEntity = userRepository.findByUUID(uuid).orElseThrow(() -> new UserNotFoundException(uuid));
        userRepository.deleteById(userEntity.getId());
    }
}