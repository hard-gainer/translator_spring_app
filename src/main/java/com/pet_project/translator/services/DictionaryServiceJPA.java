package com.pet_project.translator.services;

import com.pet_project.translator.entities.Dictionary;
import com.pet_project.translator.entities.Word;
import com.pet_project.translator.mappers.DictionaryWordMapper;
import com.pet_project.translator.models.DictionaryDTO;
import com.pet_project.translator.models.WordDTO;
import com.pet_project.translator.repositories.DictionaryRepository;
import com.pet_project.translator.repositories.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class DictionaryServiceJPA implements DictionaryService {

    private final DictionaryRepository dictionaryRepository;
    private final WordRepository wordRepository;
    private final DictionaryWordMapper mapper;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 25;

    @Override
    public Word saveNewWord(UUID dictionaryId, WordDTO word) {

        AtomicReference<Word> returnWord = new AtomicReference<>();

        dictionaryRepository.findById(dictionaryId).ifPresent(foundDictionary -> {

            word.setRowNum(foundDictionary.getNumber_of_rows() + 1);
            foundDictionary.setNumber_of_rows(foundDictionary.getNumber_of_rows() + 1);
            word.setId(UUID.randomUUID());
//            word.setDictionary(foundDictionary);

            Word newWord = mapper.wordDtoToWord(word);
            foundDictionary.getWordMap().put(UUID.randomUUID(), newWord);

            newWord.setDictionary(foundDictionary);

            dictionaryRepository.save(foundDictionary);

            returnWord.set(newWord);
//            wordRepository.save(newWord);
        });
        return returnWord.get();
    }

    @Override
    public Optional<List<WordDTO>> getDictionaryById(UUID id, Integer pageNumber, Integer pageSize) {

        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
        List<Word> list = wordRepository.findAllByDictionary_Id(id, pageRequest);
        List<WordDTO> print_list = new ArrayList<>();

        list.forEach(word -> print_list.add(mapper.wordToWordDto(word)));

        return Optional.of(print_list);
    }

    private PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE;
        }

        if (pageSize == null) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        } else {
            if (pageSize > 25) {
                queryPageSize = 25;
            } else {
                queryPageSize = pageSize;
            }
        }

        return PageRequest.of(queryPageNumber, queryPageSize, Sort.by(Sort.Direction.ASC, "rowNum"));
    }


    @Override
    public Map<UUID, String> printAllDictionaries() {

        return dictionaryRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Dictionary::getId, Dictionary::getName));
    }

    @Override
    public Optional<WordDTO> updateWordById(UUID dictionaryId, WordDTO word) {

        AtomicReference<Optional<WordDTO>> atomicReference = new AtomicReference<>();

        wordRepository.findById(word.getId()).ifPresentOrElse(foundWord -> {
            foundWord.setText(word.getText());
            foundWord.setTranslation(word.getTranslation());

            atomicReference.set(Optional.of(mapper.wordToWordDto(wordRepository.save(foundWord))));
        }, () -> atomicReference.set(Optional.empty()));

        dictionaryRepository.findById(dictionaryId).ifPresentOrElse(foundDictionary -> {
                    Word current_word = foundDictionary.getWordMap().get(word.getId());
                    current_word.setText(word.getText());
                    current_word.setTranslation(word.getTranslation());

                    dictionaryRepository.save(foundDictionary);
        }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    @Override
    public Boolean deleteWordById(UUID dictionaryId, UUID wordId) {

        if (dictionaryRepository.existsById(dictionaryId)) {

            dictionaryRepository.findById(dictionaryId).ifPresent(foundDictionary -> {
                foundDictionary.getWordMap().remove(wordId);

                foundDictionary.setNumber_of_rows(0);
                Map<UUID, Word> map = foundDictionary.getWordMap();

                for (var elem : map.values()) {
                    elem.setRowNum(foundDictionary.getNumber_of_rows() + 1);
                    foundDictionary.setNumber_of_rows(foundDictionary.getNumber_of_rows() + 1);
                }

                dictionaryRepository.save(foundDictionary);

            });

            if (wordRepository.existsById(wordId)){
                wordRepository.deleteById(wordId);
                return true;
            }
            else return false;

        }
        else return false;
    }

    @Override
    public Boolean deleteDictionaryById(UUID dictionaryId) {

        if (wordRepository.existsAllByDictionary_Id(dictionaryId)) {
            wordRepository.deleteAllByDictionary_Id(dictionaryId);

            if (dictionaryRepository.existsById(dictionaryId))
            {
                dictionaryRepository.deleteById(dictionaryId);
                return true;
            }
            else return false;
        }
        else return false;
    }

    @Override
    public DictionaryDTO saveNewDictionary(DictionaryDTO dictionary) {

        String dictionaryName = dictionary.getName();
        dictionary = DictionaryDTO.builder()
                .id(UUID.randomUUID())
                .name(dictionaryName)
                .number_of_rows(0)
                .wordMap(new HashMap<>())
                .build();

        dictionaryRepository.save(mapper.dictionaryDtoToDictionary(dictionary));
        return dictionary;
    }
}
