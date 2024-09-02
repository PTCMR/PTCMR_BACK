package soon.PTCMR_Back.domain.category.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;


}
