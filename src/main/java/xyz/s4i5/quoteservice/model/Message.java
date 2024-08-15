package xyz.s4i5.quoteservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    private Integer id;
    private Integer date;
    @JsonProperty("peer_id")
    private Integer peerId;
    @JsonProperty("from_id")
    private Integer fromId;
    private String text;
    @JsonProperty("random_id")
    private Integer randomId;
}
