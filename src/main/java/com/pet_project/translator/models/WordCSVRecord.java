package com.pet_project.translator.models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordCSVRecord {

    @CsvBindByPosition(position = 0)
    String text;

    @CsvBindByPosition(position = 1)
    String translation;
}
