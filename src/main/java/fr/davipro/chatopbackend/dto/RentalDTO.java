package fr.davipro.chatopbackend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalDTO {
    private Integer id;
    private String name;
    private BigDecimal surface;
    private BigDecimal price;
    private String picture;
    private String description;
    private Integer ownerId;
    
    @Schema(type = "string", format = "date", example = "2022/12/31", description = "The creation date is in format yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime createdAt;

    @Schema(type = "string", format = "date", example = "2022/12/31", description = "The updated date is in format yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime updatedAt;
}