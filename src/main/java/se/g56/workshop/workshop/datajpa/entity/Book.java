package se.g56.workshop.workshop.datajpa.entity;

import jakarta.persistence.*;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
@RequiredArgsConstructor(access = PROTECTED)

public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name="book_id", nullable = false, updatable = false)
    private Integer id;

    @Column(name="isbn", nullable = false, unique = true, length = 17)
    @NonNull
    @Setter(AccessLevel.NONE)
    private String isbn;

    @Column(name="book_title", length = 255, nullable = false)
    @NonNull
    @Setter
    private String title;

    @Column(name="max_loan_days", nullable = false)
    @NonNull
    @Setter
    private int maxLoanDays;
}
