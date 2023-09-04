package com.pet_project.translator.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordDTO {

    private UUID id;
    private long rowNum;

    @NotBlank
    @NotEmpty(message = "Please provide a word")
    @Size(max = 50)
    private String text;

    @NotBlank
    @NotEmpty(message = "Please provide a translation")
    @Size(max = 50)
    private String translation;
}
