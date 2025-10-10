package se.g56.workshop.workshop.datajpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.g56.workshop.workshop.datajpa.entity.Author;
import se.g56.workshop.workshop.datajpa.entity.Book;
import se.g56.workshop.workshop.datajpa.repo.AuthorRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor

public class AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional()
    public void deleteAuthorById(Integer authorId) {
        Author author = authorRepository.findById(authorId).orElse(null);

        Set<Book> books = new HashSet<>(author.getWrittenBooks());

        for (Book book : books) {
            book.removeAuthor(author);
        }
        authorRepository.delete(author);
    }
}
