package com.georgiaandtours.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.georgiaandtours.dto.MessageDto;
import com.georgiaandtours.repository.UsersRepository;
import com.georgiaandtours.service.MessagesService;
import com.georgiaandtours.service.UsersService;
import com.georgiaandtours.util.Constants;
import com.georgiaandtours.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

@Component
public class MessengerEndpoint extends TextWebSocketHandler {

    private static final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    private WebSocketSession adminSession;
    private final ObjectMapper objectMapper;
    private final ModelConverter modelConverter;
    private final UsersService usersService;
    private final MessagesService messagesService;

    @Autowired
    public MessengerEndpoint(ObjectMapper objectMapper, ModelConverter modelConverter, UsersService usersService, MessagesService messagesService) {
        this.objectMapper = objectMapper;
        this.modelConverter = modelConverter;
        this.usersService = usersService;
        this.messagesService = messagesService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        sessions.add(session);

        if (session.isOpen()) {
            String jsonToSend = objectMapper.writeValueAsString(
                    new MessageDto(Constants.SERVER_ROLE_STATIC, Constants.WEBSOCKET_SID_STATIC, session.getId())
            );
            session.sendMessage(new TextMessage(jsonToSend));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        String payload = textMessage.getPayload();
        MessageDto messageDto = objectMapper.readValue(payload, MessageDto.class);

        if (Constants.ADMIN_ROLE_STATIC.equals(messageDto.getSender())) {
            if (!adminSession.isOpen()) {
                adminSession = session;
            }

            String receiverSid = usersService.getUserSidByEmail(messageDto.getReceiverEmail());
            for (WebSocketSession webSocketSession : sessions) {
                if (receiverSid.equals(webSocketSession.getId()) && webSocketSession.isOpen()) {
                    webSocketSession.sendMessage(textMessage);
                }
            }

            messagesService.addMessage(messageDto);
        } else if (Constants.CLIENT_ROLE_STATIC.equals(messageDto.getSender())) {
            usersService.saveUserSid(messageDto.getSender(), session.getId());

            if (adminSession.isOpen()) {
                adminSession.sendMessage(textMessage);
            }
            messagesService.addMessage(messageDto);
        }
    }
}