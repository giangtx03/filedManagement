package com.fieldmanagement.commom.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

@UtilityClass
public class FileUtil {
    public boolean validImage(MultipartFile file){
        return true;
    }
}
