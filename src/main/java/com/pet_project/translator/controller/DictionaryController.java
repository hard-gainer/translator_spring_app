package com.pet_project.translator.controller;

import com.pet_project.translator.entities.Word;
import com.pet_project.translator.models.DictionaryDTO;
import com.pet_project.translator.models.WordDTO;
import com.pet_project.translator.services.DictionaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class DictionaryController {

    public static final String DICTIONARY_PATH = "api/v1/dictionary";
    public static final String DICTIONARY_PATH_ID = DICTIONARY_PATH + "/{dictionaryId}";
    public static final String WORD_PATH_ID = DICTIONARY_PATH_ID + "/{wordId}";

    private final DictionaryService dictionaryService;

    @PutMapping(value = DICTIONARY_PATH)
    public ResponseEntity updateWordById(@PathVariable UUID dictionaryId, @Validated @RequestBody WordDTO word) {

        dictionaryService.updateWordById(dictionaryId, word);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(DICTIONARY_PATH_ID)
    public ResponseEntity deleteDictionaryById(@PathVariable UUID dictionaryId) {

        dictionaryService.deleteDictionaryById(dictionaryId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping(WORD_PATH_ID)
    public ResponseEntity deleteWordById(@PathVariable UUID dictionaryId, @PathVariable UUID wordId) {

        dictionaryService.deleteWordById(dictionaryId, wordId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = DICTIONARY_PATH)
    public ResponseEntity saveNewDictionary(@Valid @RequestBody DictionaryDTO dictionary) {

        dictionaryService.saveNewDictionary(dictionary);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(DICTIONARY_PATH_ID)
    public ResponseEntity saveNewWord(@PathVariable UUID dictionaryId, @Valid @RequestBody WordDTO word) {


        Word savedWord = dictionaryService.saveNewWord(dictionaryId, word);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", DICTIONARY_PATH_ID + "/" + savedWord.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping(DICTIONARY_PATH)
    public Map<UUID, String> printAllDictionaries() {
        return dictionaryService.printAllDictionaries();
    }

    @GetMapping(DICTIONARY_PATH_ID)
    public Optional<List<WordDTO>> getDictionaryById(@PathVariable("dictionaryId") UUID dictionaryId,
                                                     @RequestParam(required = false) Integer pageNumber,
                                                     @RequestParam(required = false) Integer pageSize) {
        if (dictionaryService.getDictionaryById(dictionaryId, pageNumber, pageSize).isEmpty())
            throw new NotFoundException();
        else
            return dictionaryService.getDictionaryById(dictionaryId, pageNumber, pageSize);
    }

}
