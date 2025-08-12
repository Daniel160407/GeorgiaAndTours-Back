package com.georgiaandtours.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    private Integer id;
    private String senderEmail;
    private String receiverEmail;
    private String sender;
    private String receiver;
    private String date;
    private String subject;
    private String payload;

    public MessageDto(String senderEmail, String receiverEmail, String sender, String receiver, String payload) {
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.sender = sender;
        this.receiver = receiver;
        this.payload = payload;
    }

    public MessageDto(String sender, String subject, String payload) {
        this.sender = sender;
        this.subject = subject;
        this.payload = payload;
    }
}
