package org.zeveon.walletmanagementview;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {
        "org.zeveon.walletmanagementview.domain.model.entity",
        "org.axonframework.eventhandling.tokenstore.jpa"
})
@OpenAPIDefinition(
        info = @Info(title = "Wallet Management API", version = "1.0"),
        servers = @Server(url = "/")
)
public class WalletManagementViewApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletManagementViewApplication.class, args);
    }
}
