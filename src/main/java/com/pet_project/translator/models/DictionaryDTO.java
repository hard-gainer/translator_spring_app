package com.pet_project.translator.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Builder
@Data
public class DictionaryDTO {
    private UUID id;

    @NotBlank
    @NotEmpty(message = "Please provide a name")
    private String name;

    private long rows;
    Map<UUID, WordDTO> wordMap;
}
