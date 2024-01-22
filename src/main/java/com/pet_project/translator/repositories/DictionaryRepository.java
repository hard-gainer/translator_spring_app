package com.pet_project.translator.repositories;

import com.pet_project.translator.entities.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DictionaryRepository extends JpaRepository<Dictionary, UUID> {

}
