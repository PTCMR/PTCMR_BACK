package soon.PTCMR_Back.domain.category.repository;

import static soon.PTCMR_Back.domain.category.entity.QCategory.category;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import soon.PTCMR_Back.domain.category.dto.CategoryPaginationDto;
import soon.PTCMR_Back.domain.team.entity.Team;

@RequiredArgsConstructor
@Repository
public class CategoryPaginationRepository {

    private final JPAQueryFactory queryFactory;

    public static final int PAGE_SIZE = 5;

    public List<CategoryPaginationDto> getCategoryList(Long categoryId, Team team) {
        BooleanExpression categoryCondition = getCategoryCondition(categoryId);

        return queryFactory
            .select(Projections.constructor(CategoryPaginationDto.class,
                category.id.as("categoryId"),
                category.title
            ))
            .from(category)
            .where(
                category.team.eq(team),
                categoryCondition
            )
            .orderBy(category.title.asc())
            .limit(PAGE_SIZE + 1)
            .fetch();
    }

    private BooleanExpression getCategoryCondition(Long categoryId) {
        if (categoryId == null) {
            return null;
        }

        return category.id.loe(categoryId);
    }
}
