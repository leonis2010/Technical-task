package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите путь к файлу tests.json и values.json");
        // S:\My_projects\ТЗ\task3\tests.json S:\My_projects\ТЗ\task3\values.json- для теста
        ObjectMapper objectMapper = new ObjectMapper();

        // Чтение данных из файлов tests.json и values.json
        try {
            Scanner scanner = new Scanner(System.in);
            String pathForFile1 = scanner.nextLine();
            String pathForFile2 = scanner.nextLine();
            scanner.close();

            JsonNode testsJson = objectMapper.readTree(new File(pathForFile1));
            JsonNode valuesJson = objectMapper.readTree(new File(pathForFile2));
            // Заполнение поля "value" в tests.json на основе значений из values.json
            fillFieldValue(testsJson, valuesJson);

            // Сохранение изменений в новый файл report.json
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("report.json"), testsJson);

            System.out.println("Файл report.json успешно создан!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Рекурсивная функция для заполнения поля "value" в tests.json на основе значений из values.json
    private static void fillFieldValue(JsonNode testsJson, JsonNode valuesJson) {
        if (testsJson.isArray()) {
            for (JsonNode element : testsJson) {
                fillFieldValue(element, valuesJson);
            }
        } else if (testsJson.isObject()) {
            JsonNode idNode = testsJson.get("id");
            if (idNode!= null && idNode.isInt()) {
                int id = idNode.asInt();
                JsonNode valueNode = findValueNode(id, valuesJson);
                if (valueNode!= null && valueNode.has("value")) {
                    ((ObjectNode) testsJson).put("value", valueNode.get("value").asText());
                }
            }
            JsonNode valuesArray = testsJson.get("values");
            if (valuesArray != null && valuesArray.isArray()) {
                for (JsonNode valuesNode : valuesArray) {
                    idNode = valuesNode.get("id");
                    if (idNode != null && idNode.isInt()) {
                        int id = idNode.asInt();
                        JsonNode valueNode = findValueNode(id, valuesJson);
                        if (valueNode != null && valueNode.has("value")) {
                            ((ObjectNode) valuesNode).put("value", valueNode.get("value").asText());
                        }
                    }
                }
            }
            // Дробим на элементы
            for (JsonNode child : testsJson) {
                fillFieldValue(child, valuesJson);
            }
        }
    }

    // Вспомогательная функция для поиска узла с заданным значением "id" в values.json
    private static JsonNode findValueNode(int id, JsonNode valuesJson) {
        ArrayNode valuesArrayNode = (ArrayNode) valuesJson.get("values");
        for (JsonNode valueNode : valuesArrayNode) {
            JsonNode idNode = valueNode.get("id");
            if (idNode != null && idNode.isInt() && idNode.asInt() == id) {
                return valueNode;
            }
        }
        return null;
    }
}