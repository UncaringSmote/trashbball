package com.mycompany.trashbasketball.web.rest;

import com.mycompany.trashbasketball.domain.Score;
import com.mycompany.trashbasketball.domain.User;
import com.mycompany.trashbasketball.service.ScoreService;
import com.mycompany.trashbasketball.service.UserService;
import com.mycompany.trashbasketball.service.dto.AdminUserDTO;
import com.mycompany.trashbasketball.web.rest.errors.InvalidPasswordException;
import com.mycompany.trashbasketball.web.rest.vm.ManagedUserVM;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/score")
public class ScoreResource {
    private final UserService userService;
    private final ScoreService scoreService;

    public ScoreResource(UserService userService, ScoreService scoreService) {
        this.userService = userService;
        this.scoreService = scoreService;
    }

    @PutMapping("/update/{score}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Score> updateScore(@PathVariable Long score) {
        Optional<User> userOptional = userService.getUserWithAuthorities();
        if(userOptional.isPresent())
        {
           User user = userOptional.get();
           Optional<Score> scorenew = scoreService.updateScore(user.getId(),score);
           if(scorenew.isPresent())
           {
               return ResponseEntity.ok(scorenew.get());
           }
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/all")
    public List<Score> getScores()
    {
        List<Score> scores=scoreService.getAllScores();
        scores.sort(Comparator.comparing(Score::getTotalscore));
        Collections.reverse(scores);
        return scores;
    }
}
