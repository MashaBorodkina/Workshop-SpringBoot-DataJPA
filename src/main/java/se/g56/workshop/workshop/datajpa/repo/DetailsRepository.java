package se.g56.workshop.workshop.datajpa.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import se.g56.workshop.workshop.datajpa.entity.Details;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DetailsRepository extends CrudRepository<Details, Long> {

    Optional<Details> findByEmail(String email);
    List<Details> findByNameContainingIgnoreCase(String name);
    List<Details> findByNameIgnoreCase(String name);
    List<Details> findByBirthDateBetween(LocalDate start, LocalDate end);

    @Query("SELECT d FROM Details d WHERE MONTH(d.birthDate)=:month")
    List<Details> findByMonth(@Param("month") int month);
}
