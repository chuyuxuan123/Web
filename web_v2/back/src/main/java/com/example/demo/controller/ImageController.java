package com.example.demo.controller;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
@CrossOrigin("http://localhost:3000")
@RequestMapping("/image")
public class ImageController {

    @GetMapping(value = "/img/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImage(@PathVariable("id") String id) throws IOException {
        //TODO: pic url is fixed
        File file = new File("G:\\Web\\proj\\web_v2\\back\\src\\main\\resources\\img\\" + id);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }

//    @PostMapping

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }

        String fileName = file.getOriginalFilename();
//        System.out.println(fileName);
        String filePath = "G:\\Web\\proj\\web_v2\\back\\src\\main\\resources\\img\\";
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            return "上传成功";
        } catch (IOException e) {
        }
        return "上传失败！";
    }


}
