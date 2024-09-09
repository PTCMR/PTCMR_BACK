package soon.PTCMR_Back.domain.product.service;

import static soon.PTCMR_Back.domain.product.entity.ProductSortOption.toSortOption;
import static soon.PTCMR_Back.domain.product.repository.ProductPaginationRepository.PAGE_SIZE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.PTCMR_Back.domain.category.entity.Category;
import soon.PTCMR_Back.domain.category.repository.CategoryRepository;
import soon.PTCMR_Back.domain.product.dto.ProductPaginationDto;
import soon.PTCMR_Back.domain.product.dto.request.ProductCreateRequest;
import soon.PTCMR_Back.domain.product.dto.request.ProductPaginationRequest;
import soon.PTCMR_Back.domain.product.dto.request.ProductUpdateRequest;
import soon.PTCMR_Back.domain.product.dto.response.ProductDetailResponse;
import soon.PTCMR_Back.domain.product.dto.response.ProductPaginationResponseWrapper;
import soon.PTCMR_Back.domain.product.entity.Product;
import soon.PTCMR_Back.domain.product.repository.ProductPaginationRepository;
import soon.PTCMR_Back.domain.product.repository.ProductRepository;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.domain.team.repository.TeamRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final TeamRepository teamRepository;
    private final ProductPaginationRepository productPaginationRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Long create(ProductCreateRequest request) {
        Team team = teamRepository.findById(request.teamId());
        Category category = categoryRepository.findById(request.categoryId());

        Product product = Product.create()
            .name(request.name())
            .expirationDate(request.expirationDate())
            .quantity(request.quantity())
            .imageUrl(request.imageUrl())
            .storageType(request.storageType())
            .repurchase(request.repurchase())
            .description(request.description())
            .team(team)
            .category(category)
            .build();
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

    @Transactional(readOnly = true)
    public ProductPaginationResponseWrapper getPaginatedProducts(ProductPaginationRequest request) {
        Team team = teamRepository.findById(request.teamId());

        List<ProductPaginationDto> paginatedProducts = productPaginationRepository.getProductList(
            request.lastProductId(), toSortOption(request.sortOption()), request.categoryTitle(),
            team);

        boolean hasNext = determineHasNextPage(paginatedProducts);

        return ProductPaginationResponseWrapper.builder()
            .products(paginatedProducts)
            .hasNext(hasNext)
            .categoryTitle(request.categoryTitle())
            .build();
    }

    private boolean determineHasNextPage(List<ProductPaginationDto> paginatedProducts) {
        if (paginatedProducts.size() > PAGE_SIZE) {
            paginatedProducts.remove(PAGE_SIZE);
            return true;
        }
        return false;
    }
}