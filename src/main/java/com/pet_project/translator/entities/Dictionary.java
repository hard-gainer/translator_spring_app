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
    @Column(name = "id",length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    @NotEmpty(message = "Please provide a name")
    private String name;

    private long number_of_rows;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "dictionary_word_mapping",
            joinColumns = {@JoinColumn(name = "wordmap_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "word_id", referencedColumnName = "id")})
    @MapKey(name = "id")
    private Map<UUID, Word> wordMap;
}
