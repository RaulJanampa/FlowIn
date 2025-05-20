package com.project.FlowIn.Sala;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sala")
public class SalaController {
    @Autowired
    private SalaService salaService;

    @PostMapping()
    public ResponseEntity<SalaResponse> sala(@RequestBody @Validated SalaRequest salaRequest) {
        SalaResponse salaResponse = salaService.save(salaRequest);
        return ResponseEntity.ok(salaResponse);
    }
}