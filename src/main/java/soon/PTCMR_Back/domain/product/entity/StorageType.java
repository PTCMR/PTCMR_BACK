package soon.PTCMR_Back.domain.product.entity;

import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum StorageType {
    REFRIGERATION, ROOM_TEMPERATURE, FROZEN;

    public static StorageType toStorageType(String storageType) {
        return Stream.of(StorageType.values())
            .filter(type -> type.toString().equals(storageType.toUpperCase()))
            .findFirst()
            .get(); //  TODO 더 고민 필요
    }
}
