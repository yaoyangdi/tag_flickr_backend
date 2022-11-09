package com.example.tagflickr.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.tagflickr.common.ApiResponse;
import com.example.tagflickr.common.CloudinarySingleton;
import com.example.tagflickr.dto.ImageDto;
import com.example.tagflickr.dto.ImageResponseDto;
import com.example.tagflickr.model.Image;
import com.example.tagflickr.model.Tag;
import com.example.tagflickr.repository.ImageRepository;
import com.example.tagflickr.service.image.ImageService;
import com.example.tagflickr.service.tag.TagService;
import org.cloudinary.json.JSONObject;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/img")
@CrossOrigin
public class ImageController {
    @Autowired
    ImageService imageService;

    @Autowired
    TagService tagService;

    /**
     * Get all images
     */
    @GetMapping
    public List<ImageResponseDto> allImages(){
        return imageService.getAll();
    }

    @PostMapping
    public ResponseEntity<ApiResponse> newImg(ImageDto imageDto) {
        try {
            Image image = null;

            if (imageDto.getImage() != null) {
                File uploadedFile = convertMultiPartToFile(imageDto.getImage());
                Cloudinary cloudinary = CloudinarySingleton.getInstance().getCloudinary();
                Map uploadResult = cloudinary.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
                String imag_url = new JSONObject(uploadResult).getString("url");
                image = new Image(imageDto.getTitle(), imag_url);
                imageService.addImage(image);
                if(imageDto.getTags().contains(",")){
                    ArrayList<String> tagsArr = new ArrayList<String>(Arrays.asList(imageDto.getTags().split(",")));
                    for(int i =0; i<tagsArr.size(); i++) {
                        if(tagsArr.get(i) != "") {  // Avoid empty string
                            tagService.addTag(tagsArr.get(i), image);
                        }
                    }
                } else{
                    tagService.addTag(imageDto.getTags(), image);
                }

            } else {
                return new ResponseEntity<>(new ApiResponse(false, "not url generated"), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(true,tagService.getImagesByTagName(imageDto.getTags()) ), HttpStatus.CREATED);
    }

    /**
     * Delete all images
     */
    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteAll() {
        try{
            imageService.deleteAllImages();
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(true, "Delete successfully"), HttpStatus.ACCEPTED);
    }

    /**
     * Convert Multipart data(Image file) to File data
     */
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
    }
