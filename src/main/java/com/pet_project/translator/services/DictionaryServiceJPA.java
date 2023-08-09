package com.pet_project.translator.services;

import com.pet_project.translator.entities.Dictionary;
import com.pet_project.translator.entities.Word;
import com.pet_project.translator.mappers.DictionaryMapper;
import com.pet_project.translator.mappers.WordMapper;
import com.pet_project.translator.models.DictionaryDTO;
import com.pet_project.translator.models.WordDTO;
import com.pet_project.translator.repositories.DictionaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class DictionaryServiceJPA implements DictionaryService {

    private final DictionaryRepository dictionaryRepository;
    private final DictionaryMapper dictionaryMapper;
    private final WordMapper wordMapper;

    @Override
    public void saveNewWord(UUID dictionaryId, WordDTO word) {

        dictionaryRepository.findById(dictionaryId).ifPresent(foundDictionary -> {

            word.setRowNum(foundDictionary.getNumber_of_rows() + 1);
            word.setId(UUID.randomUUID());
            foundDictionary.setNumber_of_rows(foundDictionary.getNumber_of_rows() + 1);

            foundDictionary.getWordMap().put(UUID.randomUUID(), wordMapper.wordDtoToWord(word));
            dictionaryRepository.save(foundDictionary);
        });
    }

    @Override
    public List<WordDTO> getDictionaryById(UUID id) {

        List<Dictionary> list = dictionaryRepository.findById(id)
                .stream()
                .toList();
        List<Word> list_words = list.get(0).getWordMap().values().stream().toList();
        List<WordDTO> list_dtos = new ArrayList<>();


        for (Word elem : list_words) {
            list_dtos.add(wordMapper.wordToWordDto(elem));
        }

        return list_dtos;
    }


    @Override
    public Map<UUID, String> printAllDictionaries() {

        return dictionaryRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Dictionary::getId, Dictionary::getName));
    }

    @Override
    public void updateWordById(UUID dictionaryId, WordDTO word) {

        dictionaryRepository.findById(dictionaryId).ifPresent(foundDictionary ->
                {
                    Word current_word = foundDictionary.getWordMap().get(word.getId());
                    current_word.setText(word.getText());
                    current_word.setTranslation(word.getTranslation());

                    dictionaryRepository.save(foundDictionary);
                });
    }

    @Override
    public void deleteWordById(UUID dictionaryId, UUID wordId) {
        dictionaryRepository.findById(dictionaryId).ifPresent(foundDictionary -> {
            foundDictionary.getWordMap().remove(wordId);

//            foundDictionary.setRows(foundDictionary.getRows() - 1);
            foundDictionary.setNumber_of_rows(0);
            Map<UUID, Word> map = foundDictionary.getWordMap();

            for (var elem : map.values()) {
                elem.setRowNum(foundDictionary.getNumber_of_rows() + 1);
                foundDictionary.setNumber_of_rows(foundDictionary.getNumber_of_rows() + 1);
            }


            dictionaryRepository.save(foundDictionary);
        });
    }

    @Override
    public void deleteDictionaryById(UUID dictionaryId) {
        dictionaryRepository.deleteById(dictionaryId);
    }

    @Override
    public void saveNewDictionary(DictionaryDTO dictionary) {

        String dictionaryName = dictionary.getName();
        dictionary = DictionaryDTO.builder()
                .id(UUID.randomUUID())
                .name(dictionaryName)
                .rows(0)
                .wordMap(new HashMap<>())
                .build();

        dictionaryRepository.save(dictionaryMapper.dictionaryDtoToDictionary(dictionary));
    }
}
