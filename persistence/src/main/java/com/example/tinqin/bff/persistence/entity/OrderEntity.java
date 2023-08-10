package com.example.tinqin.bff.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;

    @CreationTimestamp(source = SourceType.DB)
    @Column(nullable = false, updatable = false,insertable = false)
    private Timestamp timeOfSale;

    private UUID userId;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @OneToMany
    @JoinColumn(name = "userId")
    private Set<CartItemEntity> cartItemEntities = new HashSet<>();

    private BigDecimal bill;

}
