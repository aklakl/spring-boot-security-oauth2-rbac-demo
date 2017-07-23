package com.leewaiho.Configurer;

import com.leewaiho.Bean.BaseBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

/**
 * Created by leewaiho on 2017/7/20.
 */
@Configuration
public class RepositoryRestWebMvcConfigurer extends RepositoryRestConfigurerAdapter {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(BaseBean.class);
    }
}
