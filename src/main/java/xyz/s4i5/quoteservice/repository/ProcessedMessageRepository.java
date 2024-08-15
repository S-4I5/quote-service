package xyz.s4i5.quoteservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.s4i5.quoteservice.model.entity.ProcessedMessage;

import java.util.UUID;

public interface ProcessedMessageRepository extends JpaRepository<ProcessedMessage, UUID> {
}
