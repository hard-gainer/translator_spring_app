package com.pet_project.translator.mappers;

import com.pet_project.translator.entities.Dictionary;
import com.pet_project.translator.entities.Word;
import com.pet_project.translator.models.DictionaryDTO;
import com.pet_project.translator.models.WordDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DictionaryWordMapper {

    Dictionary dictionaryDtoToDictionary(DictionaryDTO dictionaryDTO);

    DictionaryDTO dictionaryToDictionaryDTO(Dictionary dictionary);

    Word wordDtoToWord(WordDTO dto);

    WordDTO wordToWordDto(Word word);
}
