package com.example.tagflickr.dto;

import com.example.tagflickr.model.Image;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class TagResponseDto {

    private String name;

    public TagResponseDto(String name) {
        this.name = name;
    }
}
