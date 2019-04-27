package com.pdclientmanager.dao;

import java.io.Serializable;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GenericCrudDaoImpl<T extends Serializable>
    extends AbstractCrudDao<T>
    implements CrudDao<T> {
    //Contains all basic CRUD methods: create, getById, getAll, update, delete, and deleteById
}
