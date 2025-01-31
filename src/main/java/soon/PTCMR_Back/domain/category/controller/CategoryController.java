package soon.PTCMR_Back.domain.category.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import soon.PTCMR_Back.domain.category.dto.request.CategoryCreateRequest;
import soon.PTCMR_Back.domain.category.dto.request.CategoryDeleteRequest;
import soon.PTCMR_Back.domain.category.dto.request.CategoryPaginationRequest;
import soon.PTCMR_Back.domain.category.dto.request.CategoryUpdateRequest;
import soon.PTCMR_Back.domain.category.dto.response.CategoryPaginationResponseWrapper;
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

    @PatchMapping("/{categoryId}")
    public ResponseEntity<Void> update(@RequestBody @Valid CategoryUpdateRequest request,
        @PathVariable Long categoryId) {
        categoryService.update(request, categoryId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody @Valid CategoryDeleteRequest request) {
        categoryService.deleteCategoryAndReassignProducts(request);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<CategoryPaginationResponseWrapper> list(
        @RequestBody @Valid CategoryPaginationRequest request) {
        CategoryPaginationResponseWrapper response = categoryService.getPaginatedCategories(
            request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
