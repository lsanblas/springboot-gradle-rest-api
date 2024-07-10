package dev.example.restapi.model.error;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ErrorDetail {
    private String code;
    private String message;
    private String target;
    private List<String> details;
    private InnerError innerError;
}
