package io.wegetit.nautilus.commons.cache.user;

import com.hazelcast.core.HazelcastInstance;
import io.wegetit.nautilus.commons.cache.DistributedCacheService;
import io.wegetit.nautilus.commons.utils.CSVUtils;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDistributedCacheService extends DistributedCacheService<Integer, User> {

    @Getter
    @Value("${cache.user.initOnStartup:false}")
    private boolean initOnStartup;

    public UserDistributedCacheService(HazelcastInstance hazelcastInstance) {
        super(hazelcastInstance);
    }

    @Override
    public String getCacheName() {
        return "users";
    }

    @Override
    protected List<User> readAll() {
        return CSVUtils.loadFile("users.csv",
            c -> User.builder().id(Integer.valueOf(c.get(0))).username(c.get(1)).build());
    }

    @Override
    protected Integer key(User user) {
        return user.getId();
    }
}
