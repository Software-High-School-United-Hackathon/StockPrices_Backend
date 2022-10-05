package com.investment.domain.upload.exception;

import com.investment.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class FileUploadConvertException extends CustomException {

    public FileUploadConvertException() {
        super(HttpStatus.BAD_REQUEST, "파일 변환에 실패했습니다.");
    }
}
