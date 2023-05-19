package org.zeveon.walletmanagementview.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zeveon.walletmanagementview.domain.model.entity.WalletBalanceHistory;

/**
 * @author Stanislav Vafin
 */
@Repository
public interface WalletBalanceHistoryRepository extends JpaRepository<WalletBalanceHistory, Long> {
}
