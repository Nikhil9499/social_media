package com.nikhil.social_media.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {
    Boolean success;
    String version = "V1";
    String error;
    T data;

    public ResponseDto(T data) {
        this.success = true;
        this.data = data;
    }

    public ResponseDto(Boolean success, T data) {
        this(data);
        this.success = success;
    }

    public ResponseDto(T data, Boolean success) {
        this(data);
        this.success = success;
    }

    public ResponseDto(Boolean success, String errorMessage, T data) {
        this(data, success);
        this.error = errorMessage;
    }
}
