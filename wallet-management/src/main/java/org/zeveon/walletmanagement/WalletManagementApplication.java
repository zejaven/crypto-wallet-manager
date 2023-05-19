package org.zeveon.walletmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"org.axonframework.eventhandling.tokenstore.jpa"})
public class WalletManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletManagementApplication.class, args);
    }

}
