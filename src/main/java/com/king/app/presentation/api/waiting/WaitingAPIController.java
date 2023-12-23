package com.king.app.presentation.api.waiting;


import com.king.app.application.waiting.WaitingService;
import com.king.app.domain.waiting.WaitingTeam;
import com.king.app.presentation.api.waiting.request.WaitingTeamRequest;
import com.king.app.presentation.api.waiting.response.WaitingTeamResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/waiting")
@RequiredArgsConstructor
@Slf4j
public class WaitingAPIController {
    private final WaitingService waitingService;

    @PostMapping
    public ResponseEntity<WaitingTeamResponse> waitingTeam(@RequestBody WaitingTeamRequest waitingTeamRequest, Principal principal) {

        log.debug("waitingTeamRequest: {}", waitingTeamRequest);
        //todo: globalExceptionHandler
        try {
            WaitingTeam waitingTeam = waitingService.waitingTeam(waitingTeamRequest.toEntity(Long.valueOf(principal.getName())));
            return ResponseEntity.ok().body(WaitingTeamResponse.from(waitingTeam));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).build();
        }
    }

    @PostMapping("/move-one-step-back")
    public ResponseEntity<Void> moveOneStepBack(Principal principal) {

        String userId = principal.getName();
        log.debug("userId : {}", userId);
        waitingService.moveOneStepBack(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/move-to-last")
    public ResponseEntity<Void> moveToLast(Principal principal) {

        String userId = principal.getName();
        log.debug("userId : {}", userId);
        waitingService.moveToLast(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/check")
    public ResponseEntity<Void> waitingCheck(Principal principal) {

        String userId = principal.getName();
        log.debug("userId : {}", userId);
        waitingService.waitingCheck(userId);
        return ResponseEntity.ok().build();
    }
}
