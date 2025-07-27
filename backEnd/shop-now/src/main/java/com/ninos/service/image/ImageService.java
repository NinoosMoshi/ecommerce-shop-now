package com.ninos.service.image;

import com.ninos.dto.ImageDTO;
import com.ninos.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    Image getImageById(Long imageId);
    void deleteImageById(Long imageId);
    void updateImage(MultipartFile file, Long imageId);
    List<ImageDTO> saveImages(Long productId, List<MultipartFile> files);

}
