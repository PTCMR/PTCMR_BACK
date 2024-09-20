package soon.PTCMR_Back.domain.team.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import soon.PTCMR_Back.domain.category.service.CategoryService;

@RequiredArgsConstructor
@Component
public class TeamEventListener {

    private final CategoryService categoryService;

    @EventListener
    public void handleTeamCreatedEvent(TeamCreateEvent event) {
    }
}
