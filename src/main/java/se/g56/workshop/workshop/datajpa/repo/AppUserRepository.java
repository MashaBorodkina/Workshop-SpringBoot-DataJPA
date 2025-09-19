package se.g56.workshop.workshop.datajpa.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import se.g56.workshop.workshop.datajpa.entity.AppUser;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser,Long> {
    List<AppUser> findByUsername(String username);
    Optional<AppUser> findByUserDetails_Id(Long detailsId);//first way
    Optional<AppUser>findByUserDetailsEmailIgnoreCase(String email);//first way


    @Query("SELECT a FROM AppUser a WHERE a.regDate>:start AND a.regDate<:end")
    List<AppUser> findByDate(@Param("start") LocalDate start, @Param("end") LocalDate end);

    //@Query("SELECT a FROM AppUser a WHERE a.userDetails.id=:detailId")
    //Optional<AppUser> findByDetailsId(@Param("detailId") Long detailId);//second way

    //@Query("SELECT a FROM AppUser a JOIN a.details d WHERE lower(d.email)= lower(:email)")
    //Optional<AppUser> findByDetailsEmail(@Param("email") String email);//second way


}
