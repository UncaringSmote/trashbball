package com.mycompany.trashbasketball.service;

import com.mycompany.trashbasketball.domain.Score;
import com.mycompany.trashbasketball.repository.ScoreRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ScoreService {
    private final ScoreRepository scoreRepository;

    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public Optional<Score> getUserScore(Long userId)
    {
        return scoreRepository.findByUserId(userId);
    }

    public Score createUserScore(Long userId,String name)
    {
        return scoreRepository.save(new Score().setUserId(userId).setLogin(name).setTotalscore(0L).setLastScore(0L).setLastScoreDate(Instant.now()));
    }

    public List<Score> getAllScores()
    {
        return scoreRepository.findAll();
    }

    public Optional<Score> updateScore(Long userId, Long score)
    {
        Optional<Score> userScore = scoreRepository.findByUserId(userId);
        if(userScore.isPresent())
        {
            Score thisisgarbage= userScore.get();
            if(Instant.now().truncatedTo(ChronoUnit.DAYS).equals(thisisgarbage.getLastScoreDate().truncatedTo(ChronoUnit.DAYS)))
            {
                if(thisisgarbage.getLastScore()<score)
                {
                    thisisgarbage.setTotalscore(thisisgarbage.getTotalscore()-thisisgarbage.getLastScore()+score);
                    thisisgarbage.setLastScore(score);
                }
            }
            else{
                thisisgarbage.setTotalscore(thisisgarbage.getTotalscore()+score);
                thisisgarbage.setLastScore(score);
                thisisgarbage.setLastScoreDate(Instant.now());
            }
            return Optional.of(scoreRepository.save(thisisgarbage));
        }
        return Optional.empty();
    }
}
