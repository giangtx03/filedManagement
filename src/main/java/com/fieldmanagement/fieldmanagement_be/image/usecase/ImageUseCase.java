package com.fieldmanagement.fieldmanagement_be.image.usecase;

import com.fieldmanagement.fieldmanagement_be.common.port.FileService;
import com.fieldmanagement.fieldmanagement_be.image.domain.model.Image;
import com.fieldmanagement.fieldmanagement_be.image.domain.port.ImageRepository;
import com.fieldmanagement.fieldmanagement_be.image.exception.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

@Service
@RequiredArgsConstructor
public class ImageUseCase {
    private final FileService fileService;
    private final ImageRepository imageRepository;

    public Resource getImageFile(String pathImg) throws MalformedURLException, FileNotFoundException {
        Image image = imageRepository.findByPath(pathImg)
                .orElseThrow(() -> new ImageNotFoundException("Image not found"));
        return fileService.download(image.getPath());
    }

}
