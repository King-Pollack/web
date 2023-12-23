package com.king.app.presentation.api.waiting;


import com.king.app.application.waiting.WaitingSSEService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.security.Principal;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/waiting/stream")
@RequiredArgsConstructor
public class WaitingSSEController {
    private final WaitingSSEService sseService;
    @GetMapping("/total-team")
    public SseEmitter getTotalTeam(HttpServletResponse response) {
        response.setCharacterEncoding(null);
        AtomicBoolean isComplete = new AtomicBoolean(false);

        SseEmitter emitter = new SseEmitter(15000L);
        sseService.addTotalTeamEmitter(emitter);

        Runnable onDetach = () -> {
            sseService.detachTotalTeamEmitter(emitter);
            if (!isComplete.get()) {
                isComplete.set(true);
                emitter.complete();
            }
        };

        emitter.onCompletion(onDetach);
        emitter.onTimeout(onDetach);
        emitter.onError(err -> onDetach.run());

        return emitter;
    }


    @GetMapping("/my-ranking")
    public SseEmitter myRanking(HttpServletResponse response, Principal principal) {
        response.setCharacterEncoding(null);
        AtomicBoolean isComplete = new AtomicBoolean(false);

        SseEmitter emitter = new SseEmitter(15000L);
        final String userId = principal.getName();

        sseService.addMyRankEmitter(emitter, userId);

        Runnable onDetach = () -> {
            sseService.detachMyRankEmitter(userId);
            if (!isComplete.get()) {
                isComplete.set(true);
                emitter.complete();
            }
        };

        emitter.onCompletion(onDetach);
        emitter.onTimeout(onDetach);
        emitter.onError(err -> onDetach.run());

        return emitter;
    }
}