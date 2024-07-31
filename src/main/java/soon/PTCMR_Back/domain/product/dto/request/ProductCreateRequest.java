package soon.PTCMR_Back.domain.product.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public class ProductCreateRequest {

    @NotEmpty(message = "상품명을 입력하세요.")
    private String name;

    @NotNull(message = "유통기간을 입력하세요.")
    @FutureOrPresent(message = "유통기간은 현재 시점 이후여야 합니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime expirationDate;

    @Positive(message = "수량을 입력하세요.")
    private int quantity;

    @NotNull(message = "존재하지 않는 url 입니다.")
    private String imageUrl;

    @NotEmpty(message = "보관방법을 입력하세요.")
    private String storageType;

    private boolean repurchase;

    @NotEmpty(message = "설명을 입력하세요.")
    private String description;

    private Long teamId;
}
