package se.g56.workshop.workshop.datajpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@ToString(exclude = "writtenBooks")

public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="author_id", updatable = false,  nullable = false)
    @Setter(AccessLevel.NONE)
    private Integer id;

    @Column(name="first_name",  nullable = false, length = 100)
    @Setter
    private String firstName;

    @Column(name="last_name",  nullable = false, length = 100)
    @Setter
    private String lastName;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private Set<Book> writtenBooks = new HashSet<>();
}
