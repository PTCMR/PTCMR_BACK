package soon.PTCMR_Back.domain.product.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soon.PTCMR_Back.domain.product.dto.request.ProductCreateRequest;
import soon.PTCMR_Back.domain.product.entity.Product;
import soon.PTCMR_Back.domain.product.repository.ProductRepository;
import soon.PTCMR_Back.domain.team.repository.TeamRepository;
import soon.PTCMR_Back.global.exception.TeamNotFoundException;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public Long create(ProductCreateRequest request) {
        boolean exists = teamRepository.existsById(request.getTeamId());

        if (!exists) {
            throw new TeamNotFoundException();
        }

        Product product = Product.create(request);
        productRepository.save(product);

        return product.getId();
    }
}

//[#32] Feat: Security 및 OAuth2 구현