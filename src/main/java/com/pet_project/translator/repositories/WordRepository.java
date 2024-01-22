package com.pet_project.translator.repositories;

import com.pet_project.translator.entities.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WordRepository extends JpaRepository<Word, UUID> {
    Page<Word> findAllByDictionary_Id(UUID id, Pageable pageable);
}
