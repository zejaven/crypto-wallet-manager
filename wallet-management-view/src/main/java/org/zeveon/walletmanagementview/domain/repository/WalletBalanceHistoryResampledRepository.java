package org.zeveon.walletmanagementview.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zeveon.walletmanagementview.domain.model.entity.WalletBalanceHistoryId;
import org.zeveon.walletmanagementview.domain.model.entity.WalletBalanceHistoryResampled;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Stanislav Vafin
 */
@Repository
public interface WalletBalanceHistoryResampledRepository extends JpaRepository<WalletBalanceHistoryResampled, WalletBalanceHistoryId> {

    List<WalletBalanceHistoryResampled> findAllById_WalletIdAndId_UpdateTimeBetweenOrderById_UpdateTimeAsc(
            String walletId,
            ZonedDateTime startDatetime,
            ZonedDateTime endDatetime
    );
}
