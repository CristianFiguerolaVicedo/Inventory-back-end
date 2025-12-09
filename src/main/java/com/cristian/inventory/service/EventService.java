package com.cristian.inventory.service;

import com.cristian.inventory.dto.EventDto;
import com.cristian.inventory.entity.EventEntity;
import com.cristian.inventory.entity.ProfileEntity;
import com.cristian.inventory.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    public List<EventDto> getCurrentMonthEventsForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());
        List<EventEntity> list = eventRepository.findByProfileIdAndDateBetween(profile.getId(), startDate, endDate);

        return list.stream().map(this::toDto).toList();
    }

    public void deleteEvent(Long id) {
        ProfileEntity profile = profileService.getCurrentProfile();
        EventEntity event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (!event.getProfile().getId().equals(profile.getId())) {
            throw new RuntimeException("Unauthorized to delete this event");
        }

        eventRepository.delete(event);
    }

    public EventDto updateEvent(Long eventId, EventDto eventDto) {
        ProfileEntity profile = profileService.getCurrentProfile();
        EventEntity event = eventRepository.findByIdAndProfileId(eventId, profile.getId())
                .orElseThrow(() -> new RuntimeException("Event not found or not accessible"));

        event.setName(eventDto.getName());
        event.setDate(eventDto.getDate());
        event.setDescription(eventDto.getDescription());
        event = eventRepository.save(event);

        return toDto(event);
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
