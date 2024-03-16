package com.sql.authentication.payload.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Response <T>{

        private int status;
        private String message;
        private T data;


        public Response(int status, String message, T data) {
            this.status = status;
            this.message = message;
            this.data = data;
        }

        public static <T> Response<T> success(String message, T data) {
            return new Response<>(HttpStatus.OK.value(), message, data);
        }

        public static <T> Response<T> error(String message) {
            return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
        }


}
