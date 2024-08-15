package xyz.s4i5.quoteservice.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class VkUser implements Serializable {
    private Integer id;
    private String firstName;
    private String secondName;
    private String isClosed;
}
