package com.jpademo.relations.manytomany.demo.repo;

import org.springframework.data.repository.CrudRepository;

import com.jpademo.relations.manytomany.demo.entities.Programmer;

public interface ProgrammerRepo extends CrudRepository<Programmer, Integer> {

}
