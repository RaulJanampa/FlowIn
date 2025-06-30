package org.example.flowin2.web.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/music")
public class MusicController {

    private final String s3BaseUrl = "https://flowin2-music-bucket.s3.amazonaws.com/";

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Void> getSong(@PathVariable String filename) {
        String fileUrl = s3BaseUrl + filename;

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(java.net.URI.create(fileUrl));

        return new ResponseEntity<>(headers, HttpStatus.FOUND); // 302 redirect
    }
}
