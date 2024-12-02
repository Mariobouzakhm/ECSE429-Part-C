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
        }

        return result;
    }

    public static long modifyTodoDescriptionNTimes(int n, int todoId, String new_description) {
        int result = 0;
        for(int i = 0; i < n; i++) {
            JSONObject todoMap = new JSONObject();
            todoMap.put("description", new_description + " " + i);

            RequestSpecification request = RestAssured.given();
            request.header("Content-Type", "application/json");
            request.header("Accept", "application/json");
            request.body(todoMap.toJSONString());

            long startTime = System.currentTimeMillis();
            Response response = request.post("/todos/"+String.valueOf(todoId+i));
            long endTime = System.currentTimeMillis();

            result += (endTime - startTime);
        }

        return result;
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

    public static long createNCategory(int n, String title, String description) {
        RequestSpecification request = given();
        request.header("Content-Type", "application/json");
        request.header("Accept", "application/json");

        long result = 0;

        for(int i = 0; i < n; i++) {
            JSONObject categoriesMap = new JSONObject();
            categoriesMap.put("title", title + " " + i);
            categoriesMap.put("description", description+ " " + i);
            request.body(categoriesMap.toJSONString());

            long startTime = System.currentTimeMillis();
            Response response = request.post("/categories");
            long endTime = System.currentTimeMillis();

            result += (endTime - startTime);
        }

        return result;
    }

    public static long modifyNCategoryDescription(int n, int todoId, String new_description) {
        int result = 0;
        for(int i = 0; i < n; i++) {
            JSONObject todoMap = new JSONObject();
            todoMap.put("description", new_description + " " + i);

            RequestSpecification request = RestAssured.given();
            request.header("Content-Type", "application/json");
            request.header("Accept", "application/json");
            request.body(todoMap.toJSONString());

            long startTime = System.currentTimeMillis();
            Response response = request.post("/categories/"+String.valueOf(todoId+i));
            long endTime = System.currentTimeMillis();

            result += (endTime - startTime);
        }

        return result;
    }

    public static long deleteNCategories(int n, int id) {
        long result = 0;

        for(int i = 0; i < n; i++) {
            long startTime = System.currentTimeMillis();

            Response response = given().when().delete("/categories/"+(id+i));

            long endTime = System.currentTimeMillis();

            result += (endTime - startTime);
        }

        return result;
    }
}
