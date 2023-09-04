package com.pet_project.translator.repositories;

import com.pet_project.translator.entities.Word;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface WordRepository extends JpaRepository<Word, UUID> {
    List<Word> findAllByDictionary_Id(UUID id, Pageable pageable);

    void deleteAllByDictionary_Id(UUID id);

    Boolean existsAllByDictionary_Id(UUID id);

}
