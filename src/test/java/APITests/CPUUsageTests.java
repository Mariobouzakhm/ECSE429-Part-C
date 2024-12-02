package APITests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import com.sun.management.OperatingSystemMXBean;

import static io.restassured.RestAssured.given;

public class CPUUsageTests {
    public static double createNTodo(int n, String title, boolean status, String description) {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

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

        createCall.run();
        double afterCall = osBean.getProcessCpuLoad();

        return afterCall;
    }

    public static double modifyNTodoDescription(int n, int todoId, String new_description) {
        Runtime runtime = Runtime.getRuntime();
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

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

        createCall.run();
        double afterCall = osBean.getProcessCpuLoad();

        return afterCall;
    }

    public static double deleteNTodo(int n, int id) {
        Runtime runtime = Runtime.getRuntime();
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        Runnable createCall = () -> {
            for (int i =0; i < n; i++) {
                Response response = given().when().delete("/todos/"+(id+i));
            }
        };

        createCall.run();
        double afterCall = osBean.getProcessCpuLoad();

        return afterCall;
    }

    public static double createNCategories(int n, String title, String description) {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

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

        createCall.run();
        double afterCall = osBean.getProcessCpuLoad();

        return afterCall;
    }

    public static double modifyNCategoriesDescription(int n, int todoId, String new_description) {
        Runtime runtime = Runtime.getRuntime();
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

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

        createCall.run();
        double afterCall = osBean.getProcessCpuLoad();

        return afterCall;
    }

    public static double deleteNCategories(int n, int id) {
        Runtime runtime = Runtime.getRuntime();
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        Runnable createCall = () -> {
            for (int i =0; i < n; i++) {
                Response response = given().when().delete("/categories/"+(id+i));
            }
        };

        createCall.run();
        double afterCall = osBean.getProcessCpuLoad();

        return afterCall;
    }
}
