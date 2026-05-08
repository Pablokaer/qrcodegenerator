package com.example.qrcode.service;

import com.example.qrcode.entity.QrCode;
import com.example.qrcode.repository.QrCodeRepository;
import com.example.qrcode.util.QrCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QrCodeService {

    @Autowired
    private QrCodeRepository repository;

    public QrCode create(String content) {
        byte[] image = QrCodeGenerator.generate(content);

        QrCode qr = new QrCode();
        qr.setCode(content);
        qr.setUrl("data:image/png;base64," + java.util.Base64.getEncoder().encodeToString(image));

        return repository.save(qr);
    }

    public Optional<QrCode> findById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
