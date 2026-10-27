package com.example.certificates.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class IssueCertificateRequest {
    @NotBlank(message = "Recipient name is required")
    @Size(max = 120, message = "Recipient name must be at most 120 characters")
    private String recipientName;

    @NotBlank(message = "Course or certificate title is required")
    @Size(max = 160, message = "Course title must be at most 160 characters")
    private String courseName;

    @NotBlank(message = "Organization name is required")
    @Size(max = 160, message = "Organization name must be at most 160 characters")
    private String organizationName;

    public String getRecipientName() { return recipientName; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getOrganizationName() { return organizationName; }
    public void setOrganizationName(String organizationName) { this.organizationName = organizationName; }
}
