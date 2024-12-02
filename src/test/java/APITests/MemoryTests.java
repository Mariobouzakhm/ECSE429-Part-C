package APITests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class MemoryTests {
    public static long createTodo(String title, boolean status, String description) {
        Runtime runtime = Runtime.getRuntime();

        Runnable createCall = () -> {
            RequestSpecification request = given();
            request.header("Content-Type", "application/json");
            request.header("Accept", "application/json");

            JSONObject paramsMap = new JSONObject();
            paramsMap.put("title", title);
            paramsMap.put("description", description);
            paramsMap.put("doneStatus", status);
            request.body(paramsMap.toJSONString());
            Response response = request.post("/todos");
        };

        System.gc();
        long beforeCall = runtime.totalMemory() - runtime.freeMemory();

        createCall.run();

        System.gc();
        long afterCall = runtime.totalMemory() - runtime.freeMemory();

        return afterCall - beforeCall;
    }

    public static long createNTodo(int n, String title, boolean status, String description) {
        Runtime runtime = Runtime.getRuntime();

        Runnable createCall = () -> {
            RequestSpecification request = given();
            request.header("Content-Type", "application/json");
            request.header("Accept", "application/json");

            for(int i = 0; i < n; i++) {
                JSONObject paramsMap = new JSONObject();
                paramsMap.put("title", title+" "+i);
                paramsMap.put("description", description+" "+i);
                paramsMap.put("doneStatus", status);
                request.body(paramsMap.toJSONString());
            }

        };

        System.gc();
        long beforeCall = runtime.totalMemory() - runtime.freeMemory();

        createCall.run();

        System.gc();
        long afterCall = runtime.totalMemory() - runtime.freeMemory();

        return afterCall - beforeCall;
    }

    public static long modifyTodoDescription(int todoId, String new_description) {
        Runtime runtime = Runtime.getRuntime();

        Runnable createCall = () -> {
            JSONObject todoMap = new JSONObject();
            todoMap.put("description", new_description);

            RequestSpecification request = RestAssured.given();
            request.header("Content-Type", "application/json");
            request.header("Accept", "application/json");
            request.body(todoMap.toJSONString());

            Response response = request.post("/todos/"+String.valueOf(todoId));
        };

        System.gc();
        long beforeCall = runtime.totalMemory() - runtime.freeMemory();

        createCall.run();

        System.gc();
        long afterCall = runtime.totalMemory() - runtime.freeMemory();

        return afterCall - beforeCall;
    }

    public static long modifyNTodoDescription(int n, int todoId, String new_description) {
        Runtime runtime = Runtime.getRuntime();

        Runnable createCall = () -> {
            for(int i = 0; i < n; i++) {
                JSONObject todoMap = new JSONObject();
                todoMap.put("description", new_description+" "+i);

                RequestSpecification request = RestAssured.given();
                request.header("Content-Type", "application/json");
                request.header("Accept", "application/json");
                request.body(todoMap.toJSONString());

                Response response = request.post("/todos/"+String.valueOf(todoId+i));
            }
        };

        System.gc();
        long beforeCall = runtime.totalMemory() - runtime.freeMemory();

        createCall.run();

        System.gc();
        long afterCall = runtime.totalMemory() - runtime.freeMemory();

        return afterCall - beforeCall;
    }

    public static long deleteTodo(int id) {
        Runtime runtime = Runtime.getRuntime();

        Runnable createCall = () -> {
            Response response = given().when().delete("/todos/"+id);
        };

        System.gc();
        long beforeCall = runtime.totalMemory() - runtime.freeMemory();

        createCall.run();

        System.gc();
        long afterCall = runtime.totalMemory() - runtime.freeMemory();

        return afterCall - beforeCall;
    }

    public static long deleteNTodo(int n, int id) {
        Runtime runtime = Runtime.getRuntime();

        Runnable createCall = () -> {
            for (int i =0; i < n; i++) {
                Response response = given().when().delete("/todos/"+(id+i));
            }
        };

        System.gc();
        long beforeCall = runtime.totalMemory() - runtime.freeMemory();

        createCall.run();

        System.gc();
        long afterCall = runtime.totalMemory() - runtime.freeMemory();

        return afterCall - beforeCall;
    }
}

