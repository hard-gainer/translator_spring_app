package com.pet_project.translator.controller;

import com.pet_project.translator.models.DictionaryDTO;
import com.pet_project.translator.models.TYPE;
import com.pet_project.translator.models.WordDTO;
import com.pet_project.translator.services.DictionaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class DictionaryController {

    public static final String START = "http://localhost:8080/";
    public static final String DICTIONARY_PATH = "api/v1/dictionary";
    public static final String DICTIONARY_PATH_ID = DICTIONARY_PATH + "/{dictionaryId}";
    public static final String WORD_PATH_ID = DICTIONARY_PATH_ID + "/{wordId}";

    private final DictionaryService dictionaryService;

    @PutMapping(value = DICTIONARY_PATH_ID)
    public ResponseEntity<String> updateWordById(@PathVariable("dictionaryId") UUID dictionaryId,
                                                 @Validated @RequestBody WordDTO word) {

        if (dictionaryService.updateWordById(dictionaryId, word).isEmpty())
            throw new NotFoundException("Word not found");

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(DICTIONARY_PATH_ID)
    public ResponseEntity<String> deleteDictionaryById(@PathVariable UUID dictionaryId) {

        if (!dictionaryService.deleteDictionaryById(dictionaryId))
            throw new NotFoundException("Dictionary not found");

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping(WORD_PATH_ID)
    public ResponseEntity<String> deleteWordById(@PathVariable UUID dictionaryId,
                                                 @PathVariable UUID wordId) {

        Optional<TYPE> value = dictionaryService.deleteWordById(dictionaryId, wordId);
        if (value.isPresent()) {
            if (value.get() == TYPE.DICT)
                throw new NotFoundException("Dictionary not found");
            else if (value.get() == TYPE.WORD)
                throw new NotFoundException("Word not found");
        }


        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = DICTIONARY_PATH)
    public ResponseEntity<String> saveNewDictionary(@Valid @RequestBody DictionaryDTO dictionary) {

        dictionaryService.saveNewDictionary(dictionary);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(DICTIONARY_PATH_ID)
    public ResponseEntity<String> saveNewWord(@PathVariable UUID dictionaryId,
                                              @Valid @RequestBody WordDTO word) {


        WordDTO savedWord = dictionaryService.saveNewWord(dictionaryId, word);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", DICTIONARY_PATH + dictionaryId + "/" + savedWord.getId());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(DICTIONARY_PATH)
    public Page<DictionaryDTO> printAllDictionaries(@RequestParam(required = false) Integer pageNumber,
                                                    @RequestParam(required = false) Integer pageSize) {
        return dictionaryService.printAllDictionaries(pageNumber, pageSize);
    }

    @GetMapping(DICTIONARY_PATH_ID)
    public Page<WordDTO> getDictionaryById(@PathVariable("dictionaryId") UUID dictionaryId,
                                           @RequestParam(required = false) Integer pageNumber,
                                           @RequestParam(required = false) Integer pageSize) {
        if (dictionaryService.getDictionaryById(dictionaryId, pageNumber, pageSize).isEmpty())
            throw new NotFoundException();
        else
            return dictionaryService.getDictionaryById(dictionaryId, pageNumber, pageSize);
    }

}
