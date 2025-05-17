package com.jpademo.relations.manytomany.demo.repo;

import org.springframework.data.repository.CrudRepository;

import com.jpademo.relations.manytomany.demo.entities.Project;

public interface ProjectRepo extends CrudRepository<Project, Integer> {

}
