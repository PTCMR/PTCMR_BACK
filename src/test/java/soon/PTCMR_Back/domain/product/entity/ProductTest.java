package soon.PTCMR_Back.domain.product.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import soon.PTCMR_Back.domain.product.dto.request.ProductCreateRequest;

public class ProductTest {

    public static Product createProduct() {
        return Product.create(new ProductCreateRequest("자일리톨",
            LocalDateTime.now().plusDays(19), 2, "", StorageType.ROOM_TEMPERATURE.toString(),
            true, "이것은 자일리톨 껌이요", 1L));
    }
}