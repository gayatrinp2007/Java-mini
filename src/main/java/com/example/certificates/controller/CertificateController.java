package com.example.certificates.controller;

import com.example.certificates.dto.ApiMessage;
import com.example.certificates.dto.IssueCertificateRequest;
import com.example.certificates.entity.Certificate;
import com.example.certificates.service.CertificateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {
    private final CertificateService service;

    public CertificateController(CertificateService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Certificate> issueCertificate(@Valid @RequestBody IssueCertificateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.issueCertificate(request));
    }

    @GetMapping("/verify/{certificateId}")
    public ResponseEntity<?> verifyCertificate(@PathVariable String certificateId) {
        Certificate certificate = service.verifyCertificate(certificateId);
        if ("REVOKED".equals(certificate.getStatus())) {
            return ResponseEntity.ok(new VerificationResponse(false,
                    "Certificate exists, but it has been revoked.", certificate));
        }
        return ResponseEntity.ok(new VerificationResponse(true,
                "Certificate is authentic and active.", certificate));
    }

    @GetMapping
    public List<Certificate> getAllCertificates() {
        return service.getAllCertificates();
    }

    @PatchMapping("/{certificateId}/revoke")
    public ResponseEntity<Certificate> revokeCertificate(@PathVariable String certificateId) {
        return ResponseEntity.ok(service.revokeCertificate(certificateId));
    }

    public static class VerificationResponse {
        private boolean valid;
        private String message;
        private Certificate certificate;

        public VerificationResponse() { }
        public VerificationResponse(boolean valid, String message, Certificate certificate) {
            this.valid = valid;
            this.message = message;
            this.certificate = certificate;
        }
        public boolean isValid() { return valid; }
        public void setValid(boolean valid) { this.valid = valid; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public Certificate getCertificate() { return certificate; }
        public void setCertificate(Certificate certificate) { this.certificate = certificate; }
    }
}
