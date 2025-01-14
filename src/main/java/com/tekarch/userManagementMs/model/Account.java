package com.tekarch.userManagementMs.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId; // Primary key

    @ManyToOne(fetch = FetchType.LAZY) // Many accounts belong to one user
    @JoinColumn(name = "user_id", nullable = false) // Foreign key to User
    @JsonBackReference // Marking this side of the relationship to avoid serialization

    private User user;

    @Column(name = "account_number", nullable = false, unique = true, length = 20)
    private String accountNumber;

    @Column(name = "account_type", nullable = false, length = 20)
    private String accountType; // e.g., "Savings", "Checking"

    @Column(name = "balance", precision = 15, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO; // Default balance is 0.0

    @Column(name = "currency", length = 10, nullable = false)
    private String currency = "USD"; // Default currency is USD

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}

