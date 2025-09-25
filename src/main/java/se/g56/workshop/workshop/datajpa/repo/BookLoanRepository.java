package se.g56.workshop.workshop.datajpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Meta;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.g56.workshop.workshop.datajpa.entity.BookLoan;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookLoanRepository extends JpaRepository<BookLoan,Integer> {
    List<BookLoan> findByBorrowerId(Long borrowerId);
    List<BookLoan> findByBookId(Integer bookId);
    List<BookLoan> findByReturnedFalse();
    List<BookLoan> findByReturnedFalseAndDueDateBefore(LocalDate date);
    List<BookLoan> findAllByLoanDateBetween(LocalDate start, LocalDate end);

    @Modifying(flushAutomatically = true)
    @Query("update BookLoan bl set bl.returned=true where bl.id=:id and bl.returned=false")
    int markReturnedById(@Param("id") Integer id);
}
