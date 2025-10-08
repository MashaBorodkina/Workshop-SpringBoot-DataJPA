package se.g56.workshop.workshop.datajpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.Locale;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
@RequiredArgsConstructor


public class Details {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false,  length = 254, unique = true)
    @Email
    @NotBlank
    @NonNull
    private String email;
    public void setEmail(@Email String email){
        this.email=(email == null) ? null : email.trim().toLowerCase(Locale.ROOT);
    }

    @Column(nullable = false,  length = 100)
    @Setter
    @NotBlank
    @NonNull
    private String name;

    @Column(nullable = false)
    @Setter
    @NonNull
    private LocalDate birthDate;

    public static Details of(String email, String name, LocalDate birthDate) {
        Details d =  new Details();
        d.setEmail(email);
        d.setName(name);
        d.setBirthDate(birthDate);
        return d;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (org.hibernate.Hibernate.getClass(this) != org.hibernate.Hibernate.getClass(o)) return false;
        Details details = (Details) o;
        return id != null && id.equals(details.id);
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}