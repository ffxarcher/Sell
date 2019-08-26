package com.sq.sell.repository;

import com.sq.sell.entity.SecondKill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecondKillRepository extends JpaRepository<SecondKill,String> {
        SecondKill findByProductId(String id);
}
