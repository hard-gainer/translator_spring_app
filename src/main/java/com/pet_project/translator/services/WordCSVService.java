package com.pet_project.translator.services;


import com.pet_project.translator.models.WordCSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface WordCSVService {

    List<WordCSVRecord> convertCSV(File csvFile);
}
