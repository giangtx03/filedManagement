package com.fieldmanagement.fieldmanagement_be.image.adapter.web.controller;

import com.fieldmanagement.fieldmanagement_be.image.usecase.ImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/${api.prefix}/images")
public class ImageController {
    private final ImageUseCase imageUseCase;

    @GetMapping("/{pathImg}")
    public ResponseEntity<?> getImage(@PathVariable("pathImg") String pathImg)
            throws MalformedURLException, FileNotFoundException
    {
        Resource source = imageUseCase.getImageFile(pathImg);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(source);
    }

}
