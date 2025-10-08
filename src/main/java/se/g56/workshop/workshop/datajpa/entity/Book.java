package se.g56.workshop.workshop.datajpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@ToString(exclude = "authors")
@NoArgsConstructor(access = PROTECTED)
@RequiredArgsConstructor()

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name="book_author",
    joinColumns = @JoinColumn(name="book_id", foreignKey = @ForeignKey(name = "fk_ba_book")),
    inverseJoinColumns = @JoinColumn(name="author_id", foreignKey = @ForeignKey(name="fk_ba_author")),
    uniqueConstraints = @UniqueConstraint(name = "uk_book_author", columnNames = {"book_id", "author_id"}))
    private Set<Author> authors = new HashSet<>();

    public void addAuthor(Author a) {
        if (authors.add(a)) a.getWrittenBooks().add(this);
    }
    public void removeAuthor(Author a) {
        if (authors.remove(a)) a.getWrittenBooks().remove(this);
    }

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Attachment> attachments = new ArrayList<>();

    public void addAttachments(Attachment a) {
        attachments.add(a);
        a.setBook(this);
    }
    public void removeAttachments(Attachment a) {
        attachments.remove(a);
        a.setBook(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (org.hibernate.Hibernate.getClass(this) != org.hibernate.Hibernate.getClass(o)) return false;
        Book book = (Book) o;
        return id != null && id.equals(book.id);
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
