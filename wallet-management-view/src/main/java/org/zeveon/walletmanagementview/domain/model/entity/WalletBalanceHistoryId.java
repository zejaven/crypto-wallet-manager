package org.zeveon.walletmanagementview.domain.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * @author Stanislav Vafin
 */
@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class WalletBalanceHistoryId implements Serializable {

    @Column(name = "wallet_id", nullable = false)
    private String walletId;

    @Column(name = "update_time", nullable = false)
    private ZonedDateTime updateTime;
}
