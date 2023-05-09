package org.zeveon.walletmanagementview.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zeveon.walletmanagementview.domain.model.entity.WalletBalanceHistory;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Stanislav Vafin
 */
@Repository
public interface WalletBalanceHistoryRepository extends JpaRepository<WalletBalanceHistory, Long> {

    List<WalletBalanceHistory> findAllByWalletIdAndUpdateTimeBetweenOrderByUpdateTimeAsc(
            String walletId,
            ZonedDateTime startDatetime,
            ZonedDateTime endDatetime
    );
}
