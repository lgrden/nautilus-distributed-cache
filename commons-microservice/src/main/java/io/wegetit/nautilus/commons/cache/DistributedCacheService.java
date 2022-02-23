package io.wegetit.nautilus.commons.cache;

import com.hazelcast.config.EntryListenerConfig;
import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.MapEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
public abstract class DistributedCacheService<T, K> implements EntryListener<T, K> {

    private final HazelcastInstance hazelcastInstance;

    public abstract String getCacheName();
    public abstract boolean isInitOnStartup();

    @PostConstruct
    protected void initialize() {
        if (hazelcastInstance.getConfig().getMapConfigOrNull(getCacheName()) == null) {
            throw new IllegalStateException("Cache "+ getCacheName() + " not defined.");
        }
        log.info("Initializing cache {} on startup is {}.", getCacheName(), isInitOnStartup());
        Map<T, K> map = hazelcastInstance.getMap(getCacheName());
        if (isInitOnStartup()) {
            map.clear();
            List<K> data = findAll();
            map.putAll(data.stream().collect(Collectors.toMap(this::key, Function.identity())));
            log.info("Cache {} reloaded with {} elements.", getCacheName(), data.size());
        }
        hazelcastInstance.getConfig().getMapConfig(getCacheName()).addEntryListenerConfig(new EntryListenerConfig(this, false, true));
    }

    @PreDestroy
    protected void destroy() {
        hazelcastInstance.getConfig().getMapConfig(getCacheName()).setEntryListenerConfigs(Collections.emptyList());
    }

    protected abstract List<K> findAll();
    protected abstract T key(K k);


    @Override
    public void entryAdded(EntryEvent<T, K> entryEvent) {
        log.info("Cache {} with key {} added.", getCacheName(), entryEvent.getKey());
    }

    @Override
    public void entryEvicted(EntryEvent<T, K> entryEvent) {
        log.info("Cache {} with key {} evicted.", getCacheName(), entryEvent.getKey());
    }

    @Override
    public void entryExpired(EntryEvent<T, K> entryEvent) {
        log.info("Cache {} with key {} expired.", getCacheName(), entryEvent.getKey());
    }

    @Override
    public void entryRemoved(EntryEvent<T, K> entryEvent) {
        log.info("Cache {} with key {} removed.", getCacheName(), entryEvent.getKey());
    }

    @Override
    public void entryUpdated(EntryEvent<T, K> entryEvent) {
        log.info("Cache {} with key {} updated.", getCacheName(), entryEvent.getKey());
    }

    @Override
    public void mapCleared(MapEvent mapEvent) {
        log.info("Cache {} cleared.", getCacheName());
    }

    @Override
    public void mapEvicted(MapEvent mapEvent) {
        log.info("Cache {} evicted.", getCacheName());
    }
}
