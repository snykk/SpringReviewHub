package com.example.springreviewhub.core.interfaces.services;

/**
 * Interface for Mailer Service.
 * <p>
 * This interface defines the contract for sending email notifications within the application.
 * It provides a method to compose and dispatch emails to recipients with specified content.
 * </p>
 */
public interface IMailerService {

    /**
     * Sends an email to the specified recipient.
     * <p>
     * This method allows sending an email by specifying the recipient's email address,
     * the subject of the email, and the body content. It can be used for various purposes
     * such as notifications, account verification, or password recovery.
     * </p>
     *
     * @param to      the recipient's email address
     * @param subject the subject of the email
     * @param body    the body content of the email
     */
    void sendEmail(String to, String subject, String body);
}
