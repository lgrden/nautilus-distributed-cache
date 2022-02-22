package io.wegetit.nautilus.commons.cache;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ComponentScan("io.wegetit.nautilus.commons.cache")
public class DistributedCacheConfiguration {

    @Value("cache.instanceName")
    private String cacheInstanceName;

    @Bean
    public HazelcastInstance hazelcastInstance() {
        Config config = new ClasspathXmlConfig("cache.xml").setInstanceName(cacheInstanceName);
        return Hazelcast.getOrCreateHazelcastInstance(config);
    }
}
