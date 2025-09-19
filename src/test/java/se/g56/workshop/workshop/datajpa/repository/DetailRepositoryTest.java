package se.g56.workshop.workshop.datajpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.g56.workshop.workshop.datajpa.entity.AppUser;
import se.g56.workshop.workshop.datajpa.entity.Details;
import se.g56.workshop.workshop.datajpa.repo.DetailsRepository;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class DetailRepositoryTest {
    @Autowired
    private DetailsRepository detailsRepository;

    @Test
    @DisplayName("find Maria by email")
    void findByEmail() {
        Details details = new Details("exampe@email.se", "Maria", LocalDate.of(1995, 1, 1));

        Details savedDetails = detailsRepository.save(details);

        Optional<Details> foundDetails = detailsRepository.findByEmail(savedDetails.getEmail());

        assertThat(foundDetails.isPresent()).isTrue();
        assertThat(foundDetails.get().getEmail()).isEqualTo(savedDetails.getEmail());
    }
    @Test
    @DisplayName("find Maria by name contain")
    void findByNameContain() {
        detailsRepository.save(new Details("maria@email.se", "Maria", LocalDate.of(1995,1,1)));
        detailsRepository.save(new Details("mark@email.se",  "Mark",  LocalDate.of(2000,2,2)));

        List<Details> found = detailsRepository.findByNameContainingIgnoreCase("Ria");
        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0).getEmail()).isEqualTo("maria@email.se");
    }
    @Test
    @DisplayName("find Maria by name ignore case")
    void findByNameIgnoreCase() {
        detailsRepository.save(new Details("maria@email.se", "Maria", LocalDate.of(1995,1,1)));
        List<Details> found = detailsRepository.findByNameIgnoreCase("MARIA");
        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0).getName()).isEqualTo("Maria");
    }
    @Test
    @DisplayName("find Maria by birth date")
    void findByBirthDate() {
        detailsRepository.save(new Details("maria@email.se", "Maria", LocalDate.of(1995,1,1)));
        List<Details> found = detailsRepository.findByBirthDateBetween(LocalDate.of(1994,12,1), LocalDate.of(2000,2,2));
        assertThat(found.size()).isEqualTo(1);
    }
    @Test
    @DisplayName("find Maria by Month birthday")
    void findByMonthBirthday() {
        detailsRepository.save(new Details("maria@email.se", "Maria", LocalDate.of(1995,1,1)));
        List<Details> found = detailsRepository.findByMonth(1);
        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0).getName().equals("Maria"));
    }
}
