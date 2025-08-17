package com.georgiaandtours.repository;

import com.georgiaandtours.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByTourId(Integer id);
}
