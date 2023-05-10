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
@Table(name = "wallet_balance_history", schema = "wallet_management")
public class WalletBalanceHistory {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wallet_id", nullable = false)
    private String walletId;

    @Column(name = "update_time", nullable = false)
    private ZonedDateTime updateTime;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;
}
