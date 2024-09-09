package soon.PTCMR_Back.domain.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import soon.PTCMR_Back.domain.category.entity.Category;
import soon.PTCMR_Back.domain.product.dto.request.ProductUpdateRequest;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.global.entity.BaseTimeEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE product SET deleted = true WHERE id=?")
@SQLRestriction("deleted = false")
@Entity
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    @Column(nullable = false)
    private int quantity;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean deleted;

    @Enumerated(EnumType.STRING)
    private StorageType storageType;

    @Column(nullable = false)
    private boolean repurchase;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    private Product(String name, LocalDateTime expirationDate, int quantity,
        String imageUrl, String storageType, boolean repurchase,
        String description, Team team, Category category) {
        this.name = name;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
        this.imageUrl = imageUrl.isEmpty() ? "default image url" : imageUrl;
        this.storageType = StorageType.toStorageType(storageType);
        this.repurchase = repurchase;
        this.description = description;
        this.category = category;
        this.team = team;
        this.status = ProductStatus.getProductStatus(expirationDate);
    }

    public static Product.ProductBuilder create() {
        return Product.builder();
    }

    public void delete() {
        this.deleted = true;
    }

    public void update(ProductUpdateRequest request) {
        this.name = request.name();
        this.expirationDate = request.expirationDate();
        this.quantity = request.quantity();
        this.status = ProductStatus.getProductStatus(request.expirationDate());
        this.storageType = StorageType.toStorageType(request.storageType());
        this.repurchase = request.repurchase();
        this.description = request.description();
//        this.category = request.category
    }
}
