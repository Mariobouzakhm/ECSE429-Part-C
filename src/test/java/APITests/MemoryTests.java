package APITests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;

public class MemoryTests {
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

                Response response = request.post("/todos");
            }

        };

        long beforeCall = runtime.totalMemory() - runtime.freeMemory();

        createCall.run();

        long afterCall = runtime.totalMemory() - runtime.freeMemory();

        return Math.abs(afterCall - beforeCall);
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

        long beforeCall = runtime.totalMemory() - runtime.freeMemory();

        createCall.run();

        long afterCall = runtime.totalMemory() - runtime.freeMemory();

        return Math.abs(afterCall - beforeCall);
    }

    public static long deleteNTodo(int n, int id) {
        Runtime runtime = Runtime.getRuntime();

        Runnable createCall = () -> {
            for (int i =0; i < n; i++) {
                Response response = given().when().delete("/todos/"+(id+i));
            }
        };

        long beforeCall = runtime.totalMemory() - runtime.freeMemory();

        createCall.run();

        long afterCall = runtime.totalMemory() - runtime.freeMemory();

        return Math.abs(afterCall - beforeCall);
    }

    public static long createNCategories(int n, String title, String description) {
        Runtime runtime = Runtime.getRuntime();

        Runnable createCall = () -> {
            RequestSpecification request = given();
            request.header("Content-Type", "application/json");
            request.header("Accept", "application/json");

            for(int i = 0; i < n; i++) {
                JSONObject paramsMap = new JSONObject();
                paramsMap.put("title", title+" "+i);
                paramsMap.put("description", description+" "+i);
                request.body(paramsMap.toJSONString());

                Response response = request.post("/categories");
            }

        };

        long beforeCall = runtime.totalMemory() - runtime.freeMemory();

        createCall.run();

        long afterCall = runtime.totalMemory() - runtime.freeMemory();

        return Math.abs(afterCall - beforeCall);
    }

    public static long modifyNCategoriesDescriptions(int n, int todoId, String new_description) {
        Runtime runtime = Runtime.getRuntime();

        Runnable createCall = () -> {
            for(int i = 0; i < n; i++) {
                JSONObject todoMap = new JSONObject();
                todoMap.put("description", new_description+" "+i);

                RequestSpecification request = RestAssured.given();
                request.header("Content-Type", "application/json");
                request.header("Accept", "application/json");
                request.body(todoMap.toJSONString());

                Response response = request.post("/categories/"+String.valueOf(todoId+i));
            }
        };

        long beforeCall = runtime.totalMemory() - runtime.freeMemory();

        createCall.run();

        long afterCall = runtime.totalMemory() - runtime.freeMemory();

        return Math.abs(afterCall - beforeCall);
    }

    public static long deleteNCategories(int n, int id) {
        Runtime runtime = Runtime.getRuntime();

        Runnable createCall = () -> {
            for (int i =0; i < n; i++) {
                Response response = given().when().delete("/categories/"+(id+i));
            }
        };

        long beforeCall = runtime.totalMemory() - runtime.freeMemory();

        createCall.run();

        long afterCall = runtime.totalMemory() - runtime.freeMemory();

        return Math.abs(afterCall - beforeCall);
    }
}

