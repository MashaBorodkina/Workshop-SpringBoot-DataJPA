package se.g56.workshop.workshop.datajpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@ToString (exclude = {"password", "userDetails"})
@NoArgsConstructor(access = PROTECTED)
@RequiredArgsConstructor

public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    @Setter
    @NotBlank
    @NonNull
    private String username;

    @Column(nullable = false, length = 100)
    @Setter
    @NotBlank
    @NonNull
    private String password;

    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDate regDate;

    @OneToOne (cascade = CascadeType.ALL, optional = false)
    @JoinColumn(nullable = false, unique = true, name="details_id")
    @NonNull
    Details userDetails;

    @PrePersist
    private void onCreate(){
        this.regDate = LocalDate.now();
    }

}
