package com.mysite.sbb.mail;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
@Controller
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/email")
    public String showEmailForm(Model model) {
        model.addAttribute("emailRequest", new EmailRequest());
        return "email/email"; 
    }

    @PostMapping("/send-email")
    public String sendEmail(
    		@ModelAttribute EmailRequest emailRequest,
    		@RequestParam("file") MultipartFile file,
    		Model model
    		) {
    	try {
            // Call the sendEmail method of the EmailService to send the email.
            // You can access emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText(), and the file here.
            emailService.sendEmailWithAttachment(
                emailRequest.getTo(),
                emailRequest.getSubject(),
                emailRequest.getText(),
                file
            );
            model.addAttribute("to", emailRequest.getTo());

            // 이메일 성공시
            return "/email/mailsuccess";
        } catch (Exception e) {
            // Handle any exceptions here (e.g., validation errors, email sending errors)
            // 이메일 전송실패시
            return "/email/mailerror";
        }
    }
    @GetMapping("/email/mailsuccess")
    public String showSuccessPage() {
        return "mailsuccess"; 
    }
    @GetMapping("/email/mailerror")
    public String showErrorPage() {
    	return "mailerror"; 
    }
}
@Data
class EmailRequest {
    private String to;
    private String subject;
    private String text;

    // getters and setters...
}