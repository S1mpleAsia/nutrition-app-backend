package com.example.nutritionapp.http.response;

import com.example.nutritionapp.dto.RespStatusDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneralResponse<T> {
    private RespStatusDTO status;
    private T data;

    public static <T> GeneralResponse<T> success(T data) {
        return new GeneralResponse<>(RespStatusDTO.builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .build(), data);
    }

    public static <T> GeneralResponse<T> error(String message, T data) {
        return new GeneralResponse<>(RespStatusDTO.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .build(), data);
    }

    public static <T> GeneralResponse<T> error(HttpStatus status, String message, T data) {
        return new GeneralResponse<>(RespStatusDTO.builder()
                .code(status.value())
                .message(message)
                .build(), data);
    }
}
