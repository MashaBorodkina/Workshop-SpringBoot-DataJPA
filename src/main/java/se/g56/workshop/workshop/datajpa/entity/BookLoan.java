package se.g56.workshop.workshop.datajpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString(exclude = {"borrower","book"})

public class BookLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bookloan_id", nullable = false, updatable = false)
    private Integer id;

    @Column(name="loan_date", nullable = false, updatable = false)
    private LocalDate loanDate;

    @Column(name="due_date", nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private boolean returned;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id", nullable = false)
    @Setter(AccessLevel.PACKAGE)
    private AppUser borrower;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="book_id", nullable = false)
    private Book book;

    public static BookLoan issue(AppUser borrower, Book book, LocalDate when){
        BookLoan loan = new BookLoan();
        loan.borrower = Objects.requireNonNull(borrower);
        loan.book = Objects.requireNonNull(book);
        loan.loanDate = (when != null ? when : LocalDate.now());
        loan.dueDate = loan.loanDate.plusDays(book.getMaxLoanDays());
        return loan;
    }
    public void markReturned() { this.returned = true; }

    public void extendDueDate(int extraDays) {
        if (extraDays <= 0) throw new IllegalArgumentException("extraDays must be > 0");
        this.dueDate = this.dueDate.plusDays(extraDays);
    }
}
