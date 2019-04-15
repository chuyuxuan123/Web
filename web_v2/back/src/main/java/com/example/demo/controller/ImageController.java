package com.example.demo.controller;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@CrossOrigin("http://localhost:3000")
@RequestMapping("/image")
public class ImageController {

    @GetMapping(value = "/img/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable("id") String id) throws IOException{
        File file = new File("G:\\Web\\proj\\web_v2\\back\\src\\main\\resources\\img\\"+id);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes,0,inputStream.available());
        return bytes;
    }
}
