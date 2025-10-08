package se.g56.workshop.workshop.datajpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString(exclude = "writtenBooks")
@RequiredArgsConstructor()

public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="author_id", updatable = false,  nullable = false)
    @Setter(AccessLevel.NONE)
    private Integer id;

    @Column(name="first_name",  nullable = false, length = 100)
    @Setter(AccessLevel.PACKAGE)
    @NotBlank
    @NonNull
    private String firstName;

    @Column(name="last_name",  nullable = false, length = 100)
    @Setter(AccessLevel.PACKAGE)
    @NotBlank
    @NonNull
    private String lastName;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private Set<Book> writtenBooks = new HashSet<>();

    public void addBook(Book b) {
        if (writtenBooks.add(b)) b.getAuthors().add(this);
    }
    public void removeBook(Book b) {
        if (writtenBooks.remove(b)) b.getAuthors().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (org.hibernate.Hibernate.getClass(this) != org.hibernate.Hibernate.getClass(o)) return false;
        Author author = (Author) o;
        return id != null && id.equals(author.id);
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
