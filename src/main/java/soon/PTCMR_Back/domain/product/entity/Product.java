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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import soon.PTCMR_Back.domain.product.dto.request.ProductCreateRequest;
import soon.PTCMR_Back.domain.team.entity.Team;
import soon.PTCMR_Back.global.entity.BaseTimeEntity;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE product SET deleted = true WHERE id=?")
@SQLRestriction("deleted = false")
@Entity
public class Product extends BaseTimeEntity {

    @ToString.Exclude
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

    private Product(ProductCreateRequest request) {
        this.name = request.getName();
        this.expirationDate = request.getExpirationDate();
        this.quantity = request.getQuantity();
        this.status = ProductStatus.getProductStatus(request.getExpirationDate());
        this.storageType = StorageType.valueOf(request.getStorageType());
        this.repurchase = request.isRepurchase();
        this.description = request.getDescription();

        // TODO S3 연동 후 변겅
        this.imageUrl =
            request.getImageUrl().isEmpty() ? "default image url" : request.getImageUrl();

        // TODO Team 구현 후 변경
        this.team = team;
    }

    public static Product create(ProductCreateRequest request) {
        return new Product(request);
    }

    public void delete() {
        this.deleted = true;
    }
}
