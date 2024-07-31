package soon.PTCMR_Back.domain.product.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.function.LongPredicate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProductStatus {

    BLACK(daysLeft -> daysLeft <= 0),
    RED(daysLeft -> daysLeft > 0 && daysLeft < 10),
    YELLOW(daysLeft -> daysLeft >= 10 && daysLeft < 20),
    GREEN(daysLeft -> daysLeft >= 20);

    private final LongPredicate status;

    public static ProductStatus getProductStatus(LocalDateTime expirationDate) {
        long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), expirationDate.toLocalDate());

        return Arrays.stream(ProductStatus.values())
            .filter(p -> p.status.test(daysLeft))
            .findFirst()
            .orElseThrow(NullPointerException::new);
    }
}
