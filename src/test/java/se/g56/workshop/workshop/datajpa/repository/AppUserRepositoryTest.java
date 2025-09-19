package se.g56.workshop.workshop.datajpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import se.g56.workshop.workshop.datajpa.entity.AppUser;
import se.g56.workshop.workshop.datajpa.entity.Details;
import se.g56.workshop.workshop.datajpa.repo.AppUserRepository;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository appUserRepository;


    @Test
    @DisplayName("Save user")
    void saveUser(){
        Details details = new Details("exampe@email.se", "Maria", LocalDate.of(1995, 1, 1));
        AppUser appUser = new AppUser("mariboro", "password", details);

        AppUser savedappUser = appUserRepository.save(appUser);

        assertThat(savedappUser.getId()).isNotNull();
    }
    @Test
    @DisplayName("Find Maria by Details_Id")
    void testFindMariaByDetails_Id(){
        Details details = new Details("exampe@email.se", "Maria", LocalDate.of(1995, 1, 1));
        AppUser appUser = new AppUser("mariboro", "password", details);

        AppUser savedappUser = appUserRepository.save(appUser);

        Optional<AppUser> result = appUserRepository.findByUserDetails_Id(savedappUser.getUserDetails().getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result).get()
                .extracting(AppUser::getUsername)
                .isEqualTo("mariboro");
    }
    @Test
    @DisplayName("Find Maria by username")
    void testFindMariaByUsername(){
        Details details = new Details("exampe@email.se", "Maria", LocalDate.of(1995, 1, 1));
        AppUser appUser = new AppUser("mariboro", "password", details);

        appUserRepository.save(appUser);
        List<AppUser> result = appUserRepository.findByUsername("mariboro");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result).singleElement().satisfies(u -> {
            assertThat(u.getId()).isNotNull();
            assertThat(u.getUsername()).isEqualTo("mariboro");
            assertThat(u.getUserDetails().getEmail()).isEqualTo("exampe@email.se");
        });
    }
    @Test
    @DisplayName("Find Maria by email")
    void testFindMariaByEmail(){
        Details details = new Details("exampe@email.se", "Maria", LocalDate.of(1995, 1, 1));
        AppUser appUser = new AppUser("mariboro", "password", details);

        appUserRepository.save(appUser);
        Optional<AppUser> result = appUserRepository.findByUserDetailsEmailIgnoreCase("ExAmPe@EMAIL.SE");

        assertThat(result.isPresent()).isTrue();
        assertThat(result).hasValueSatisfying(u -> {
            assertThat(u.getId()).isNotNull();
            assertThat(u.getUsername()).isEqualTo("mariboro");
            assertThat(u.getUserDetails().getEmail()).isEqualTo("exampe@email.se");
        });
    }
    @Test
    @DisplayName("Find Maria by regdata")
    void testFindMariaByRegData(){
        Details details = new Details("exampe@email.se", "Maria", LocalDate.of(1995, 1, 1));
        AppUser appUser = new AppUser("mariboro", "password", details);

        AppUser savedappUser = appUserRepository.save(appUser);

        List<AppUser> result = appUserRepository.findByDate(LocalDate.of(2025, 9, 17), LocalDate.of(2025, 9,20));
        assertThat(result.size()).isEqualTo(1);
        assertThat(result).singleElement().satisfies(u -> {
            assertThat(u.getId()).isNotNull();
            assertThat(u.getUsername()).isEqualTo("mariboro");
        });
    }
    @Test
    @DisplayName("existsByUsername returns true for existing username")
    void testExistsByUsername(){
        Details details = new Details("exampe@email.se", "Maria", LocalDate.of(1995, 1, 1));
        AppUser appUser = new AppUser("mariboro", "password", details);
        appUserRepository.save(appUser);

        boolean result = appUserRepository.existsByUsername("mariboro");
        assertThat(result).isTrue();
    }
    @Test
    @DisplayName("existsByUsername returns false for non-existing username")
    void testExistsByUsername_false(){
        boolean result = appUserRepository.existsByUsername("mariboro");
        assertThat(result).isFalse();
    }
}
