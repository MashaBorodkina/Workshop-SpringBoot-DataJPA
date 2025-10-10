package se.g56.workshop.workshop.datajpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import se.g56.workshop.workshop.datajpa.entity.Author;
import se.g56.workshop.workshop.datajpa.entity.Book;
import se.g56.workshop.workshop.datajpa.repo.AuthorRepository;
import se.g56.workshop.workshop.datajpa.repo.BookRepository;
import se.g56.workshop.workshop.datajpa.service.AuthorService;

import java.util.List;


import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(AuthorService.class)

public class AuthorRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorService authorService;

    @Test
    @DisplayName("Find by first name ignore case")
    void findByFirstNameIgnoreCase() {
        Author author1 = authorRepository.save(new Author("Name", "Lastname"));
        Author author2 = authorRepository.save(new Author("Name", "Laaaastname"));

       List<Author> result = authorRepository.findByFirstNameIgnoreCase("name");

       assertThat(result)
               .extracting(Author::getLastName)
               .containsExactlyInAnyOrder("Lastname", "Laaaastname");
    }
    @Test
    @DisplayName("Find by last name ignore case")
    void findByLastNameIgnoreCase() {
        Author author1 = authorRepository.save(new Author("Namefirst", "Lastname"));
        Author author2 = authorRepository.save(new Author("Namesecond", "Laaaastname"));

        List<Author> result = authorRepository.findByLastNameIgnoreCase("lastname");

        assertThat(result)
                .extracting(Author::getFirstName)
                .containsExactly("Namefirst");
    }

    @Test
    @DisplayName("Find by first name or last name containing part")
    void findByFirstNameContainingOrLastNameContaining(){
        Author author1 = authorRepository.save(new Author("Namefirst", "Lastname"));
        Author author2 = authorRepository.save(new Author("Namesecond", "Lastenname"));

        List<Author> result = authorRepository.findByFirstNameContainingOrLastNameContaining("sec", "ten");

        assertThat(result)
                .extracting(Author::getFirstName)
                .containsExactly("Namesecond");
    }
    @Test
    @DisplayName("Find author by books_Id")
    void  findByWrittenBooks_Id() {
        Book book1 = bookRepository.save(new Book("Book 1", "title1", 10));
        Book book2 = bookRepository.save(new Book("Book 2", "title2", 10));


        Author author1 = authorRepository.save(new Author("Namefirst", "Lastnamefirst"));
        Author author2 = authorRepository.save(new Author("Namesecond", "Lastnamesecond"));

        author1.addBook(book1);
        author1.addBook(book2);
        author2.addBook(book1);

        List<Author>result = authorRepository.findByWrittenBooks_Id(book1.getId());

        assertThat(result)
                .extracting(Author::getFirstName)
                .containsExactlyInAnyOrder(author1.getFirstName(), author2.getFirstName());

        assertThat(result)
                .hasSize(2);
    }

    @Test
    @DisplayName("Delete by ID")
    void deleteById() {
        Author author1 = authorRepository.save(new Author("Namefirst", "Lastnamefirst"));
        Author author2 = authorRepository.save(new Author("Namesecond", "Lastnamesecond"));

        Book book1 = bookRepository.save(new Book("Book 1", "title1", 10));
        Book book2 = bookRepository.save(new Book("Book 2", "title2", 10));

        author1.addBook(book1);
        author1.addBook(book2);
        author2.addBook(book1);

        authorService.deleteAuthorById(author1.getId());

        assertThat(authorRepository.findById(author1.getId())).isNotPresent();
        assertThat(authorRepository.findById(author2.getId())).isPresent();
        assertThat(bookRepository.findById(book1.getId())).isPresent();
        assertThat(bookRepository.findById(book2.getId())).isPresent();
        assertThat(authorRepository.findByWrittenBooks_Id(book1.getId())).isNotEmpty();
        assertThat(authorRepository.findByWrittenBooks_Id(book2.getId())).isEmpty();
    }
}
