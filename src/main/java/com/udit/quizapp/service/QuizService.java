package com.udit.quizapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.udit.quizapp.dao.QuestionDao;
import com.udit.quizapp.dao.QuizDao;
import com.udit.quizapp.model.Question;
import com.udit.quizapp.model.QuestionWrapper;
import com.udit.quizapp.model.Quiz;
import com.udit.quizapp.model.Response;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questionDao.findRandomQuestionsByCategory(category, numQ));
        System.out.println(quiz + "fgdsgsfs");

        quizDao.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
        Optional<Quiz> quiz = quizDao.findById(id);

        List<Question> questionFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionForUser = new ArrayList<>();

        for (Question q : questionFromDB) {
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(),
                    q.getOption3(), q.getOption4());
            questionForUser.add(qw);
        }
        return new ResponseEntity<>(questionForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer quizId, List<Response> responses) {
        Optional<Quiz> quiz = quizDao.findById(quizId);
        List<Question> questions = quiz.get().getQuestions();
        int score = 0;
        for (Response response : responses) {
            for (Question question : questions) {
                if (response.getResponse().equals(question.getRightAnswer())){
                    score++;
                    break;
                }
            }
        }

        return new ResponseEntity<>(score,HttpStatus.OK);
    }

}
