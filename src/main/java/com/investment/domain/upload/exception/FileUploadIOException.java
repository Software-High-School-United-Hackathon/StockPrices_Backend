package com.investment.domain.upload.exception;

import com.investment.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class FileUploadIOException extends CustomException {

    public FileUploadIOException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 중 서버 오류가 발생했습니다.");
    }
}
