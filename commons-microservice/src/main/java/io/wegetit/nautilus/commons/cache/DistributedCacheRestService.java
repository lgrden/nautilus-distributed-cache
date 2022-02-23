package io.wegetit.nautilus.commons.cache;

import com.hazelcast.core.HazelcastInstance;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RequestMapping("/api/cache")
@RestController
public class DistributedCacheRestService {

    private final HazelcastInstance hazelcastInstance;

    @GetMapping
    public List<DistributedCacheInfo> info() {
        return hazelcastInstance.getConfig().getMapConfigs().keySet().stream()
            .map(p -> DistributedCacheInfo.builder().name(p).elements(hazelcastInstance.getMap(p).size()).build())
            .sorted(Comparator.comparing(DistributedCacheInfo::getName))
            .collect(Collectors.toList());
    }
}
