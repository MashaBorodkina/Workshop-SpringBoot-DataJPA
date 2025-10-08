package se.g56.workshop.workshop.datajpa.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.g56.workshop.workshop.datajpa.entity.Details;
import se.g56.workshop.workshop.datajpa.repo.DetailsRepository;
import se.g56.workshop.workshop.datajpa.service.DetailsService;

import java.time.LocalDate;

@Configuration

public class DataInitializer {
    @Bean
    CommandLineRunner run(DetailsRepository detailsRepository, DetailsService detailsService) {
        return (args) -> {
            seedData(detailsRepository);
            createPersonAndSendEmail(detailsService, true);
        };
    }
    private void seedData(DetailsRepository repo) {
        Details d = new Details("test@gmail.com", "Test Testov", LocalDate.of(2001, 1, 1));
        repo.save(d);
    }
    private void  createPersonAndSendEmail(DetailsService detailsService, boolean sendEmail) {
        if(sendEmail){
            detailsService.createDetails(new Details("maria.borodkina1507@gmail.com", "Maria", LocalDate.of(1982, 7, 15)));
        }
    }
}
