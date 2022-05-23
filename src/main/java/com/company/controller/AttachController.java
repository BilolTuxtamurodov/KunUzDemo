package com.company.controller;

import com.company.enums.ProfileRole;
import com.company.service.AttachService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/attach")
public class AttachController {
    @Autowired
    private AttachService attachService;

    @PostMapping("/upload")
    public ResponseEntity<?> create (@RequestParam("file")MultipartFile file){
        return ResponseEntity.ok(attachService.upload(file));
    }

    @GetMapping(value = "/open/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open(@PathVariable("fileName") String fileName){
        return attachService.open(fileName);
    }

    @GetMapping(value = "/open_general/{fileName}", produces = MediaType.ALL_VALUE)
    public byte[] open_general(@PathVariable("fileName") String fileName){
        return attachService.open_general(fileName);
    }

    @GetMapping("/download/{fileName}")
    public Resource download(@PathVariable("fileName") String fileName) {
        return attachService.download(fileName);
    }

    @GetMapping("/adm/list")
    public ResponseEntity<?> getList(@RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "5") int size,
                                     HttpServletRequest request){
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(attachService.getList(page, size));
    }

    @GetMapping("/adm/get{id}")
    public ResponseEntity<?> getById(@PathVariable("id")String id,
                                     HttpServletRequest request){
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(attachService.getById(id));
    }

    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")String id,
                                    HttpServletRequest request){
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(attachService.delete(id));
    }




}