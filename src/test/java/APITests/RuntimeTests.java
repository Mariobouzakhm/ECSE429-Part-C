package APITests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RuntimeTests {
    public static long createTodo(String title, boolean status, String description) {
        RequestSpecification request = given();
        request.header("Content-Type", "application/json");
        request.header("Accept", "application/json");

        JSONObject paramsMap = new JSONObject();
        paramsMap.put("title", title);
        paramsMap.put("description", description);
        paramsMap.put("doneStatus", status);

        request.body(paramsMap.toJSONString());

        long startTime = System.currentTimeMillis();
        Response response = request.post("/todos");
        long endTime = System.currentTimeMillis();
        Map<String, Object> responseMap = response.then().extract().jsonPath().getMap("$");

        int status_code = response.statusCode();

        System.out.println("Status Code: " + status_code);

        return endTime - startTime;
    }

    public static long deleteTodo(int id) {
        long startTime = System.currentTimeMillis();

        Response response = given().when().delete("/todos/"+id);

        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    public static long deleteNTodo(int n, int id) {
        long result = 0;

        for(int i = 0; i < n; i++) {
            long startTime = System.currentTimeMillis();

            Response response = given().when().delete("/todos/"+(id+i));

            long endTime = System.currentTimeMillis();

            result += (endTime - startTime);
        }

        return result;
    }

    public static long createNTodo(int n, String title, boolean status, String description) {
        RequestSpecification request = given();
        request.header("Content-Type", "application/json");
        request.header("Accept", "application/json");

        long result = 0;


        for (int i = 0; i < n; i++) {
            JSONObject paramsMap = new JSONObject();
            paramsMap.put("title", title + " " + i);
            paramsMap.put("description", description + " " + i);
            paramsMap.put("doneStatus", status);

            request.body(paramsMap.toJSONString());

            long startCurrentTime = System.currentTimeMillis();
            Response response = request.post("/todos");
            long endCurrentTime = System.currentTimeMillis();

            result += (endCurrentTime - startCurrentTime);

            int status_code = response.statusCode();
            System.out.println("Status Code: " + i + " " + status_code);
        }

        return result;
    }

    public static long modifyTodoDescription(int todoId, String new_description) {
        JSONObject todoMap = new JSONObject();
        todoMap.put("description", new_description);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.header("Accept", "application/json");
        request.body(todoMap.toJSONString());

        long startTime = System.currentTimeMillis();
        Response response = request.post("/todos/"+String.valueOf(todoId));
        long endTime = System.currentTimeMillis();

        Map<String, Object> responseMap = response.then().extract().jsonPath().getMap("$");

        int status_code = response.statusCode();
        System.out.println("Status Code: " + status_code);

        return endTime - startTime;
    }

    public static long modifyTodoDescriptionNTimes(int n, int todoId, String new_description) {
        int result = 0;
        for(int i = 0; i < n; i++) {
            JSONObject todoMap = new JSONObject();
            todoMap.put("description", new_description + " " + n);

            RequestSpecification request = RestAssured.given();
            request.header("Content-Type", "application/json");
            request.header("Accept", "application/json");
            request.body(todoMap.toJSONString());

            long startTime = System.currentTimeMillis();
            Response response = request.post("/todos/"+String.valueOf(todoId));
            long endTime = System.currentTimeMillis();

            result += (endTime - startTime);
        }

        return result;
    }

    public static long createCategory(String title, String description) {
        JSONObject categoriesMap = new JSONObject();
        categoriesMap.put("title", title);
        categoriesMap.put("description", description);

        RequestSpecification request = given();
        request.header("Content-Type", "application/json");
        request.header("Accept", "application/json");
        request.body(categoriesMap.toJSONString());

        long startTime = System.currentTimeMillis();
        Response response = request.post("/categories");
        long endTime = System.currentTimeMillis();

        int status_code = response.statusCode();
        System.out.println("Status Code: " + status_code);

        return endTime - startTime;
    }

    public static long createNCategory(int n, String title, String description) {
        JSONObject categoriesMap = new JSONObject();
        categoriesMap.put("title", title + " " + n);
        categoriesMap.put("description", description+ " " + n);

        RequestSpecification request = given();
        request.header("Content-Type", "application/json");
        request.header("Accept", "application/json");
        request.body(categoriesMap.toJSONString());

        long result = 0;

        for(int i = 0; i < n; i++) {
            long startTime = System.currentTimeMillis();
            Response response = request.post("/categories");
            long endTime = System.currentTimeMillis();

            result += (endTime - startTime);
        }

        return result;
    }
}
