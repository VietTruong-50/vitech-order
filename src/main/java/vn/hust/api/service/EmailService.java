package vn.hust.api.service;

public interface EmailService {
    void sendEmail(String to, String subject, String content);
}
