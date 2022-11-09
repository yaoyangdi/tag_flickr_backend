package com.example.tagflickr.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Image entities DTO with tags in list of string format
 */
@Data
public class ImageResponseDto {
    private String title;
    private List<String> tags;
    private String imgUrl;

    public ImageResponseDto(String title, List<String> tags, String imgUrl) {
        this.title = title;
        this.tags = tags;
        this.imgUrl = imgUrl;
    }
}
