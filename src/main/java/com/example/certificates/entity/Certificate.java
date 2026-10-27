package com.example.certificates.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "certificates")
public class Certificate {
    @Id
    @Column(name = "certificate_id", nullable = false, unique = true, length = 40)
    private String certificateId;

    @Column(nullable = false, length = 120)
    private String recipientName;

    @Column(nullable = false, length = 160)
    private String courseName;

    @Column(nullable = false, length = 160)
    private String organizationName;

    @Column(nullable = false)
    private LocalDate issueDate;

    @Column(nullable = false, length = 20)
    private String status;

    public Certificate() { }

    public Certificate(String certificateId, String recipientName, String courseName,
                       String organizationName, LocalDate issueDate, String status) {
        this.certificateId = certificateId;
        this.recipientName = recipientName;
        this.courseName = courseName;
        this.organizationName = organizationName;
        this.issueDate = issueDate;
        this.status = status;
    }

    public String getCertificateId() { return certificateId; }
    public void setCertificateId(String certificateId) { this.certificateId = certificateId; }
    public String getRecipientName() { return recipientName; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getOrganizationName() { return organizationName; }
    public void setOrganizationName(String organizationName) { this.organizationName = organizationName; }
    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
