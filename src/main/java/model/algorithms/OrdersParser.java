package model.algorithms;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.NonNull;
import model.dto.DTO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

/**
 * Used to parse orders to schedules
 */
public class OrdersParser {
    private OrdersParser() {

    }

    /**
     * Create a DTO list from .json file of orders.
     *
     * @param file json file with orders, contains JSON array with orders, containing id, dispatch time, latitude, longitude
     * @return list of order DTOs
     * @throws FileNotFoundException if file is not .json format
     */
    public static List<DTO.order> parse(@NonNull File file) throws FileNotFoundException {
        if (!file.getName().endsWith(".json")) throw new FileNotFoundException(file.getName() + " is not a json file");
        Gson g = new Gson();
        return g.fromJson(new FileReader(file.getPath()), new TypeToken<List<DTO.order>>() {
        }.getType());
    }
}
