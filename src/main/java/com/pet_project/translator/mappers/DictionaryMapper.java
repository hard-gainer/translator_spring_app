package com.pet_project.translator.mappers;

import com.pet_project.translator.entities.Dictionary;
import com.pet_project.translator.models.DictionaryDTO;
import org.mapstruct.Mapper;

@Mapper
public interface DictionaryMapper {

    Dictionary dictionaryDtoToDictionary(DictionaryDTO dictionaryDTO);

    DictionaryDTO dictionaryToDictionaryDTO(Dictionary dictionary);

}
