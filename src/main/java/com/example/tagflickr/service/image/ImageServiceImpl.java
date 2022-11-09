package com.example.tagflickr.service.image;

import com.example.tagflickr.dto.ImageDto;
import com.example.tagflickr.dto.ImageResponseDto;
import com.example.tagflickr.model.Image;
import com.example.tagflickr.model.Tag;
import com.example.tagflickr.repository.ImageRepository;
import com.example.tagflickr.service.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
     * Get all image entities with tags in list of string format
     */
    @Override
    public List<ImageResponseDto> getAll() {
        List<ImageResponseDto> imageDtoList = new ArrayList<>();
        List<Image> imageList = imageRepository.findAll();
        for (int i = 0; i<imageList.size(); i++) {
            Image image = imageList.get(i);      // Each element in the image list
            ImageResponseDto imageResponseDto = new ImageResponseDto(image.getTitle(),new ArrayList<>(), image.getUrl());   // Initialize a new ImageResponseDto object
            List<Tag> tagList = image.getTags();

            for(int j = 0; j<tagList.size(); j++) {
                Tag tag = tagList.get(j);
                imageResponseDto.getTags().add(tag.getName());
            }

            imageDtoList.add(imageResponseDto);
        }
        return imageDtoList;
    }

    /**
     * Delete all image entities
     */
    @Override
    public void deleteAllImages() {
        imageRepository.deleteAll();
    }
}
