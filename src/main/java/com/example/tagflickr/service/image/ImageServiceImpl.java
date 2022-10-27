package com.example.tagflickr.service.image;

import com.example.tagflickr.model.Image;
import com.example.tagflickr.repository.ImageRepository;
import com.example.tagflickr.service.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    ImageRepository imageRepository;


    /**
     * Create an image entity
     */
    @Override
    public void addImage(Image image) {
        // save the image
        imageRepository.save(image);
    }

    /**
     * Get all image entities
     */
    @Override
    public List<Image> getAll() {
        return imageRepository.findAll();
    }

    /**
     * Delete all image entities
     */
    @Override
    public void deleteAllImages() {
        imageRepository.deleteAll();
    }
}
