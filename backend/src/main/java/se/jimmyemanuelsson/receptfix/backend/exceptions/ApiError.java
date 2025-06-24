package se.jimmyemanuelsson.receptfix.backend.exceptions;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiError {
    private String code;
    private String description;
    private Integer statusCode;
}