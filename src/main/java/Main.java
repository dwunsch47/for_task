import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static void main(String[] args) {
        // csv
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String csvFileName = "data.csv";
        List<Employee> csvList = parseCSV(columnMapping, new File(csvFileName));
        String csvJson = "data.json";
        toJsonAndSave(csvList, csvJson);

        // xml
        String xmlFileName = "data.xml";
        List<Employee> xmlList = parseXML(new File(xmlFileName));
        String xmlJson = "data2.json";
        toJsonAndSave(xmlList, xmlJson);

        // json
        String jsonFileName = "csvData.json";
        String json = readString(new File(jsonFileName));
        List<Employee> jsonList = jsonToList(json);
        if (jsonList.size() >= 2) {
            System.out.println(jsonList.get(0).toString() + '\n' + jsonList.get(1).toString());
        }
    }

    static List<Employee> parseCSV(String[] cMapping, File fileName) {
        List<Employee> result = null;
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy =
                    new ColumnPositionMappingStrategy<>();

            strategy.setType(Employee.class);
            strategy.setColumnMapping(cMapping);

            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(strategy)
                    .build();

            result = csv.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    static List<Employee> parseXML(File fileName) {
        Node root = null;
        List<Employee> result = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(fileName);

            root = doc.getDocumentElement();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (root == null) { // probably not necessary, but doesn't hurt
            return result;
        }

        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currNode = nodeList.item(i);
            if (currNode.getNodeType() == Node.ELEMENT_NODE
                    && currNode.getNodeName().equals("employee")) {
                Element currElement = (Element) currNode;

                long id = Long.parseLong(currElement.getElementsByTagName("id").item(0).getTextContent());
                String firstName = currElement.getElementsByTagName("firstName").item(0).getTextContent();
                String lastName = currElement.getElementsByTagName("lastName").item(0).getTextContent();
                String country = currElement.getElementsByTagName("country").item(0).getTextContent();
                int age = Integer.parseInt(currElement.getElementsByTagName("age").item(0).getTextContent());

                result.add(new Employee(id, firstName, lastName, country, age));
            }
        }
        return result;
    }

    static void toJsonAndSave(List<Employee> list, String fileName) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(gson.toJson(list));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String readString(File fileName) {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            return br.readAllAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    static List<Employee> jsonToList(String jsonString) {
        Type listType = new TypeToken<List<Employee>>(){}.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(jsonString, listType);
    }
}


