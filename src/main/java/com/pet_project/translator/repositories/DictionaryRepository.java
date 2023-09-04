package com.pet_project.translator.repositories;

import com.pet_project.translator.entities.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface DictionaryRepository extends JpaRepository<Dictionary, UUID> {

}
