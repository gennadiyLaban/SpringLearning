package org.laban.learning.spring.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.laban.learning.spring.web.validation.multipart.MultipartFileNotEmpty;
import org.springframework.web.multipart.MultipartFile;

@Data @NoArgsConstructor
public class UploadBookFile {
    @MultipartFileNotEmpty(message = "File must not be empty")
    private MultipartFile file;
}
