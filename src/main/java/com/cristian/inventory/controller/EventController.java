package com.cristian.inventory.controller;

import com.cristian.inventory.dto.EventDto;
import com.cristian.inventory.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventDto> addEvent(@RequestBody EventDto eventDto) {
        EventDto saved = eventService.addEvent(eventDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
