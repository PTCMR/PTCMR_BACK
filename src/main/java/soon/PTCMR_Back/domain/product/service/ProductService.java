package soon.PTCMR_Back.domain.product.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soon.PTCMR_Back.domain.product.dto.request.ProductCreateRequest;
import soon.PTCMR_Back.domain.product.dto.request.ProductUpdateRequest;
import soon.PTCMR_Back.domain.product.entity.Product;
import soon.PTCMR_Back.domain.product.repository.ProductRepository;
import soon.PTCMR_Back.domain.team.entity.NotificationSchedule;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.domain.team.repository.TeamRepository;
import soon.PTCMR_Back.global.exception.TeamNotFoundException;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public Long create(ProductCreateRequest request) {
        // TODO 팀 개발 시 수정
        Team testTeam = Team.builder()
            .title("test team")
            .schedule(new NotificationSchedule(1L, 3L))
            .build();
        teamRepository.save(testTeam);

        boolean exists = teamRepository.existsById(request.getTeamId());

        if (!exists) {
            throw new TeamNotFoundException();
        }

        Product product = Product.create(request);
        productRepository.save(product);

        return product.getId();
    }

    @Transactional
    public void delete(Long productId) {
        Product product = productRepository.findById(productId);
        product.delete();
    }

    @Transactional
    public void update(Long productId, ProductUpdateRequest request) {
        //TODO Team 인증 기능 추가 해야함
        Product product = productRepository.findById(productId);
        product.update(request);
    }
}