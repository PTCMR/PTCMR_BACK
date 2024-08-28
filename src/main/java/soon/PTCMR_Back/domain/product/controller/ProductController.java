package soon.PTCMR_Back.domain.product.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soon.PTCMR_Back.domain.product.dto.request.ProductCreateRequest;
import soon.PTCMR_Back.domain.product.dto.request.ProductPaginationRequest;
import soon.PTCMR_Back.domain.product.dto.request.ProductUpdateRequest;
import soon.PTCMR_Back.domain.product.dto.response.ProductDetailResponse;
import soon.PTCMR_Back.domain.product.dto.ProductPaginationDto;
import soon.PTCMR_Back.domain.product.dto.response.ProductPaginationResponseWrapper;
import soon.PTCMR_Back.domain.product.service.ProductService;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/product")
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Valid ProductCreateRequest request) {
        Long productId = productService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable Long productId) {
        productService.delete(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<Void> update(@PathVariable Long productId,
        @RequestBody @Valid ProductUpdateRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/v1/product/" + productId));

        productService.update(productId, request);

        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponse> detail(@PathVariable Long productId) {
        ProductDetailResponse detail = productService.detail(productId);

        return ResponseEntity.status(HttpStatus.OK).body(detail);
    }

    @GetMapping
    public ResponseEntity<ProductPaginationResponseWrapper> getPaginatedProducts(
        @RequestBody ProductPaginationRequest request) {
        ProductPaginationResponseWrapper paginatedProducts = productService.getPaginatedProducts(request);

        return ResponseEntity.status(HttpStatus.OK).body(paginatedProducts);
    }
}
