package com.georgiaandtours.repository;

import com.georgiaandtours.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagesRepository extends JpaRepository<Message, Integer> {
    List<Message> findAllBySenderEmail(String email);

    List<Message> findAllByReceiverEmail(String email);
}
