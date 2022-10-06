package com.investment.domain.finance.domain.repository;

import com.investment.domain.finance.domain.entity.FinanceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceInfoRepository extends JpaRepository<FinanceInfo, Long> {
}
