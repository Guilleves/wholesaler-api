package com.api.rest.security;

// #region Imports
import javax.annotation.Priority;

import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Context;
import javax.ws.rs.container.ResourceInfo;

import java.lang.reflect.AnnotatedElement;

import java.io.IOException;

// #endregion

@Secured
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {
    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String resource = getResource(resourceInfo.getResourceClass());
        String permission = getPermission(resourceInfo.getResourceMethod());
    }

    private String getResource(AnnotatedElement annotatedElement) {
        if (annotatedElement == null)
        return null;

        Resource resource = annotatedElement.getAnnotation(Resource.class);

        if (resource == null)
        return null;

        return resource.resource();
    }

    private String getPermission(AnnotatedElement annotatedElement) {
        if (annotatedElement == null)
        return null;

        Permission permission = annotatedElement.getAnnotation(Permission.class);

        if (permission == null)
        return null;

        return permission.permission();
    }
}
