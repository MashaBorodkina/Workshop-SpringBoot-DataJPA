package se.g56.workshop.workshop.datajpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.g56.workshop.workshop.datajpa.entity.Book;
import se.g56.workshop.workshop.datajpa.repo.BookRepository;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest

public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Find by Isbn case ignore")
    void testFindByIsbnCaseIgnore(){
        Book book = new Book("ISBN12345678", "title", 10);
        Book savedBook = bookRepository.save(book);

        assertThat(bookRepository.findByIsbnIgnoreCase("isbn12345678"))
                .contains(savedBook);
    }

    @Test
    @DisplayName("Find by title ignore case")
    void testFindByTitleCaseIgnore(){
        Book book = new Book("ISBN12345678", "title", 10);
        Book savedBook = bookRepository.save(book);

        assertThat(bookRepository.findByTitleContainingIgnoreCase("TItle").contains(savedBook));
    }

    @Test
    @DisplayName("Find by max loan days less 12")
    void testFindByMaxLoanDaysLessThan12(){
        Book book = bookRepository.save(new Book("ISBN12345678", "title", 10));
        Book book2 = bookRepository.save (new Book("ISBN12345679", "title", 12));
        Book book3 = bookRepository.save(new Book("ISBN12345680", "title", 13));

        var result = bookRepository.findByMaxLoanDaysLessThan(12);

        assertThat(result)
                .extracting(Book::getIsbn)
                .containsExactly(book.getIsbn());
    }
}
