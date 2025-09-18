package se.g56.workshop.workshop.datajpa.repo;

import org.springframework.data.repository.CrudRepository;
import se.g56.workshop.workshop.datajpa.entity.Details;

import java.util.List;
import java.util.Optional;

public interface DetailsRepository extends CrudRepository<Details, Long> {

    Optional<Details> findByEmail(String email);
    List<Details> findByNameContainingIgnoreCase(String name);
    List<Details> findByNameIgnoreCase(String name);
}
