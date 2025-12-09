package com.cristian.inventory.service;

import com.cristian.inventory.dto.EventDto;
import com.cristian.inventory.entity.EventEntity;
import com.cristian.inventory.entity.ProfileEntity;
import com.cristian.inventory.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {

    private final ProfileService profileService;
    private final EventRepository eventRepository;

    public EventDto addEvent(EventDto eventDto) {
        ProfileEntity profile = profileService.getCurrentProfile();
        EventEntity newEvent = toEntity(eventDto, profile);
        newEvent = eventRepository.save(newEvent);

        return toDto(newEvent);
    }

    //HELPER METHODS

    private EventEntity toEntity(EventDto eventDto, ProfileEntity profile) {
        return EventEntity.builder()
                .name(eventDto.getName())
                .date(eventDto.getDate())
                .description(eventDto.getDescription())
                .profile(profile)
                .build();
    }

    private EventDto toDto(EventEntity event) {
        return EventDto.builder()
                .id(event.getId())
                .name(event.getName())
                .date(event.getDate())
                .description(event.getDescription())
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .build();
    }
}
