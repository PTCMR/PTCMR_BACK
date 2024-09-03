package soon.PTCMR_Back.domain.category.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import soon.PTCMR_Back.domain.product.entity.Product;
import soon.PTCMR_Back.domain.team.entity.Team;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    private Category(String title, Team team, Product product) {
        this.title = title;
        this.team = team;
        this.product = product;
    }

    public static Category create(String title, Team team, Product product) {
        return Category.builder()
            .title(title)
            .team(team)
            .product(product)
            .build();
    }

    public void update(String title, Product product) {
        this.title = title;
        this.product = product;
    }
}
