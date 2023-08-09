package com.pet_project.translator.mappers;

import com.pet_project.translator.entities.Word;
import com.pet_project.translator.models.WordDTO;
import org.mapstruct.Mapper;

@Mapper
public interface WordMapper {

    Word wordDtoToWord(WordDTO dto);

    WordDTO wordToWordDto(Word word);

}
