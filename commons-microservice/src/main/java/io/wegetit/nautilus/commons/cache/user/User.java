package io.wegetit.nautilus.commons.cache.user;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class User implements Serializable {
    private Integer id;
    private String username;
}
