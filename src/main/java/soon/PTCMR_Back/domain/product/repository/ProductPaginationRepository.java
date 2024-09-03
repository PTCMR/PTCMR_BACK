package soon.PTCMR_Back.domain.product.repository;

import static soon.PTCMR_Back.domain.product.entity.QProduct.product;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import soon.PTCMR_Back.domain.product.dto.ProductPaginationDto;
import soon.PTCMR_Back.domain.product.entity.ProductSortOption;
import soon.PTCMR_Back.domain.team.entity.Team;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ProductPaginationRepository {

    private final JPAQueryFactory queryFactory;
    public static final int PAGE_SIZE = 10;

    // TODO 카테고리 추가 시 수정
    public List<ProductPaginationDto> getProductList(Long productId, ProductSortOption option,
        String category, Team team) {
        final BooleanExpression condition = getProductCondition(productId, option);

        return queryFactory
            .select(Projections.constructor(ProductPaginationDto.class,
                product.id.as("productId"),
                product.name,
                product.expirationDate,
                product.quantity,
                product.imageUrl,
                Expressions.stringTemplate("function('str', {0})", product.status)
                    .as("status"),
                Expressions.stringTemplate("function('str', {0})", product.storageType)
                    .as("storageType"),
                product.createTime.as("createdDate")
            ))
            .from(product)
            .where(
                product.team.eq(team),
                condition
            )
            .orderBy(getOrderSpecifier(option))
            .limit(PAGE_SIZE + 1)
            .fetch();
    }

    private BooleanExpression getProductCondition(Long productId, ProductSortOption option) {
        if (productId == null) {
            return null;
        }

        return switch (option) {
            case EXPIRATION_DATE_ASC, NAME_ASC, CREATE_DATE_ASC -> product.id.goe(productId);
            default -> product.id.loe(productId);
        };
    }

    private OrderSpecifier<?> getOrderSpecifier(ProductSortOption option) {
        return switch (option) {
            case EXPIRATION_DATE_ASC -> product.expirationDate.asc();
            case NAME_ASC -> product.name.asc();
            case NAME_DESC -> product.name.desc();
            case CREATE_DATE_ASC -> product.createTime.asc();
            case CREATE_DATE_DESC -> product.createTime.desc();
            default -> product.expirationDate.desc();
        };
    }
}
