package soon.PTCMR_Back.domain.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import soon.PTCMR_Back.domain.product.dto.request.ProductCreateRequest;
import soon.PTCMR_Back.domain.product.service.ProductService;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<Long> create(@RequestBody @Valid ProductCreateRequest request) {
        Long productId = productService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> delete(@PathVariable Long productId) {
        productService.delete(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
