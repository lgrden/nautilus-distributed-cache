package io.wegetit.nautilus.commons.cache.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
    private Integer id;
    private String username;
}
