package soon.PTCMR_Back.domain.product.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import soon.PTCMR_Back.domain.category.entity.Category;
import soon.PTCMR_Back.domain.product.dto.request.ProductCreateRequest;
import soon.PTCMR_Back.domain.team.entity.Team;

public class ProductTest {

    public static Product createProduct(Team team, Category category) {
        return Product.create()
            .name("자일리톨")
            .expirationDate(LocalDateTime.now().plusDays(3))
            .quantity(3)
            .imageUrl("")
            .storageType(StorageType.ROOM_TEMPERATURE.toString())
            .repurchase(true)
            .description("설명")
            .team(team)
            .category(category)
            .build();
    }

    public static List<Product> pagingSetUp(Team team, Category category) {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Product product = Product.create()
                .name("자일리톨" + i)
                .expirationDate(LocalDateTime.now().plusDays(3))
                .quantity(3)
                .imageUrl("")
                .storageType(StorageType.ROOM_TEMPERATURE.toString())
                .repurchase(true)
                .description("설명")
                .team(team)
                .category(category)
                .build();

            products.add(product);
        }
        return products;
    }
}