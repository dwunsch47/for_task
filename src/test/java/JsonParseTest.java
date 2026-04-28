import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.File;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class JsonParseTest {
    static File jsonTestFilesFolder = new File(Utils.basePath, "json");

    @BeforeAll
    static void prepare() {
        jsonTestFilesFolder.mkdirs();
    }

    @AfterAll
    static void finishUp() {
        Utils.deleteFolderAndFiles(jsonTestFilesFolder);
    }
    @Test
    void givenJson_whenParse_thenReturnEmployeeString() {
        final String testFileName = "jsonDataTest1.json";
        final String testData = "[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"firstName\": \"John\",\n" +
                "    \"lastName\": \"Smith\",\n" +
                "    \"country\": \"USA\",\n" +
                "    \"age\": 25\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 2,\n" +
                "    \"firstName\": \"Inav\",\n" +
                "    \"lastName\": \"Petrov\",\n" +
                "    \"country\": \"RU\",\n" +
                "    \"age\": 23\n" +
                "  }\n" +
                "]";
        File filePath = new File(jsonTestFilesFolder, testFileName);
        Utils.createFileAndAddData(filePath, testData);

        String result = Main.readString(filePath);

        Assertions.assertEquals(result, testData);
    }
    @Test
    void givenJson_whenParse_thenReturnEmployeeString_Hamcrest() {
        final String testFileName = "jsonDataTest1.json";
        final String testData = "[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"firstName\": \"John\",\n" +
                "    \"lastName\": \"Smith\",\n" +
                "    \"country\": \"USA\",\n" +
                "    \"age\": 25\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 2,\n" +
                "    \"firstName\": \"Inav\",\n" +
                "    \"lastName\": \"Petrov\",\n" +
                "    \"country\": \"RU\",\n" +
                "    \"age\": 23\n" +
                "  }\n" +
                "]";
        File filePath = new File(jsonTestFilesFolder, testFileName);
        Utils.createFileAndAddData(filePath, testData);

        String result = Main.readString(filePath);

        assertThat(result, equalTo(testData));
    }

    @Test
    void givenJson_whenParse_thenResultNotEmpty_Hamcrest() {
        final String testFileName = "jsonDataTest1.json";
        final String testData = "[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"firstName\": \"John\",\n" +
                "    \"lastName\": \"Smith\",\n" +
                "    \"country\": \"USA\",\n" +
                "    \"age\": 25\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 2,\n" +
                "    \"firstName\": \"Inav\",\n" +
                "    \"lastName\": \"Petrov\",\n" +
                "    \"country\": \"RU\",\n" +
                "    \"age\": 23\n" +
                "  }\n" +
                "]";
        File filePath = new File(jsonTestFilesFolder, testFileName);
        Utils.createFileAndAddData(filePath, testData);

        String result = Main.readString(filePath);

        assertThat(result, not(isEmptyOrNullString()));
    }
}
