package dev.example.restapi.model.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="USER_TB", schema = "DB2INST1")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String UUID;
    private String email;
    private String name;
    private LocalDateTime createdDate;
    @Enumerated(EnumType.STRING)
    private UserType userType;
}