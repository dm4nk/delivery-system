package model.dto;

import lombok.Value;

/**
 * Class which contains every dto of this app
 */
public enum DTO {
    ;

    @Value(staticConstructor = "create")
    public static class vertex {
        String id;
        String lat;
        String lon;
    }

    @Value(staticConstructor = "create")
    public static class edge {
        String id;
        String sourceVertex;
        String targetVertex;
        String length;
        String streetType;
        String maxSpeed;
    }

    @Value(staticConstructor = "create")
    public static class order {
        String id;
        String date;
        double lat;
        double lon;
    }
}
