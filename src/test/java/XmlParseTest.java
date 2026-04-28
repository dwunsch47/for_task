import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.File;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class XmlParseTest {
    static File xmlTestFilesFolder = new File(Utils.basePath, "xml");

    @BeforeAll
    static void prepare() {
        xmlTestFilesFolder.mkdirs();
    }

    @AfterAll
    static void finishUp() {
        Utils.deleteFolderAndFiles(xmlTestFilesFolder);
    }

    @Test
    void giverXml_whenParse_thenReturnEmployeeList() {
        final String testFileName = "xmlDataTest1.xml";
        final String testData = "<staff>\n" +
                "    <employee>\n" +
                "        <id>1</id>\n" +
                "        <firstName>John</firstName>\n" +
                "        <lastName>Smith</lastName>\n" +
                "        <country>USA</country>\n" +
                "        <age>25</age>\n" +
                "    </employee>\n" +
                "    <employee>\n" +
                "        <id>2</id>\n" +
                "        <firstName>Inav</firstName>\n" +
                "        <lastName>Petrov</lastName>\n" +
                "        <country>RU</country>\n" +
                "        <age>23</age>\n" +
                "    </employee>\n" +
                "</staff>";
        List<Employee> expectedOutput = List.of(
                new Employee(1, "John", "Smith", "USA", 25),
                new Employee(2, "Inav", "Petrov", "RU", 23)
        );
        File filePath = new File(xmlTestFilesFolder, testFileName);
        Utils.createFileAndAddData(filePath, testData);

        List<Employee> result = Main.parseXML(filePath);

        Assertions.assertEquals(result, expectedOutput);
    }

    @Test
    void giverXml_whenParse_thenReturnEmployeeList_Hamcrest() {
        final String testFileName = "xmlDataTest2.xml";
        final String testData = "<staff>\n" +
                "    <employee>\n" +
                "        <id>1</id>\n" +
                "        <firstName>John</firstName>\n" +
                "        <lastName>Smith</lastName>\n" +
                "        <country>USA</country>\n" +
                "        <age>25</age>\n" +
                "    </employee>\n" +
                "    <employee>\n" +
                "        <id>2</id>\n" +
                "        <firstName>Inav</firstName>\n" +
                "        <lastName>Petrov</lastName>\n" +
                "        <country>RU</country>\n" +
                "        <age>23</age>\n" +
                "    </employee>\n" +
                "</staff>";
        List<Employee> expectedOutput = List.of(
                new Employee(1, "John", "Smith", "USA", 25),
                new Employee(2, "Inav", "Petrov", "RU", 23)
        );
        File filePath = new File(xmlTestFilesFolder, testFileName);
        Utils.createFileAndAddData(filePath, testData);

        List<Employee> result = Main.parseXML(filePath);

        assertThat(result, equalTo(expectedOutput));
    }
}
