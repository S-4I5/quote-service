package xyz.s4i5.quoteservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "processed_message")
public class ProcessedMessage {
    @Id
    @GeneratedValue
    private UUID id;
    @Column
    private String sourceMessage;
    @Column
    private String message;
    @Column
    private Integer userId;
    @Column
    private Integer randomId;
    @Column
    private Integer groupId;
    @Column
    private String sourceId;
    @Column
    private Long processedAt;
}
