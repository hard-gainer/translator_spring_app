package com.pet_project.translator.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dictionaries")
public class Dictionary {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "dictionary_id", length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    @NotEmpty(message = "Please provide a name")
    private String name;

    private long number_of_rows;

    // mappedBy очень важна, даёт понять, что хибернейту не нужно создавать доп таблицу mapping'а
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dictionary")
    @MapKey(name = "id")
    private Map<UUID, Word> wordMap;
}
