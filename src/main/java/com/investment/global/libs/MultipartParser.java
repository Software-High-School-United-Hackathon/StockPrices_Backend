package com.investment.global.libs;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MultipartParser {

    public MultipartFile changeBase64ToMultipartFile(String base64) {

        String[] base64Array = base64.split(",");
        String dataUir = "data:image/jpg;base64";
        String data = base64Array[0];

        return new Base64ToMultipartFile(data, dataUir);
    }
}
