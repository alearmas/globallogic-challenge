package com.aarmas.globallogic.entities;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {
    private List<ErrorDetail> error;

    public ErrorResponse(List<ErrorDetail> error) {
        this.error = error;
    }

    public List<ErrorDetail> getError() {
        return error;
    }

    public static class ErrorDetail {
        private LocalDateTime timestamp;
        private int code;
        private String detail;

        public ErrorDetail(LocalDateTime timestamp, int codigo, String detail) {
            this.timestamp = timestamp;
            this.code = codigo;
            this.detail = detail;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public int getCode() {
            return code;
        }

        public String getDetail() {
            return detail;
        }
    }
}
