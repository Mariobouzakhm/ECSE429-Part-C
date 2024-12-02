package APITests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

import static io.restassured.RestAssured.given;

public class CPUUsageTests {
    public static double createTodo(String title, boolean status, String description) {
        Runtime runtime = Runtime.getRuntime();
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        RequestSpecification request = given();
        request.header("Content-Type", "application/json");
        request.header("Accept", "application/json");

        JSONObject paramsMap = new JSONObject();
        paramsMap.put("title", title);
        paramsMap.put("description", description);
        paramsMap.put("doneStatus", status);
        request.body(paramsMap.toJSONString());

        Runnable createCall = () -> {
            Response response = request.post("/todos");
        };

        double beforeCall = osBean.getProcessCpuLoad();
        createCall.run();
        double afterCall = osBean.getProcessCpuLoad();

        return afterCall - beforeCall;
    }

    public static double createNTodo(int n, String title, boolean status, String description) {
        Runtime runtime = Runtime.getRuntime();
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
            }

        };

        double beforeCall = osBean.getProcessCpuLoad();
        createCall.run();
        double afterCall = osBean.getProcessCpuLoad();

        return afterCall - beforeCall;
    }

    public static double modifyTodoDescription(int todoId, String new_description) {
        Runtime runtime = Runtime.getRuntime();
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        Runnable createCall = () -> {
            JSONObject todoMap = new JSONObject();
            todoMap.put("description", new_description);

            RequestSpecification request = RestAssured.given();
            request.header("Content-Type", "application/json");
            request.header("Accept", "application/json");
            request.body(todoMap.toJSONString());

            Response response = request.post("/todos/"+String.valueOf(todoId));
        };

        double beforeCall = osBean.getProcessCpuLoad();
        createCall.run();
        double afterCall = osBean.getProcessCpuLoad();

        return afterCall - beforeCall;
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

        double beforeCall = osBean.getProcessCpuLoad();
        createCall.run();
        double afterCall = osBean.getProcessCpuLoad();

        return afterCall - beforeCall;
    }

    public static double deleteTodo(int id) {
        Runtime runtime = Runtime.getRuntime();
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        Runnable createCall = () -> {
            Response response = given().when().delete("/todos/"+id);
        };

        double beforeCall = osBean.getProcessCpuLoad();
        createCall.run();
        double afterCall = osBean.getProcessCpuLoad();

        return afterCall - beforeCall;
    }

    public static double deleteNTodo(int n, int id) {
        Runtime runtime = Runtime.getRuntime();
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        Runnable createCall = () -> {
            for (int i =0; i < n; i++) {
                Response response = given().when().delete("/todos/"+(id+i));
            }
        };

        double beforeCall = osBean.getProcessCpuLoad();
        createCall.run();
        double afterCall = osBean.getProcessCpuLoad();

        return afterCall - beforeCall;
    }
}
