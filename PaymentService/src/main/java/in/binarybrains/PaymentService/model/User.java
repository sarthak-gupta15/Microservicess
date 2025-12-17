package in.binarybrains.PaymentService.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false, length = 100)
    private String name;

//    @Column(nullable = false, unique = true, length = 150)
    private String email;

//    @Column(nullable = false)
    private String password;

//    @Column(length = 20)
    private String phone;

//    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal balance;

//    @Column(nullable = false)
    private Boolean isActive;

//    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

//    @PrePersist
//    protected void onCreate() {
//        this.createdAt = LocalDateTime.now();
//        this.updatedAt = LocalDateTime.now();
//        if (balance == null) balance = BigDecimal.ZERO;
//        if (isActive == null) isActive = true;
//    }

//    @PreUpdate
//    protected void onUpdate() {
//        this.updatedAt = LocalDateTime.now();
//    }

    // Getters and Setters
}
