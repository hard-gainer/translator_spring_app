package com.pet_project.translator.bootstrap;

import com.pet_project.translator.entities.Dictionary;
import com.pet_project.translator.entities.Word;
import com.pet_project.translator.repositories.DictionaryRepository;
import com.pet_project.translator.repositories.WordRepository;
import com.pet_project.translator.services.DictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner
{

    private final DictionaryRepository dictionaryRepository;
    private final WordRepository wordRepository;
    private final DictionaryService dictionaryService;

    private List<Word> wordList1;
    private List<Word> wordList2;


    @Override
    public void run(String... args) throws Exception {
        loadWordData();
        loadDictionaryData();
    }


    private void loadDictionaryData() {

        if (dictionaryRepository.count() == 0) {
            Dictionary dictionary1 = Dictionary.builder()
                    .id(UUID.randomUUID())
                    .name("Dictionary_1")
                    .number_of_rows(0)
                    .wordMap(wordList1.stream().collect(Collectors.toMap(Word::getId, word -> word)))
                    .build();

        List<Map<UUID, Word>> wordListDictionary1 = List.of(dictionary1.getWordMap());
        for (Map<UUID, Word> map : wordListDictionary1) {
            for (Map.Entry<UUID, Word> pair : map.entrySet()) {
                setAndIncRowNum(pair.getValue(), dictionary1);
            }
        }

            Dictionary dictionary2 = Dictionary.builder()
                    .id(UUID.randomUUID())
                    .name("Dictionary_2")
                    .number_of_rows(0)
                    .wordMap(wordList2.stream().collect(Collectors.toMap(Word::getId, word -> word)))
                    .build();

        List<Map<UUID, Word>> wordListDictionary2 = List.of(dictionary2.getWordMap());
        for (Map<UUID, Word> map : wordListDictionary2) {
            for (Map.Entry<UUID, Word> pair : map.entrySet()) {
                setAndIncRowNum(pair.getValue(), dictionary2);
            }
        }

            dictionaryRepository.save(dictionary1);
            dictionaryRepository.save(dictionary2);
        }

    }

    private void loadWordData() {

        if (wordRepository.count() == 0) {
            Word word1 = Word.builder()
                    .id(UUID.randomUUID())
                    .text("cat")
                    .translation("кошка")
                    .build();


            Word word2 = Word.builder()
                    .id(UUID.randomUUID())
                    .text("dog")
                    .translation("собака")
                    .build();

            Word word3 = Word.builder()
                    .id(UUID.randomUUID())
                    .text("mouse")
                    .translation("мышь")
                    .build();

            Word word4 = Word.builder()
                    .id(UUID.randomUUID())
                    .text("beer")
                    .translation("пиво")
                    .build();

            Word word5 = Word.builder()
                    .id(UUID.randomUUID())
                    .text("rum")
                    .translation("ром")
                    .build();

            Word word6 = Word.builder()
                    .id(UUID.randomUUID())
                    .text("champagne")
                    .translation("шампанское")
                    .build();

            this.wordList1 = Arrays.asList(word1, word2, word3);
            this.wordList2 = Arrays.asList(word4, word5, word6);
        }
    }


    public void setAndIncRowNum(Word word, Dictionary dictionary) {
        word.setRowNum(dictionary.getNumber_of_rows() + 1);
        dictionary.setNumber_of_rows(dictionary.getNumber_of_rows() + 1);
    }

}
