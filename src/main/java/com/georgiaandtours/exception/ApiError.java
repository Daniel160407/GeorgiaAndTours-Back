package com.georgiaandtours.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private String requestURL;
    private String errorMessage;
    private HttpStatus status;
    private LocalDateTime time;
}
