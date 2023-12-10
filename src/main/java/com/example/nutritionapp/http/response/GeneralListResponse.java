package com.example.nutritionapp.http.response;

import com.example.nutritionapp.dto.RespStatusDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneralListResponse<T> {
    private RespStatusDTO status;
    private List<T> data;

    public static <T> GeneralListResponse<T> success(List<T> data) {
        return new GeneralListResponse<>(RespStatusDTO.builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .build(), data);
    }

    public static <T> GeneralListResponse<T> error(String message, List<T> data) {
        return new GeneralListResponse<>(RespStatusDTO.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .build(), data);
    }

    public static <T> GeneralListResponse<T> error(HttpStatus status, String message, List<T> data) {
        return new GeneralListResponse<>(RespStatusDTO.builder()
                .code(status.value())
                .message(message)
                .build(), data);
    }
}
