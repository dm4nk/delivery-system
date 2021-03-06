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

public class VertexParser {

    private VertexParser() {

    }

    /**
     * Creates a list with vertex dtos
     *
     * @param file csv file with nodes.
     *             format: id, latitude, longitude
     * @return list with vertex dtos
     * @throws IOException error while opening or reading file
     */
    public static List<DTO.vertex> parse(@NonNull File file) throws IOException {
        if (!file.getName().endsWith(".csv")) throw new FileNotFoundException(file.getName() + " is not a .csv format");
        List<DTO.vertex> vertices = new ArrayList<>();

        FileReader fileReader = new FileReader(file);
        CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build();

        String[] values;
        while ((values = csvReader.readNext()) != null) {
            vertices.add(
                    DTO.vertex.create(
                            values[0],
                            values[1],
                            values[2]
                    )
            );
        }

        csvReader.close();
        fileReader.close();

        return vertices;
    }
}
