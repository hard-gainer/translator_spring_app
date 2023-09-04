package com.pet_project.translator.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryDTO {
    private UUID id;

    @NotBlank
    @NotEmpty(message = "Please provide a name")
    private String name;

    private long number_of_rows;
    Map<UUID, WordDTO> wordMap;
}
