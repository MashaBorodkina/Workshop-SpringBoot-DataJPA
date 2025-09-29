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
        Details details1 =  new Details("email@email.se", "Name", LocalDate.of(1990, 1, 1));
        Book book1 = bookRepository.save(new Book("isbn12345678", "title", 10));
        AppUser user1 = appUserRepository.save(new AppUser("un", "password",details1));


        Book book2 = bookRepository.save(new Book("isbn012345679", "title2", 10));


        BookLoan l1 = bookLoanRepository.save(BookLoan.issue(user1, book1, LocalDate.of(1990, 1, 1)));
        BookLoan l2 = bookLoanRepository.save(BookLoan.issue(user1, book2, LocalDate.of(1990, 1, 1)));

        var result = bookLoanRepository.findByBorrowerId(l1.getBorrower().getId());

        assertThat(result)
                .extracting(BookLoan::getId)
                .containsExactlyInAnyOrder(l1.getId(), l2.getId());
    }

    @Test
    @DisplayName("Find by book ID")
    void findByBookID() {
        Details details = new Details("email@email.se", "Name", LocalDate.of(1990, 1, 1));
        Book book = bookRepository.save(new Book("isbn12345678", "title", 10));
        AppUser user = appUserRepository.save(new AppUser("un", "password",details));

        BookLoan l = bookLoanRepository.save(BookLoan.issue(user, book, LocalDate.of(1990, 1, 1)));

        var result = bookLoanRepository.findByBookId(l.getBook().getId());

        assertThat(result)
                .extracting(BookLoan::getId)
                .containsExactly(l.getId());
    }

    @Test
    @DisplayName("Find by returned false")

    void findByReturnedFalse() {
        Details details1 = new Details("email@email.se", "Name", LocalDate.of(1990, 1, 1));
        Book book1 = bookRepository.save(new Book("isbn12345678", "title", 10));
        AppUser user1 = appUserRepository.save(new AppUser("un", "password", details1));

        Book book2 = bookRepository.save(new Book("isbn012345679", "title2", 10));


        BookLoan l1 = bookLoanRepository.save(BookLoan.issue(user1, book1, LocalDate.of(1990, 1, 1)));
        BookLoan l2 = bookLoanRepository.save(BookLoan.issue(user1, book2, LocalDate.of(1990, 1, 1)));

        l1.markReturned();
        bookLoanRepository.flush();

        var result = bookLoanRepository.findByReturnedFalse();

        assertThat(result)
                .size()
                .isEqualTo(1);

        assertThat(result)
                .extracting(BookLoan::getId)
                .containsExactly(l2.getId());
    }
    @Test
    @DisplayName("Find by returned false and due date before date")
    void findByReturnedFalseAndDueDateBefore(){
        Details details1 = new Details("email@email.se", "Name", LocalDate.of(1990, 1, 1));
        Book book1 = bookRepository.save(new Book("isbn12345678", "title", 10));
        AppUser user1 = appUserRepository.save(new AppUser("un", "password", details1));

        Book book2 = bookRepository.save(new Book("isbn012345679", "title2", 10));


        BookLoan l1 = bookLoanRepository.save(BookLoan.issue(user1, book1,LocalDate.of(2025, 9, 18)));
        BookLoan l2 = bookLoanRepository.save(BookLoan.issue(user1, book2, LocalDate.of(2025, 9, 25)));

        var result = bookLoanRepository.findByReturnedFalseAndDueDateBefore(LocalDate.of(2025, 9, 29));

        assertThat(result)
                .extracting(BookLoan::getId)
                .containsExactly(l1.getId());
        assertThat(result)
                .extracting(BookLoan::getId)
                .size()
                .isEqualTo(1);
    }

    @Test
    @DisplayName("Find all by loan date between dates")
    void findAllByLoanDateBetween(){
        Details details1 = new Details("email@email.se", "Name", LocalDate.of(1990, 1, 1));
        Book book1 = bookRepository.save(new Book("isbn12345678", "title", 10));
        AppUser user1 = appUserRepository.save(new AppUser("un", "password", details1));

        Book book2 = bookRepository.save(new Book("isbn012345679", "title2", 10));


        BookLoan l1 = bookLoanRepository.save(BookLoan.issue(user1, book1,LocalDate.of(2025, 9, 18)));
        BookLoan l2 = bookLoanRepository.save(BookLoan.issue(user1, book2, LocalDate.of(2025, 9, 25)));

        var result = bookLoanRepository.findAllByLoanDateBetween(LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 29));

        assertThat(result)
                .hasSize(2);
        assertThat(result)
                .extracting(BookLoan::getId)
                .containsExactlyInAnyOrder(l1.getId(), l2.getId());
    }

    @Test
    @DisplayName("mark returned by Id")
    void markReturnedById(){
        Details details1 = new Details("email@email.se", "Name", LocalDate.of(1990, 1, 1));
        Book book1 = bookRepository.save(new Book("isbn12345678", "title", 10));
        AppUser user1 = appUserRepository.save(new AppUser("un", "password", details1));

        Book book2 = bookRepository.save(new Book("isbn012345679", "title2", 10));


        BookLoan l1 = bookLoanRepository.save(BookLoan.issue(user1, book1,LocalDate.of(2025, 9, 18)));
        BookLoan l2 = bookLoanRepository.save(BookLoan.issue(user1, book2, LocalDate.of(2025, 9, 25)));

        assertThat(bookLoanRepository.markReturnedById(l1.getId()))
                .isEqualTo(1);
        assertThat(bookLoanRepository.findByReturnedFalse()).doesNotContain(l1);
    }
}
