package com.georgiaandtours.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.georgiaandtours.dto.MessageDto;
import com.georgiaandtours.dto.UserDto;
import com.georgiaandtours.service.MessagesService;
import com.georgiaandtours.service.UsersService;
import com.georgiaandtours.util.Constants;
import com.georgiaandtours.util.ModelConverter;
import org.hibernate.service.spi.ServiceException;
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
    private final UsersService usersService;
    private final MessagesService messagesService;

    @Autowired
    public MessengerEndpoint(ObjectMapper objectMapper, UsersService usersService, MessagesService messagesService) {
        this.objectMapper = objectMapper;
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
            if (adminSession == null || !adminSession.isOpen()) {
                adminSession = session;
            }

            if (!Constants.SAVE_ADMIN_SID_STATIC.equals(messageDto.getSubject())) {
                String receiverSid = usersService.getUserSidByEmail(messageDto.getReceiverEmail());
                for (WebSocketSession webSocketSession : sessions) {
                    if (receiverSid.equals(webSocketSession.getId()) && webSocketSession.isOpen()) {
                        webSocketSession.sendMessage(textMessage);
                    }
                }

                messagesService.addMessage(messageDto);
            } else if (Constants.SAVE_ADMIN_SID_STATIC.equals(messageDto.getSubject())) {
                adminSession = session;
            }
        } else if (Constants.CLIENT_ROLE_STATIC.equals(messageDto.getSender())) {
            try {
                String subject = messageDto.getSubject();
                if (Constants.USER_CREATION_STATIC.equals(subject)) {
                    UserDto newUser = objectMapper.readValue(messageDto.getPayload(), UserDto.class);
                    usersService.register(newUser);

                    List<UserDto> userDtos = usersService.getUsers();
                    messageDto.setSender(Constants.SERVER_ROLE_STATIC);
                    messageDto.setPayload(objectMapper.writeValueAsString(userDtos));

                    sendToAdminIfConnected(messageDto);
                } else if (Constants.WEBSOCKET_SID_STATIC.equals(subject)) {
                    usersService.saveUserSid(messageDto.getSenderEmail(), session.getId());
                } else {
                    usersService.saveUserSid(messageDto.getSenderEmail(), session.getId());
                    usersService.updatePositionFor(messageDto.getSenderEmail());
                    messagesService.addMessage(messageDto);
                    sendToAdminIfConnected(textMessage);
                }
            } catch (JsonProcessingException | ServiceException ignore) {
            }
        }
    }

    private void sendToAdminIfConnected(Object message) throws IOException {
        if (adminSession != null && adminSession.isOpen()) {
            String messageJson = objectMapper.writeValueAsString(message);
            adminSession.sendMessage(new TextMessage(messageJson));
        }
    }
}