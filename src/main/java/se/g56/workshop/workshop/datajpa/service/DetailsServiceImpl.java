package se.g56.workshop.workshop.datajpa.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.g56.workshop.workshop.datajpa.entity.Details;
import se.g56.workshop.workshop.datajpa.repo.DetailsRepository;


@Slf4j
@Service
public class DetailsServiceImpl implements DetailsService {
    private DetailsRepository detailsRepository;
    private EmailService emailService;

    public DetailsServiceImpl(DetailsRepository detailsRepository, EmailService emailService) {
        this.detailsRepository = detailsRepository;
        this.emailService = emailService;
    }

    @Override
    public Details  createDetails(Details details) {
        Details saved = detailsRepository.save(details);

        if (saved.getId() != null) {
            try {
                emailService.sendTextMail(details.getEmail(),
                        "Welcome to App!",
                        "Hello %s!".formatted(details.getName()));
                log.info("Successfully to send welcome email to: {}", details.getEmail());
            } catch (org.springframework.mail.MailException e) {
                log.error("Failed to send welcome email to {}: {}", details.getEmail(), e.getMessage(), e);
            }
        }
        return saved;
    }
}
