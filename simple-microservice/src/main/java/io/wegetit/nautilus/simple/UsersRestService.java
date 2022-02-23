package io.wegetit.nautilus.simple;

import io.wegetit.nautilus.commons.cache.user.User;
import io.wegetit.nautilus.commons.cache.user.UserDistributedCacheService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UsersRestService {

    private final UserDistributedCacheService userDistributedCacheService;

    @GetMapping
    public List<User> getAll() {
        return userDistributedCacheService.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Integer id) {
        return userDistributedCacheService.getById(id);
    }

    @GetMapping("/add")
    public User add() {
        Integer id = nextId();
        while (userDistributedCacheService.getById(id) != null) {
            id = nextId();
        }
        return userDistributedCacheService.save(User.builder()
            .id(id).username(RandomStringUtils.randomAlphabetic(10)).build());
    }

    @GetMapping("/{id}/update")
    public User update(@PathVariable Integer id) {
        User user = getById(id);
        if (user == null) {
            return null;
        }
        user.setUsername(RandomStringUtils.randomAlphabetic(10));
        return userDistributedCacheService.save(user);
    }

    @GetMapping("/{id}/delete")
    public void delete(@PathVariable Integer id) {
        userDistributedCacheService.delete(id);
    }

    private Integer nextId() {
        return ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
    }
}
