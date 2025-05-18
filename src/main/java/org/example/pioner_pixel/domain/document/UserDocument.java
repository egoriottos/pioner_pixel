package org.example.pioner_pixel.domain.document;

import jakarta.persistence.Id;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.pioner_pixel.domain.entity.Account;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.WriteTypeHint;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Document(indexName = "users",writeTypeHint = WriteTypeHint.FALSE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDocument {
    @Id
    private Long id;
    @Field(type = FieldType.Text, analyzer = "standard")
    private String name;
    @Field(type = FieldType.Keyword)
    private Set<String> emails;
    @Field(type = FieldType.Keyword)
    private Set<String> phones;
    @Field(type = FieldType.Double)
    private BigDecimal balance;
    @Field(type = FieldType.Date, format = DateFormat.date)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
}
