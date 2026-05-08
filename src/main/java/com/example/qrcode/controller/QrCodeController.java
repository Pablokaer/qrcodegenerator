package com.example.qrcode.controller;

import com.example.qrcode.dto.QrRequestDto;
import com.example.qrcode.entity.QrCode;
import com.example.qrcode.service.QrCodeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("/qr")
public class QrCodeController {

    @Autowired
    private QrCodeService service;

    @PostMapping
    public ResponseEntity<Long> generate(@RequestBody QrRequestDto request) {
        QrCode qr = service.create(request.getContent());
        return ResponseEntity.ok(qr.getId());
    }

    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getQr(@PathVariable Long id) {
        Optional<QrCode> qr = service.findById(id);

        if (qr.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        String base64 = qr.get().getUrl().replace("data:image/png;base64,", "");
        byte[] image = Base64.getDecoder().decode(base64);
        return ResponseEntity.ok(image);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
