package se.g56.workshop.workshop.datajpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.g56.workshop.workshop.datajpa.entity.Author;

import java.util.List;


public interface AuthorRepository extends JpaRepository<Author, Integer> {
    List<Author> findByFirstNameIgnoreCase(String firstName);
    List<Author> findByLastNameIgnoreCase(String lastName);
    List<Author> findByFirstNameContainingOrLastNameContaining(String fName, String lName);
    List<Author> findByWrittenBooks_Id(Integer bookId);
    void deleteById(Integer id);


    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Author a set a.firstName=:first, a.lastName=:last where a.id=:id")
    int updateAuthorById(@Param("first") String first, @Param("last") String last, @Param("id") Integer id);
}
