package se.g56.workshop.workshop.datajpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.g56.workshop.workshop.datajpa.entity.AppUser;
import se.g56.workshop.workshop.datajpa.entity.Book;
import se.g56.workshop.workshop.datajpa.entity.BookLoan;
import se.g56.workshop.workshop.datajpa.entity.Details;
import se.g56.workshop.workshop.datajpa.repo.AppUserRepository;
import se.g56.workshop.workshop.datajpa.repo.BookLoanRepository;
import se.g56.workshop.workshop.datajpa.repo.BookRepository;


import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest

public class BookLoanReporitoryTest {
    @Autowired
    private BookLoanRepository bookLoanRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    @DisplayName("Find by borrower ID")
    void findByBorrowerID() {
        Details details =  new Details("email@email.se", "Name", LocalDate.of(1990, 1, 1));
        Book book = bookRepository.save(new Book("isbn12345678", "title", 10));
        AppUser user = appUserRepository.save(new AppUser("un", "password",details));

        BookLoan l = bookLoanRepository.save(BookLoan.issue(user, book, LocalDate.of(1990, 1, 1)));

        var result = bookLoanRepository.findByBorrowerId(l.getBorrower().getId());

        assertThat(result)
                .extracting(BookLoan::getId)
                .containsExactly(l.getId());
    }
}
