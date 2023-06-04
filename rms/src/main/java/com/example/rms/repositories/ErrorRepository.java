package com.example.rms.repositories;

import org.springframework.data.repository.CrudRepository;
import com.example.rms.entities.Error;

public interface ErrorRepository extends CrudRepository<Error,Integer>{ }
