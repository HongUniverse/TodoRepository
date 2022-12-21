package com.example.uiju.service;

import com.example.uiju.model.TodoEntity;
import com.example.uiju.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public String testService(){
        //TodoEntity 생성
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
        //TodoEntity 저장
        repository.save(entity);
        //TodoEntity 검색
        TodoEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }

    public List<TodoEntity> delete(final TodoEntity entity){

        validate(entity);
        List<TodoEntity> entityList = repository.findByUserId(entity.getUserId());
        Map<String,TodoEntity> map = entityList.stream().collect(Collectors.toMap(x -> x.getId(), x -> x));
        TodoEntity tempentity = map.get(entity.getId());
        repository.delete(tempentity);

        return repository.findByUserId(entity.getUserId());
    }
    public List<TodoEntity> select(String userId){
        return repository.findByUserId(userId);
    }

    public List<TodoEntity> update(final TodoEntity entity){

        validate(entity);

        if(entity.getId() == null){
            throw new RuntimeException("check Id.");
        }
        repository.updateTitleById(entity.getTitle(), entity.getId());

        return repository.findByUserId(entity.getUserId());
    }
    public List<TodoEntity> create(final TodoEntity entity){

        validate(entity);
        repository.save(entity);
        log.info("Entity Id : {} is saved.", entity.getId());

        return repository.findByUserId(entity.getUserId());
    }

    private void validate(final TodoEntity entity) {
        //validations
        if(entity == null){
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null");
        }

        if(entity.getUserId() == null){
            log.warn("Unknown user.");
            throw new RuntimeException("unknown user.");
        }
    }
}
