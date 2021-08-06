package model.algorithms;

import com.opencsv.CSVReader;
import model.dto.GraphDTO;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

public class EedgeParser {
    private EedgeParser(){

    }

    /**
     * Creates a list with edges dtos
     *
     * @param file .csv file with edges
     *             format: id, id of source vertex, id of target vertex, length in meters, type of street according to speed limit, speed limit
     * @return list of edge dtos
     * @throws IOException error while opening or reading file
     */
    public static List<GraphDTO.edge> parse(File file) throws IOException {
        List<GraphDTO.edge> edges = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                edges.add(
                        GraphDTO.edge.create(
                                parseLong(values[0]),
                                parseLong(values[1]),
                                parseLong(values[2]),
                                parseDouble(values[3]),
                                parseInt(values[4]),
                                parseDouble(values[5])
                        )
                );
            }
        }
        return edges;
    }
}
