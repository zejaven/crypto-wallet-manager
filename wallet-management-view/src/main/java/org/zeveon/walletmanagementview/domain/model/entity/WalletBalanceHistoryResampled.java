package org.zeveon.walletmanagementview.domain.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wallet_balance_history_resampled", schema = "wallet_management")
public class WalletBalanceHistoryResampled {

    @EmbeddedId
    private WalletBalanceHistoryId id;

    @Column(name = "balance", nullable = false, columnDefinition = "NUMERIC(38,8)")
    private BigDecimal balance;
}
