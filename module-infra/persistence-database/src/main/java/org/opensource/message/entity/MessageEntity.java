package org.opensource.message.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class MessageEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer id;
}
