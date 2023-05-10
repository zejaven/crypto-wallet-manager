package org.zeveon.walletmanagementview.infrastructure.config;

import com.thoughtworks.xstream.XStream;
import lombok.RequiredArgsConstructor;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.zeveon.common.model.event.BaseEvent;

import java.util.List;

/**
 * @author Stanislav Vafin
 */
@Configuration
@RequiredArgsConstructor
public class AxonConfig {

    private static final List<String> ALLOWED_PACKAGES = List.of(
            "org.zeveon.walletmanagementview.domain.model.**",
            "org.zeveon.walletmanagementview.domain.query.**"
    );

    @Bean
    @Primary
    public Serializer serializer() {
        var xStream = new XStream();
        xStream.allowTypeHierarchy(BaseEvent.class);
        xStream.allowTypesByWildcard(ALLOWED_PACKAGES.toArray(new String[0]));
        return XStreamSerializer.builder()
                .xStream(xStream)
                .build();
    }
}
