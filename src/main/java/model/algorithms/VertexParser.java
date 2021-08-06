package model.algorithms;

import com.opencsv.CSVReader;
import model.dto.GraphDTO;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.lang.Long.parseLong;

public class VertexParser {
    private static final String[] columns = {"node","lat","lon"};

    private VertexParser(){

    }

    /**
     * Creates a list with vertex dtos
     *
     * @param file csv file with nodes.
     *             format: id, latitude, longitude
     * @return list with vertex dtos
     * @throws IOException error while opening or reading file
     */
    public static List<GraphDTO.vertex> parse(File file) throws IOException {
        List<GraphDTO.vertex> vertices = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                vertices.add(
                        GraphDTO.vertex.create(
                                parseLong(values[0]),
                                parseDouble(values[1]),
                                parseDouble(values[2])
                        )
                );
            }
        }

        return vertices;
    }
}
