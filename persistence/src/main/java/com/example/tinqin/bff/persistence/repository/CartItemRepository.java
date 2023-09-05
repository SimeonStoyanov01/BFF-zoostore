package com.example.tinqin.bff.persistence.repository;

import com.example.tinqin.bff.persistence.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, UUID> {
    Optional<CartItemEntity> findCartItemEntityByItemIdAndUserId(UUID itemId,UUID userId);
    Set<CartItemEntity> findAllByUserId(UUID userId);

    void deleteCartItemEntityByItemIdAndUserId(UUID itemId, UUID userId);
}
