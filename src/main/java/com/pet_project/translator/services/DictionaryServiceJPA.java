package com.pet_project.translator.services;

import com.pet_project.translator.entities.Word;
import com.pet_project.translator.mappers.DictionaryWordMapper;
import com.pet_project.translator.models.DictionaryDTO;
import com.pet_project.translator.models.TYPE;
import com.pet_project.translator.models.WordDTO;
import com.pet_project.translator.repositories.DictionaryRepository;
import com.pet_project.translator.repositories.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
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
    public WordDTO saveNewWord(UUID dictionaryId, WordDTO word) {

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
        return mapper.wordToWordDto(returnWord.get());
    }

    @Override
    public Page<WordDTO> getDictionaryById(UUID id, Integer pageNumber, Integer pageSize) {

        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, TYPE.WORD);
        Page<Word> wordList = wordRepository.findAllByDictionary_Id(id, pageRequest);

        if (wordList.isEmpty())
            return Page.empty();

        return new PageImpl<>(wordList.stream()
                .map(mapper::wordToWordDto)
                .collect(Collectors.toList()));
    }

    private PageRequest buildPageRequest(Integer pageNumber, Integer pageSize, TYPE type) {
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

        PageRequest pageRequest;
        switch (type) {
            case WORD -> pageRequest = PageRequest.of(queryPageNumber, queryPageSize,
                    Sort.by(Sort.Direction.ASC, "rowNum"));
            case DICT -> pageRequest = PageRequest.of(queryPageNumber, queryPageSize,
                    Sort.by(Sort.Direction.ASC, "name"));
            default -> pageRequest = PageRequest.of(queryPageNumber, queryPageSize);
        }
        return pageRequest;
    }


    @Override
    public Page<DictionaryDTO> printAllDictionaries(Integer pageNumber, Integer pageSize) {

        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, TYPE.DICT);

        return new PageImpl<>(dictionaryRepository.findAll(pageRequest).stream()
                .map(mapper::dictionaryToDictionaryDTO)
                .collect(Collectors.toList()));
    }

    @Override
    public Optional<WordDTO> updateWordById(UUID dictionaryId, WordDTO word) {

        AtomicReference<Optional<WordDTO>> atomicReference = new AtomicReference<>();

        wordRepository.findById(word.getId()).ifPresentOrElse(foundWord -> {
            foundWord.setText(word.getText());
            foundWord.setTranslation(word.getTranslation());

            atomicReference.set(Optional.of(mapper.wordToWordDto(wordRepository.save(foundWord))));
        }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    @Override
    public Optional<TYPE> deleteWordById(UUID dictionaryId, UUID wordId) {

        AtomicReference<Optional<TYPE>> flag = new AtomicReference<>();
        if (!dictionaryRepository.existsById(dictionaryId)) {
            return Optional.of(TYPE.DICT);
        }

        dictionaryRepository.findById(dictionaryId).ifPresentOrElse(foundDictionary -> {
            foundDictionary.getWordMap().remove(wordId);
            wordRepository.deleteById(wordId);

            foundDictionary.setNumber_of_rows(0);
            Map<UUID, Word> map = foundDictionary.getWordMap();

            for (var elem : map.values()) {
                elem.setRowNum(foundDictionary.getNumber_of_rows() + 1);
                foundDictionary.setNumber_of_rows(foundDictionary.getNumber_of_rows() + 1);
            }

            dictionaryRepository.save(foundDictionary);
            flag.set(Optional.empty());
        }, () -> flag.set(Optional.of(TYPE.WORD)));

        return flag.get();
    }

    @Override
    public Boolean deleteDictionaryById(UUID dictionaryId) {

        if (dictionaryRepository.existsById(dictionaryId)) {
            dictionaryRepository.deleteById(dictionaryId);
            return true;
        }
        return false;
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
