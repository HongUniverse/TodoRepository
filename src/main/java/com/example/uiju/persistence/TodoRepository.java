package com.example.uiju.persistence;

import com.example.uiju.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {

    // ?1은 메서드의 매개변수의 순서 위치다.
    @Query("select t from TodoEntity t where t.userId = ?1")
    List<TodoEntity> findByUserId(String userId);

    @Modifying
    @Transactional
    @Query("update TodoEntity t set t.title = :newTitle where t.id = :id")
    int updateTitleById(String newTitle, String id);
}
