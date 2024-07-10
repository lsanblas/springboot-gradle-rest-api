package dev.example.restapi.model.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InnerError {
    private String code;
    private String message;
    private InnerError innerError;
}
