package com.example.certificates.exception;

public class CertificateNotFoundException extends RuntimeException {
    public CertificateNotFoundException(String message) { super(message); }
}
