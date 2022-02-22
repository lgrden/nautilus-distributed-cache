package io.wegetit.nautilus.commons.cache;

import com.hazelcast.core.HazelcastInstance;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
public abstract class DistributedCacheService<T, K> {

    private final HazelcastInstance hazelcastInstance;

    public abstract String getCacheName();
    public abstract boolean isInitOnStartup();

    @PostConstruct
    protected void initialize() {
        if (!isInitOnStartup()) {
            log.info("{} service. Init cache {} is disabled.", getClass().getSimpleName(), getCacheName());
            return;
        }
        if (hazelcastInstance.getConfig().getMapConfigOrNull(getCacheName()) == null) {
            throw new IllegalStateException("Cache "+ getCacheName() + " not defined.");
        }
        Map<T, K> map = hazelcastInstance.getMap(getCacheName());
        map.clear();
        List<K> data = findAll();
        map.putAll(data.stream().collect(Collectors.toMap(this::key, Function.identity())));
        log.info("{} service. Cache {} reloaded with {} elements.", getClass().getSimpleName(), getCacheName(), data.size());
    }

    protected abstract List<K> findAll();
    protected abstract T key(K k);
}
