package com.example.tagflickr.service.tag;

import com.example.tagflickr.dto.ImageResponseDto;
import com.example.tagflickr.dto.TagResponseDto;
import com.example.tagflickr.model.Image;
import com.example.tagflickr.model.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {
    void addTag(String name, Image image);

    List<TagResponseDto> getAll();

    List<ImageResponseDto> getImagesByTagName(String name);

    void deleteAllTags();
}
