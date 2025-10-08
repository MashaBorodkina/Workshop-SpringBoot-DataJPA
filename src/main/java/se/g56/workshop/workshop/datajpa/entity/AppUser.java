package se.g56.workshop.workshop.datajpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@ToString (exclude = {"password", "userDetails", "loans"})
@NoArgsConstructor(access = PROTECTED)
@RequiredArgsConstructor

public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id", nullable = false, updatable = false)
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

    @OneToOne (cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, unique = true, name="details_id")
    @NonNull
    private Details userDetails;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "borrower")
    private List<BookLoan> loans = new ArrayList<>();

    @PrePersist
    private void onCreate(){
        this.regDate = LocalDate.now();
    }

    public void addLoan(BookLoan loan){
        if(loan==null) throw new NullPointerException("Loan cannot be null");
        if(loan.getBorrower() != null && loan.getBorrower()!=this) throw new IllegalArgumentException("Loan already assigned to another user");
        if(!loans.contains(loan)) loans.add(loan);
        loan.setBorrower(this);
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (org.hibernate.Hibernate.getClass(this) != org.hibernate.Hibernate.getClass(o)) return false;
        AppUser other = (AppUser) o;
        return id != null && id.equals(other.id);
        }

    @Override
    public int hashCode() {
        return getClass().hashCode();
        }
    }


