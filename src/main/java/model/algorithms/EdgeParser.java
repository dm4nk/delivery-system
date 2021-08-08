package model.algorithms;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.NonNull;
import model.dto.DTO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EdgeParser {
    private EdgeParser() {

    }

    /**
     * Creates a list with edges dtos
     *
     * @param file .csv file with edges
     *             format: id, id of source vertex, id of target vertex, length in meters, type of street according to speed limit, speed limit
     * @return list of edge dtos
     * @throws IOException error while opening or reading file
     */
    public static List<DTO.edge> parse(@NonNull File file) throws IOException {
        if (!file.getName().endsWith(".csv")) throw new FileNotFoundException(file.getName() + " is not a .csv format");
        List<DTO.edge> edges = new ArrayList<>();

        FileReader fileReader = new FileReader(file);
        CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build();

        String[] values;
        while ((values = csvReader.readNext()) != null) {
            edges.add(
                    DTO.edge.create(
                            values[0],
                            values[1],
                            values[2],
                            values[3],
                            values[4],
                            values[5]
                    )
            );
        }

        csvReader.close();
        fileReader.close();

        return edges;
    }
}
