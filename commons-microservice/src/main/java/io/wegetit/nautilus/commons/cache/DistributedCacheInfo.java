package io.wegetit.nautilus.commons.cache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class DistributedCacheInfo {
    private String name;
    private int elements;
}
