package com.example.certificates.repository;

import com.example.certificates.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate, String> {
    Optional<Certificate> findByCertificateIdIgnoreCase(String certificateId);
    boolean existsByRecipientNameIgnoreCaseAndCourseNameIgnoreCaseAndOrganizationNameIgnoreCaseAndStatus(
            String recipientName, String courseName, String organizationName, String status);
    List<Certificate> findAllByOrderByIssueDateDesc();
}
