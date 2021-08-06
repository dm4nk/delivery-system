package model.dto;

import lombok.Value;

public enum GraphDTO {
    ;

    @Value(staticConstructor = "create")
    public static class vertex {
        long id;
        double lat;
        double lon;
    }

    @Value(staticConstructor = "create")
    public static class edge {
        long id;
        long sourceVertex;
        long targetVertex;
        double length;
        int streetType;
        double maxSpeed;
    }
}
