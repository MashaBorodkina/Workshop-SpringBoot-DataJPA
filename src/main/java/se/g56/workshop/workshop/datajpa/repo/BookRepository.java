package se.g56.workshop.workshop.datajpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.g56.workshop.workshop.datajpa.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Integer> {
    Optional<Book> findByIsbnIgnoreCase(String isbn);
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByMaxLoanDaysLessThan(int maxLoanDays);
}
