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
}