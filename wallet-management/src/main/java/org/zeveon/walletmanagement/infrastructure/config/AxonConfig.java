package org.zeveon.walletmanagement.infrastructure.config;

import com.thoughtworks.xstream.XStream;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.zeveon.common.model.event.BaseEvent;

/**
 * @author Stanislav Vafin
 */
@Configuration
public class AxonConfig {

    @Bean
    @Primary
    public Serializer serializer() {
        var xStream = new XStream();
        xStream.allowTypeHierarchy(BaseEvent.class);
        return XStreamSerializer.builder()
                .xStream(xStream)
                .build();
    }
}
