package com.example.tagflickr.service.tag;

import com.example.tagflickr.dto.ImageResponseDto;
import com.example.tagflickr.dto.TagResponseDto;
import com.example.tagflickr.model.Image;
import com.example.tagflickr.model.Tag;
import com.example.tagflickr.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TagServiceImpl implements TagService{
    @Autowired
    TagRepository tagRepository;

    /**
     * Create a tag entity
     */
    @Override
    public void addTag(String name, Image image) {
        Tag tag = tagRepository.findByName(name);
        // Check if there already exist the tag  (tag name in db is unique)
        if(Objects.isNull((tag))){
            // save the tag only if the tag is not yet existing in db
            Tag new_tag = new Tag(name, image);
            tagRepository.save(new_tag);
        } else {
            tag.addImage(image); // already exist then update the image list in the tag entity
            tagRepository.save(tag);
        }
    }

    /**
     * Get all tags
     */
    @Override
    public List<TagResponseDto> getAll() {
        List<Tag> all = tagRepository.findAll();
        List<TagResponseDto> tagResponseDtoList = new ArrayList<>();
        /*  Convert Tag entity into Tag entity Dto  */
        for(int i=0; i<all.size(); i++){
            Tag tag = all.get(i);
            TagResponseDto tagResponseDto = new TagResponseDto(tag.getName());
            tagResponseDtoList.add(tagResponseDto);
        }
        return tagResponseDtoList;
    }

    /**
     * Get all selected images by the input tag name(s)
     */
    @Override
    public List<ImageResponseDto> getImagesByTagName(String name) {
        Set<Image> images = new HashSet<>(); // Use set for adding distinct images

        if (name.contains(",")) {            // Split the input tag names in list format only if the input contains comma character
            List<String> tags = Arrays.asList(name.split(","));

            /*  Search related images for each tag  */
            for(int i = 0; i<tags.size(); i++) {
                Tag tag = tagRepository.findByName(tags.get(i));

                /*  Check each tag's existence  */
                if(Objects.nonNull((tag))){                             // nonNull(searchedTag) means tag exists
                    Object[] toArray = tag.getImages().toArray();
                    for(int j=0; j<toArray.length; j++){
                        images.add((Image) toArray[j]);
                    }
                }
            }
        } else {   // Otherwise we do not need to convert to list format
            Tag tag = tagRepository.findByName(name);
            if(Objects.nonNull((tag))){
                Object[] toArray = tag.getImages().toArray();
                for(int j=0; j<toArray.length; j++){
                    images.add((Image) toArray[j]);
                }
            }
        }

        /*  Convert each Image entity into Image entity Dto  */
        List<ImageResponseDto> imageResponseDtoList = new ArrayList<>();
        Object[] imagesList = images.toArray();

        for(int j=0; j<imagesList.length; j++) {
            /*  Initialization of entity and entity Dto  */
            Image image = (Image) imagesList[j];
            ImageResponseDto imageResponseDto = new ImageResponseDto(image.getTitle(), new ArrayList<>(), image.getUrl());

            /*  format Tag entity into String  */
            List<Tag> tagList = image.getTags();
            for (int k = 0; k < tagList.size(); k++) {
                Tag tag = tagList.get(k);
                imageResponseDto.getTags().add(tag.getName());
            }

            imageResponseDtoList.add(imageResponseDto);
        }

        return imageResponseDtoList;
    }

    /**
     * Delete all tags
     */
    @Override
    public void deleteAllTags() {
        tagRepository.deleteAll();
    }

}
