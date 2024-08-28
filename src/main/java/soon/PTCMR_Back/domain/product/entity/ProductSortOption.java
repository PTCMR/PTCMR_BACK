package soon.PTCMR_Back.domain.product.entity;

import java.util.stream.Stream;

public enum ProductSortOption {
    EXPIRATION_DATE_ASC,
    EXPIRATION_DATE_DESC,
    NAME_ASC,
    NAME_DESC,
    CREATE_DATE_ASC,
    CREATE_DATE_DESC,
    CATEGORY_ASC,
    CATEGORY_DESC;

    public static ProductSortOption toSortOption(String option) {
        return Stream.of(ProductSortOption.values())
            .filter(type -> type.toString().equals(option.toUpperCase()))
            .findFirst()
            .get(); //  TODO 더 고민 필요
    }
}
