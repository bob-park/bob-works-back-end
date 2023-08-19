package org.bobpark.eventstoreservice.domain.eventstore.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.eventstoreservice.domain.eventstore.service.CreateEventRequest;
import org.bobpark.eventstoreservice.domain.eventstore.service.EventResponse;
import org.bobpark.eventstoreservice.domain.eventstore.service.EventService;

@RequiredArgsConstructor
@RestController
@RequestMapping("event")
public class EventController {

    private final EventService eventService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public EventResponse createEvent(@RequestBody CreateEventRequest createRequest) {
        return eventService.createEvent(createRequest);
    }
}
