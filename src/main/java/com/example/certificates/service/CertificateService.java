package com.example.certificates.service;

import com.example.certificates.dto.IssueCertificateRequest;
import com.example.certificates.entity.Certificate;
import com.example.certificates.exception.CertificateNotFoundException;
import com.example.certificates.exception.DuplicateCertificateException;
import com.example.certificates.repository.CertificateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CertificateService {
    private final CertificateRepository repository;

    public CertificateService(CertificateRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Certificate issueCertificate(IssueCertificateRequest request) {
        String recipient = request.getRecipientName().trim();
        String course = request.getCourseName().trim();
        String organization = request.getOrganizationName().trim();

        boolean alreadyIssued = repository
                .existsByRecipientNameIgnoreCaseAndCourseNameIgnoreCaseAndOrganizationNameIgnoreCaseAndStatus(
                        recipient, course, organization, "ACTIVE");
        if (alreadyIssued) {
            throw new DuplicateCertificateException(
                    "An active certificate for this recipient, course and organization already exists.");
        }

        String certificateId = createUniqueId();
        Certificate certificate = new Certificate(certificateId, recipient, course,
                organization, LocalDate.now(), "ACTIVE");
        return repository.save(certificate);
    }

    private String createUniqueId() {
        String id;
        do {
            id = "CERT-" + UUID.randomUUID().toString().replace("-", "")
                    .substring(0, 12).toUpperCase();
        } while (repository.existsById(id));
        return id;
    }

    public Certificate verifyCertificate(String certificateId) {
        if (certificateId == null || certificateId.trim().isEmpty()) {
            throw new IllegalArgumentException("Certificate ID must not be empty.");
        }
        return repository.findByCertificateIdIgnoreCase(certificateId.trim())
                .orElseThrow(() -> new CertificateNotFoundException(
                        "No certificate was found for ID: " + certificateId.trim()));
    }

    public List<Certificate> getAllCertificates() {
        return repository.findAllByOrderByIssueDateDesc();
    }

    @Transactional
    public Certificate revokeCertificate(String certificateId) {
        Certificate certificate = verifyCertificate(certificateId);
        if ("REVOKED".equals(certificate.getStatus())) {
            throw new IllegalArgumentException("This certificate has already been revoked.");
        }
        certificate.setStatus("REVOKED");
        return repository.save(certificate);
    }
}
