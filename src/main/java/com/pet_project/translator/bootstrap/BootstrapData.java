package com.pet_project.translator.bootstrap;

import com.pet_project.translator.entities.Dictionary;
import com.pet_project.translator.entities.Word;
import com.pet_project.translator.models.WordCSVRecord;
import com.pet_project.translator.repositories.DictionaryRepository;
import com.pet_project.translator.services.WordCSVService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class BootstrapData implements CommandLineRunner {

    private final DictionaryRepository dictionaryRepository;
    private final WordCSVService wordCSVService;

    private final List<Word> wordList1 = new ArrayList<>();
    private final List<Word> wordList2 = new ArrayList<>();


    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadCsvData();
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

            loadWords(dictionary1);
            wordList1.forEach(word -> word.setDictionary(dictionary1));
            dictionaryRepository.save(dictionary1);

            Dictionary dictionary2 = Dictionary.builder()
                    .id(UUID.randomUUID())
                    .name("Dictionary_2")
                    .number_of_rows(0)
                    .wordMap(wordList2.stream().collect(Collectors.toMap(Word::getId, word -> word)))
                    .build();

            loadWords(dictionary2);
            wordList2.forEach(word -> word.setDictionary(dictionary2));
            dictionaryRepository.save(dictionary2);
        }
    }

    private void loadWords(Dictionary dictionary) {
        List<Map<UUID, Word>> wordListDictionary = List.of(dictionary.getWordMap());
        for (Map<UUID, Word> map : wordListDictionary) {
            for (Map.Entry<UUID, Word> pair : map.entrySet()) {
                setAndIncRowNum(pair.getValue(), dictionary);
            }
        }
    }


    private void loadCsvData() throws FileNotFoundException {

        if (dictionaryRepository.count() == 0) {
            File file = ResourceUtils.getFile("classpath:csvdata/data.csv");

            List<WordCSVRecord> records = wordCSVService.convertCSV(file);

            for (int i = 0; i < records.size(); i++) {
                WordCSVRecord csvData = records.get(i);
                if (i % 2 == 0) {

                    wordList1.add(Word.builder()
                            .id(UUID.randomUUID())
                            .translation(csvData.getTranslation())
                            .text(csvData.getText())
                            .build());
                } else {

                    wordList2.add(Word.builder()
                            .id(UUID.randomUUID())
                            .translation(csvData.getTranslation())
                            .text(csvData.getText())
                            .build());
                }
            }
        }
    }


    public void setAndIncRowNum(Word word, Dictionary dictionary) {
        word.setRowNum(dictionary.getNumber_of_rows() + 1);
        dictionary.setNumber_of_rows(dictionary.getNumber_of_rows() + 1);
    }

}
