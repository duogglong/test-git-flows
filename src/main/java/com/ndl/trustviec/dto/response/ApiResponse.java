package com.ndl.trustviec.dto.response;

import com.ndl.trustviec.common.constants.ApiStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@JsonInclude(Include.NON_EMPTY)
@JsonPropertyOrder({"status", "message", "data"})
public class ApiResponse<T> {
    @JsonProperty("status")
    private String status;

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("errors")
    private List<Map<String, String>> errors;

    @JsonProperty("data")
    private T data;


    public ApiResponse() {
    }

    public static <T> ApiResponse<T> ok(T data) {
        ApiResponse<T> res = new ApiResponse<>();
        res.status = ApiStatus.OK;
        res.data = data;
        return res;
    }

    public static <T> ApiResponse<T> ok() {
        ApiResponse<T> res = new ApiResponse<>();
        res.status = ApiStatus.OK;
        return res;
    }


    public static <T> ApiResponse<T> failed(String code, String message) {
        ApiResponse<T> res = new ApiResponse<>();
        res.status = ApiStatus.FAILED;
        res.code = code;
        res.message = message;
        return res;
    }

    public static <T> ApiResponse<T> failed(String message) {
        ApiResponse<T> res = new ApiResponse<>();
        res.status = ApiStatus.FAILED;
        res.message = message;
        return res;
    }

    public static <T> ApiResponse<T> failed(String code, String message, List<Map<String, String>> errors) {
        ApiResponse<T> res = new ApiResponse<>();
        res.status = ApiStatus.FAILED;
        res.code = code;
        res.message = message;
        res.errors = errors;
        return res;
    }

    public static <T> ApiResponse<T> failed(int code, String messages) {
        ApiResponse<T> ret = new ApiResponse<>();
        ret.status = ApiStatus.FAILED;
        ret.code = String.valueOf(code);
        ret.message = messages;
        return ret;
    }

    public static <T> ApiResponse<T> unAuthentication(String message) {
        ApiResponse<T> res = new ApiResponse<>();
        res.status = HttpStatus.UNAUTHORIZED.getReasonPhrase().toUpperCase();
        res.message = message;
        return res;
    }

    public static <T> ApiResponse<T> unAuthorization(String message) {
        ApiResponse<T> res = new ApiResponse<>();
        res.status = HttpStatus.FORBIDDEN.getReasonPhrase().toUpperCase();
        res.message = message;
        return res;
    }
}
