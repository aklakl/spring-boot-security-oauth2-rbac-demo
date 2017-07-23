package com.leewaiho.Repository;

import com.leewaiho.Bean.User;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by leewaiho on 2017/7/20.
 */
@RepositoryRestResource
public interface UserRepository extends BaseRepository<User, Long> {
    User findByUsername(String username);
}
