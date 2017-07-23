package com.leewaiho.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Created by leewaiho on 2017/7/20.
 */
@NoRepositoryBean
public interface BaseRepository<Bean, ID extends Serializable> extends JpaRepository<Bean, ID> {
}
