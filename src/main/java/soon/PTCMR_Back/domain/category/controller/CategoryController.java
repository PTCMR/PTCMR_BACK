package soon.PTCMR_Back.domain.category.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soon.PTCMR_Back.domain.category.dto.request.CategoryCreateRequest;
import soon.PTCMR_Back.domain.category.service.CategoryService;

@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Valid CategoryCreateRequest request) {
        Long categoryId = categoryService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryId);
    }
}
