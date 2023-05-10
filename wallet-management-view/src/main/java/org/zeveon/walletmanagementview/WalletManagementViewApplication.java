package org.zeveon.walletmanagementview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {
        "org.zeveon.walletmanagementview.domain.model.entity",
        "org.axonframework.eventhandling.tokenstore.jpa"
})
public class WalletManagementViewApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletManagementViewApplication.class, args);
    }

}
