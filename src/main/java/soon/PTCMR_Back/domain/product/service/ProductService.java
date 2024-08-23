package soon.PTCMR_Back.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.PTCMR_Back.domain.product.dto.request.ProductCreateRequest;
import soon.PTCMR_Back.domain.product.dto.request.ProductUpdateRequest;
import soon.PTCMR_Back.domain.product.dto.response.ProductDetailResponse;
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
        boolean exists = teamRepository.existsById(request.teamId());

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
        // TODO 팀에 속한 멤버가 요청 했는지 인증 로직 추가
        Product product = productRepository.findById(productId);

        product.update(request);
    }

    @Transactional(readOnly = true)
    public ProductDetailResponse detail(Long productId) {
        Product product = productRepository.findById(productId);

        return ProductDetailResponse.from(product);
    }
}