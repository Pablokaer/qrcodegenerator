package com.example.qrcode.controller;

import com.example.qrcode.entity.QrCode;
import com.example.qrcode.service.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class QrViewController {

    @Autowired
    private QrCodeService service;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/generate")
    public String generate(@RequestParam String content, RedirectAttributes attrs) {
        if (content == null || content.isBlank()) {
            attrs.addFlashAttribute("error", "Content cannot be empty.");
            return "redirect:/";
        }
        try {
            QrCode qr = service.create(content);
            return "redirect:/view/" + qr.getId();
        } catch (Exception e) {
            attrs.addFlashAttribute("error", "Failed to generate QR Code: " + e.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        Optional<QrCode> qr = service.findById(id);
        if (qr.isEmpty()) {
            return "redirect:/";
        }
        model.addAttribute("qr", qr.get());
        return "view";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attrs) {
        service.delete(id);
        attrs.addFlashAttribute("success", "QR Code deleted successfully.");
        return "redirect:/";
    }
}
