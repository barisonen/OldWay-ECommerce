package com.oldway.config;

import com.oldway.entity.*;
import com.oldway.entity.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    @Value("${allowed.origins}")
    private String[] allowedOrigins;
    private final EntityManager entityManager;

    public MyDataRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);

        HttpMethod[] unSupportedActions = {HttpMethod.POST, HttpMethod.DELETE,
                                           HttpMethod.PUT, HttpMethod.PATCH};

        //disable HTTP methods: PUT, POST, PATCH and DELETE
        disableHttpMethods(Product.class, config, unSupportedActions);
        disableHttpMethods(ProductCategory.class, config, unSupportedActions);
        disableHttpMethods(Country.class, config, unSupportedActions);
        disableHttpMethods(State.class, config, unSupportedActions);
        disableHttpMethods(Order.class, config, unSupportedActions);

        //call an internal helper method
        exposeIds(config);

        //configure cors mapping
        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(allowedOrigins);
    }

    private static void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] unSupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(unSupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(unSupportedActions));
    }

    private void exposeIds(RepositoryRestConfiguration config) {

        //expose entity ids

        //get a list of all entity classes from the entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        List<Class> entityClasses = new ArrayList<>();

        // get the entity types for the entities
        for(EntityType tempEntityType: entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        //expose ids for the array of entity/domain types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }


}
