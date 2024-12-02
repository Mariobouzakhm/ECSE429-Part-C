package APITests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppTests {
    private Process p;
    private List<CSVWriterObject> runTimeResultsTodoCreate;
    private List<CSVWriterObject> runTimeResultsTodoDelete;
    private List<CSVWriterObject> runTimeResultsTodoModify;

    private List<CSVWriterObject> runTimeResultsCategory;

    private List<CSVWriterObject> memoryResultsCreate;
    private List<CSVWriterObject>memoryResultsDelete;
    private List<CSVWriterObject> memoryResultsModify;

    private List<CSVWriterObject> cpuUsageResultsCreate;
    private List<CSVWriterObject> cpuUsageResultsModify;
    private List<CSVWriterObject> cpuUsageResultsDelete;


    @BeforeAll
    public void setup() {
        runTimeResultsTodoCreate = new ArrayList<>();
        runTimeResultsTodoDelete = new ArrayList<>();
        runTimeResultsTodoModify = new ArrayList<>();

        runTimeResultsCategory = new ArrayList<>();

        memoryResultsCreate = new ArrayList<>();
        memoryResultsDelete = new ArrayList<>();
        memoryResultsModify = new ArrayList<>();

        cpuUsageResultsCreate = new ArrayList<>();
        cpuUsageResultsModify = new ArrayList<>();
        cpuUsageResultsDelete = new ArrayList<>();

        System.out.println("Lists Setup Done");
    }

    @BeforeEach
    public void beforeEach() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 4567;

        Runtime runtime = Runtime.getRuntime();
        try {
            this.p = runtime.exec("java -jar runTodoManagerRestAPI-1.5.5.jar");
            Thread.sleep(1000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        int status_code = 0;
        while(status_code != 200) {
            Response response = given().when().get("/docs");
            status_code = response.statusCode();

            if(status_code == 404) {
                try {
                    p.destroy();
                    Thread.sleep(500);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    this.p = runtime.exec("java -jar runTodoManagerRestAPI-1.5.5.jar");
                    Thread.sleep(1000);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @AfterEach
    public void afterEach() {
        try {
            p.destroy();
            Thread.sleep(500);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public void compileResults() {
        System.out.println("Compiling Results to .csv");
        createRunTimeCSV(runTimeResultsCategory, "csv/runTimeResultsCategory.csv");

        createRunTimeCSV(runTimeResultsTodoCreate, "csv/runTimeResultsTodoCreate.csv");
        createRunTimeCSV(runTimeResultsTodoModify, "csv/runTimeResultsTodoModify.csv");
        createRunTimeCSV(runTimeResultsTodoDelete, "csv/runTimeResultsTodoDelete.csv");

        createMemoryUsageCSV(memoryResultsCreate, "csv/memoryResultsCreate.csv");
        createMemoryUsageCSV(memoryResultsModify, "csv/memoryResultsModify.csv");
        createMemoryUsageCSV(memoryResultsDelete, "csv/memoryResultsDelete.csv");

        createCPUUsageCSV(cpuUsageResultsCreate, "csv/cpuUsageResultsCreate.csv");
        createCPUUsageCSV(cpuUsageResultsModify, "csv/cpuUsageResultsModify.csv");
        createCPUUsageCSV(cpuUsageResultsDelete, "csv/cpuUsageResultsDelete.csv");
    }

    @Test
    public void testRuntimeCreateTodoEmptyServer() {
        long createTodoTime = RuntimeTests.createTodo("Mock Title", true, "Mock Description");
        System.out.println("Create Todo Time: " + createTodoTime);

        runTimeResultsTodoCreate.add(new CSVWriterObject(1, "Create 1 Todo, Empty Server", createTodoTime, 0));
    }

    @Test
    public void testRuntimeCreate100TodoEmptyServer() {
        long create100TodoTime = RuntimeTests.createNTodo(100, "Mock Title", true, "Mock Description");
        System.out.println("Creation 20 Todo: " + create100TodoTime);

        runTimeResultsTodoCreate.add(new CSVWriterObject(2, "Create 100 Todo, Empty Server", create100TodoTime, 0));

    }

    @Test
    public void testRuntimeCreate20TodoEmptyServer() {
        long create20TodoTime = RuntimeTests.createNTodo(20, "Mock Title", true, "Mock Description");
        System.out.println("Creation 20 Todo: " + create20TodoTime);

        runTimeResultsTodoCreate.add(new CSVWriterObject(3, "Create 20 Todo, Empty Server", create20TodoTime, 0));
    }

    @Test
    public void testRuntimeCreate500TodoEmptyServer() {
        long create500TodoTime = RuntimeTests.createNTodo(500, "Mock Title", true, "Mock Description");
        System.out.println("Creation 500 Todo: " + create500TodoTime);

        runTimeResultsTodoCreate.add(new CSVWriterObject(4, "Create 500 Todo, Empty Server", create500TodoTime, 0));
    }


    @Test
    public void testRuntimeCreateTodo1000ServerServer() {
        createNTodos(1000);

        long createTodoTime = RuntimeTests.createTodo("Mock Title", true, "Mock Description");
        System.out.println("Create Todo Time: " + createTodoTime);

        runTimeResultsTodoCreate.add(new CSVWriterObject(5, "Create 1 Todo, 1000 Objects Server", createTodoTime, 0));
    }

    @Test
    public void testRuntimeCreate100Todo1000Server() {
        createNTodos(1000);

        long create20TodoTime = RuntimeTests.createNTodo(100, "Mock Title", true, "Mock Description");
        System.out.println("Creation 20 Todo: " + create20TodoTime);

        runTimeResultsTodoCreate.add(new CSVWriterObject(6, "Create 100 Todo, 1000 Objects Server", create20TodoTime, 0));

    }

    @Test
    public void testRuntimeCreate20Todo1000Server() {
        createNTodos(1000);

        long create20TodoTime = RuntimeTests.createNTodo(20, "Mock Title", true, "Mock Description");
        System.out.println("Creation 20 Todo: " + create20TodoTime);

        runTimeResultsTodoCreate.add(new CSVWriterObject(7, "Create 20 Todo, 1000 Objects Server", create20TodoTime, 0));
    }

    @Test
    public void testRuntimeCreate500Todo1000Server() {
        createNTodos(1000);

        long create20TodoTime = RuntimeTests.createNTodo(500, "Mock Title", true, "Mock Description");
        System.out.println("Creation 500 Todo: " + create20TodoTime);

        runTimeResultsTodoCreate.add(new CSVWriterObject(8, "Create 500 Todo, 1000 Objects Server", create20TodoTime, 0));
    }

    @Test
    public void testRuntimeCreateCategoryEmptyServer() {
        long result = RuntimeTests.createCategory("Mock Title", "Mock Description");
        System.out.println("Creation Category: " + result);

        runTimeResultsCategory.add(new CSVWriterObject(1, "Create 1 Category, Empty Server", result, 0));

    }

    @Test
    public void testRuntimeCreate100CategoryEmptyServer() {
        long result = RuntimeTests.createNCategory(100, "Mock Title", "Mock Description");
        System.out.println("Creation 100 Categories: " + result);

        runTimeResultsCategory.add(new CSVWriterObject(2, "Create 100 Categories, Empty", result, 0));
    }

    @Test
    public void testRuntimeCreate20CategoryEmptyServer() {
        long result = RuntimeTests.createNCategory(20, "Mock Title", "Mock Description");
        System.out.println("Creation 100 Categories: " + result);

        runTimeResultsCategory.add(new CSVWriterObject(3, "Create 20 Categories, Empty", result, 0));
    }

    @Test
    public void testRuntimeCreate500CategoryEmptyServer() {
        createNCategories(1000);
        long result = RuntimeTests.createNCategory(500, "Mock Title", "Mock Description");
        System.out.println("Creation 100 Categories: " + result);

        runTimeResultsCategory.add(new CSVWriterObject(4, "Create 500 Categories, Empty Server", result, 0));
    }

    @Test
    public void testRuntimeCreateCategory1000Server() {
        createNCategories(1000);
        long result = RuntimeTests.createCategory("Mock Title", "Mock Description");
        System.out.println("Creation Category: " + result);

        runTimeResultsCategory.add(new CSVWriterObject(5, "Create 1 Category, 1000 Objects Existing", result, 0));
    }
    @Test
    public void testRuntimeCreate100Category1000Server() {
        createNCategories(1000);
        long result = RuntimeTests.createNCategory(100, "Mock Title", "Mock Description");
        System.out.println("Creation 100 Categories: " + result);

        runTimeResultsCategory.add(new CSVWriterObject(6, "Create 100 Categories, 1000 Objects Existing", result, 0));
    }

    @Test
    public void testRuntimeCreate20Category1000Server() {
        createNCategories(1000);
        long result = RuntimeTests.createNCategory(20, "Mock Title", "Mock Description");
        System.out.println("Creation 100 Categories: " + result);

        runTimeResultsCategory.add(new CSVWriterObject(7, "Create 20 Categories, 1000 Objects Existing", result, 0));
    }

    @Test
    public void testRuntimeCreate500Category1000Server() {
        createNCategories(1000);
        long result = RuntimeTests.createNCategory(500, "Mock Title", "Mock Description");
        System.out.println("Creation 100 Categories: " + result);

        runTimeResultsCategory.add(new CSVWriterObject(8, "Create 500 Categories, 1000 Objects Existing", result, 0));
    }

    @Test
    public void testRuntimeDelete1Todo() {
        createNTodos(100);

        long result = RuntimeTests.deleteTodo(1);
        runTimeResultsTodoDelete.add(new CSVWriterObject(1, "Delete 1 Todo, 100 Existing", result, 0));
    }

    @Test
    public void testRuntimeDelete50Todo() {
        createNTodos(100);

        long result = RuntimeTests.deleteNTodo(50, 1);
        runTimeResultsTodoDelete.add(new CSVWriterObject(2, "Delete 50 Todo, 100 Existing", result, 0));
    }

    @Test
    public void testRuntimeDelete1Todo100Existing() {
        createNTodos(1000);

        long result = RuntimeTests.deleteTodo(1);
        runTimeResultsTodoDelete.add(new CSVWriterObject(3, "Delete 1 Todo, 1000 Existing", result, 0));
    }

    @Test
    public void testRuntimeDelete250Todo1000Existing() {
        createNTodos(1000);

        long result = RuntimeTests.deleteNTodo(250, 1);
        runTimeResultsTodoDelete.add(new CSVWriterObject(4, "Delete 250 Todo, 1000 Existing", result, 0));
    }

    @Test
    public void testRuntimeModify1Todo() {
        createNTodos(100);
        long result = RuntimeTests.modifyTodoDescription(1, "Mock Description 2");
        runTimeResultsTodoModify.add(new CSVWriterObject(1, "Modify 1 Todo, 100 Existing", result, 0));
    }
    @Test
    public void testRuntimeModify50Todo() {
        createNTodos(100);
        long result = RuntimeTests.modifyTodoDescriptionNTimes(50, 1, "Mock Description 2");
        runTimeResultsTodoModify.add(new CSVWriterObject(2, "Modify 50 Todo, 100 Existing", result, 0));
    }
    @Test
    public void testRuntimeModify1Todo100Existing() {
        createNTodos(100);
        long result = RuntimeTests.modifyTodoDescription(1, "Mock Description 2");
        runTimeResultsTodoModify.add(new CSVWriterObject(3, "Modify 1 Todo, 1000 Existing", result, 0));
    }
    @Test
    public void testRuntimeModify250Todo1000Existing() {
        createNTodos(100);
        long result = RuntimeTests.modifyTodoDescriptionNTimes(250, 1, "Mock Description 2");
        runTimeResultsTodoModify.add(new CSVWriterObject(4, "Modify 250 Todo, 1000 Existing", result, 0));
    }

    @Test
    public void testCPUUsageCreate1Todo() {
        double result = CPUUsageTests.createTodo("Mock Title", true, "Mock Description" );
        cpuUsageResultsCreate.add(new CSVWriterObject(1, "Create 1 Todo, Empty Server", 0, result));
    }
    @Test
    public void testCPUUsageCreate100Todo() {
        double result = CPUUsageTests.createNTodo(100, "Mock Title", true, "Mock Description" );
        cpuUsageResultsCreate.add(new CSVWriterObject(1, "Create 1 Todo, Empty Server", 0, result));
    }
    @Test
    public void testCPUUsageCreate1Todo1000Existing() {
        createNTodos(1000);
        double result = CPUUsageTests.createTodo("Mock Title", true, "Mock Description" );
        cpuUsageResultsCreate.add(new CSVWriterObject(3, "Create 1 Todo, 1000 Existing", 0, result));
    }
    @Test
    public void testCPUUsageCreate1000Todo1000Existing() {
        createNTodos(1000);
        double result = CPUUsageTests.createNTodo(1000, "Mock Title", true, "Mock Description" );
        cpuUsageResultsCreate.add(new CSVWriterObject(4, "Create 1000 Todo, 1000 Existing", 0, result));
    }

    @Test
    public void testCPUUsageModify1Todo() {
        double result = CPUUsageTests.modifyTodoDescription(1, "Mock Description");
        cpuUsageResultsModify.add(new CSVWriterObject(1, "Modify 1 Todo, Empty Server", 0, result));
    }
    @Test
    public void testCPUUsageModify100Todo() {
        double result = CPUUsageTests.modifyNTodoDescription(100, 1, "Mock Description");
        cpuUsageResultsModify.add(new CSVWriterObject(2, "Modify 100 Todo, Empty Server", 0, result));
    }
    @Test
    public void testCPUUsageModify1Todo1000Existing() {
        createNTodos(1000);
        double result = CPUUsageTests.modifyTodoDescription(1, "Mock Description");
        cpuUsageResultsModify.add(new CSVWriterObject(3, "Modify 1 Todo, 1000 Existing", 0, result));
    }
    @Test
    public void testCPUUsageModify100Todo1000Existing() {
        createNTodos(1000);
        double result = CPUUsageTests.modifyNTodoDescription(100, 1, "Mock Description");
        cpuUsageResultsModify.add(new CSVWriterObject(4, "Modify 1000 Todo, 1000 Existing", 0, result));
    }

    @Test
    public void testCPUUsageDelete1Todo() {
        createNTodos(100);

        double result = CPUUsageTests.deleteTodo(1);
        cpuUsageResultsDelete.add(new CSVWriterObject(1, "Delete 1 Todo, 100 Objects Server", 0, result));
    }
    @Test
    public void testCPUUsageDelete50Todo() {
        createNTodos(100);

        double result = CPUUsageTests.deleteNTodo(50, 1);
        cpuUsageResultsDelete.add(new CSVWriterObject(2, "Delete 50 Todo, 100 Objects Server", 0, result));
    }
    @Test
    public void testCPUUsageDelete1Todo1000Existing() {
        createNTodos(1000);
        double result = CPUUsageTests.deleteTodo(1);
        cpuUsageResultsDelete.add(new CSVWriterObject(3, "Delete 1 Todo, 1000 Existing", 0, result));
    }
    @Test
    public void testCPUUsageDelete250Todo1000Existing() {
        createNTodos(1000);
        double result = CPUUsageTests.deleteNTodo(250, 1);
        cpuUsageResultsDelete.add(new CSVWriterObject(4, "Delete 250 Todo, 1000 Existing", 0, result));
    }

    @Test
    public void testMemoryUsageCreate1Todo() {
        long result = MemoryTests.createTodo("Mock Title", true, "Mock Description" );
        memoryResultsCreate.add(new CSVWriterObject(1, "Create 1 Todo, Empty Server", result, 0));
    }
    @Test
    public void testMemoryUsageCreate100Todo() {
        long result = MemoryTests.createNTodo(100, "Mock Title", true, "Mock Description" );
        memoryResultsCreate.add(new CSVWriterObject(2, "Create 100 Todo, Empty Server", result, 0));
    }
    @Test
    public void testMemoryUsageCreate1Todo1000Existing() {
        createNTodos(1000);
        long result = MemoryTests.createTodo("Mock Title", true, "Mock Description" );
        memoryResultsCreate.add(new CSVWriterObject(3, "Create 1 Todo, 1000 Existing", result, 0));
    }
    @Test
    public void testMemoryUsageCreate1000Todo1000Existing() {
        createNTodos(1000);
        long result = MemoryTests.createNTodo(100, "Mock Title", true, "Mock Description" );
        memoryResultsCreate.add(new CSVWriterObject(4, "Create 1000 Todo, 1000 Existing", result, 0));
    }

    @Test
    public void testMemoryUsageModify1Todo() {
        long result = MemoryTests.modifyTodoDescription(1, "Mock Description");
        memoryResultsModify.add(new CSVWriterObject(1, "Modify 1 Todo, Empty Server", result, 0));
    }
    @Test
    public void testMemoryUsageModify100Todo() {
        long result = MemoryTests.modifyNTodoDescription(100, 1, "Mock Description");
        memoryResultsModify.add(new CSVWriterObject(2, "Modify 100 Todo, Empty Server", result, 0));
    }
    @Test
    public void testMemoryUsageModify1Todo1000Existing() {
        createNTodos(1000);
        long result = MemoryTests.modifyTodoDescription(1, "Mock Description");
        memoryResultsModify.add(new CSVWriterObject(3, "Modify 1 Todo, 1000 Existing", result, 0));
    }
    @Test
    public void testMemoryUsageModify100Todo1000Existing() {
        createNTodos(1000);
        long result = MemoryTests.modifyNTodoDescription(100, 1, "Mock Description");
        memoryResultsModify.add(new CSVWriterObject(4, "Modify 1000 Todo, 1000 Existing", result, 0));
    }

    @Test
    public void testMemoryUsageDelete1Todo() {
        createNTodos(100);

        long result = MemoryTests.deleteTodo(1);
        memoryResultsCreate.add(new CSVWriterObject(1, "Delete 1 Todo, 100 Objects Server", result, 0));
    }
    @Test
    public void testMemoryUsageDelete50Todo() {
        createNTodos(100);

        long result = MemoryTests.deleteNTodo(50, 1);
        memoryResultsCreate.add(new CSVWriterObject(2, "Delete 50 Todo, 100 Objects Server", result, 0));
    }
    @Test
    public void testMemoryUsageDelete1Todo1000Existing() {
        createNTodos(1000);
        long result = MemoryTests.deleteTodo(1);
        memoryResultsCreate.add(new CSVWriterObject(3, "Delete 1 Todo, 1000 Existing", result, 0));
    }
    @Test
    public void testMemoryUsageDelete250Todo1000Existing() {
        createNTodos(1000);
        long result = MemoryTests.deleteNTodo(250, 1);
        memoryResultsCreate.add(new CSVWriterObject(4, "Delete 250 Todo, 1000 Existing", result, 0));
    }


    public void createNTodos(int n) {
        RequestSpecification request = given();
        request.header("Content-Type", "application/json");
        request.header("Accept", "application/json");

        for (int i = 0; i < n; i++) {
            JSONObject paramsMap = new JSONObject();
            paramsMap.put("title", "Mock Title "+i);
            paramsMap.put("description", "Mock Description "+i);
            paramsMap.put("doneStatus", true);

            request.body(paramsMap.toJSONString());

            Response response = request.post("/todos");

            int status_code = response.statusCode();
            if(status_code != 201) {
                System.out.println("Error Creating Todos");
                throw new RuntimeException();
            }
        }

        System.out.println("Creation of "+n+" Todos Completed");
    }

    public void createNCategories(int n) {
        RequestSpecification request = given();
        request.header("Content-Type", "application/json");
        request.header("Accept", "application/json");

        for(int i = 0; i < n; i++) {
            JSONObject categoriesMap = new JSONObject();
            categoriesMap.put("title", "Mock Title "+i);
            categoriesMap.put("description", "Mock Description "+i);


            request.body(categoriesMap.toJSONString());

            Response response = request.post("/categories");
            int status_code = response.statusCode();
            if(status_code != 201) {
                System.out.println("Error Creating Categories");
                throw new RuntimeException();
            }
        }

        System.out.println("Creation of "+n+" Categories Completed");
    }

    public void createRunTimeCSV(List<CSVWriterObject> results, String fileName) {
        FileWriter writer = null;

        try {
            writer = new FileWriter(fileName);
            writer.append("id,result,description\n");

            for(CSVWriterObject object: results) {
                int id = object.getId();
                String description = object.getDescription();

                long result = object.getResult();

                writer.append(String.valueOf(id)).append(",").append(description).append(",").append(String.valueOf(result)).append("\n");
            }

            System.out.println("Transaction Time Results Created");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createMemoryUsageCSV(List<CSVWriterObject> results, String fileName) {
        FileWriter writer = null;

        try {
            writer = new FileWriter(fileName);
            writer.append("id,result,description\n");

            for(CSVWriterObject object: results) {
                int id = object.getId();
                String description = object.getDescription();

                long result = object.getResult();

                writer.append(String.valueOf(id)).append(",").append(description).append(",").append(String.valueOf(result)).append("\n");
            }

            System.out.println("Memory Usage Results Created");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createCPUUsageCSV(List<CSVWriterObject> results, String fileName) {
        FileWriter writer = null;

        try {
            writer = new FileWriter(fileName);
            writer.append("id,result,description\n");

            for(CSVWriterObject object: results) {
                int id = object.getId();
                String description = object.getDescription();

                double result = object.getResult2();

                writer.append(String.valueOf(id)).append(",").append(description).append(",").append(String.valueOf(result)).append("\n");
            }

            System.out.println("CPU Usage Results Created");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
