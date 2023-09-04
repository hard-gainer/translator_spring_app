package com.pet_project.translator.services;

import com.opencsv.bean.CsvToBeanBuilder;
import com.pet_project.translator.models.WordCSVRecord;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class WordCSVServiceImpl implements WordCSVService {
    @Override
    public List<WordCSVRecord> convertCSV(File csvFile) {

        try {
            return new CsvToBeanBuilder<WordCSVRecord>(new FileReader(csvFile))
                    .withType(WordCSVRecord.class)
                    .build().parse();
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
