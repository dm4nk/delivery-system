package model.dto;

import lombok.Value;

/**
 * Class which contains every dto of this app
 */
public enum DTO {
    ;

    @Value(staticConstructor = "create")
    public static class vertex {
        String
                id,
                lat,
                lon;
    }

    @Value(staticConstructor = "create")
    public static class edge {
        String
                id,
                sourceVertex,
                targetVertex,
                length,
                streetType,
                maxSpeed;
    }

    @Value(staticConstructor = "create")
    public static class order {
        String id;
        String date;
        double lat;
        double lon;
    }
}
