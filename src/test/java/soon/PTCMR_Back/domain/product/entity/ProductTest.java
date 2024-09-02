package soon.PTCMR_Back.domain.product.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import soon.PTCMR_Back.domain.product.dto.request.ProductCreateRequest;

public class ProductTest {

    public static Product createProduct(Long teamId) {
        return Product.create(new ProductCreateRequest("자일리톨",
            LocalDateTime.now().plusDays(19), 2, "", StorageType.ROOM_TEMPERATURE.toString(),
            true, "이것은 자일리톨 껌이요", teamId));
    }

    public static List<Product> pagingSetUp(Long teamId) {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            ProductCreateRequest request = new ProductCreateRequest("자일리톨" + i,
                LocalDateTime.now().plusDays(19), 2, "", StorageType.ROOM_TEMPERATURE.toString(),
                true, "이것은 자일리톨 껌이요", teamId);

            Product product = Product.create(request);
            products.add(product);
        }
        return products;
    }
}