package com.leewaiho.Repository;

import com.leewaiho.Bean.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

/**
 * Created by leewaiho on 2017/7/22.
 */
@RepositoryRestResource
public interface PermissionRepository extends BaseRepository<Permission, Long> {
    
    Permission findByName(String name);
    
    Collection<Permission> findAllByUrl(String url);
    
    Page<Permission> findAllByUrl(String url, Pageable pageable);
    
    Collection<Permission> findAllByMethod(String method);
    
    Page<Permission> findAllByMethod(String method, Pageable pageable);
    
    Permission findFirstByUrlAndMethod(String url, String method);
}
