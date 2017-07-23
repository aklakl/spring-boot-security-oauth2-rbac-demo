package com.leewaiho.Repository;

import com.leewaiho.Bean.Role;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by leewaiho on 2017/7/22.
 */
@RepositoryRestResource
public interface RoleRepository extends BaseRepository<Role, Long> {
    Role findByName(String name);
}
