package com.udit.quizapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.udit.quizapp.dao.QuestionDao;
import com.udit.quizapp.model.Question;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
    try{
       return new ResponseEntity<>(questionDao.findAll(),HttpStatus.OK);
    }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
     try{
       return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
    }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }
    

    public ResponseEntity<String> addNewQuestion(Question question) {
     try{
        questionDao.save(question);
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateQuestion(Question question) {
     try{
        questionDao.save(question);
        return new ResponseEntity<>("success",HttpStatus.OK);
    }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuestion(Integer questionId) {
        try{
        questionDao.deleteById(questionId);
        return new ResponseEntity<>("Question Deleted ID: "+questionId,HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed",HttpStatus.BAD_REQUEST);
    }

}
