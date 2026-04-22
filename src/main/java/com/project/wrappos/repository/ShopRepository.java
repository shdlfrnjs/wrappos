package com.project.wrappos.repository;

import com.project.wrappos.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    boolean existsByUserId(String userId);

    boolean existsByBizNo(String bizNo);

    Optional<Shop> findByUserId(String userId);

}