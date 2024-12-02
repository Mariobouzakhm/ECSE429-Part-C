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

    private List<CSVWriterObject> memoryResultsCreate;
    private List<CSVWriterObject>memoryResultsDelete;
    private List<CSVWriterObject> memoryResultsModify;

    private List<CSVWriterObject> cpuUsageResultsCreate;
    private List<CSVWriterObject> cpuUsageResultsModify;
    private List<CSVWriterObject> cpuUsageResultsDelete;

    private List<CSVWriterObject> runTimeResultsTodoCreateCategories;
    private List<CSVWriterObject> runTimeResultsTodoDeleteCategories;
    private List<CSVWriterObject> runTimeResultsTodoModifyCategories;

    private List<CSVWriterObject> memoryResultsCreateCategories;
    private List<CSVWriterObject>memoryResultsDeleteCategories;
    private List<CSVWriterObject> memoryResultsModifyCategories;

    private List<CSVWriterObject> cpuUsageResultsCreateCategories;
    private List<CSVWriterObject> cpuUsageResultsModifyCategories;
    private List<CSVWriterObject> cpuUsageResultsDeleteCategories;

    private final int[] NUMBER_OF_OBJECTS = {1, 5, 10, 25, 50, 100, 250, 1000, 2500, 5000};
    private int test_number = 10;


    @BeforeAll
    public void setup() {
        runTimeResultsTodoCreate = new ArrayList<>();
        runTimeResultsTodoDelete = new ArrayList<>();
        runTimeResultsTodoModify = new ArrayList<>();

        memoryResultsCreate = new ArrayList<>();
        memoryResultsDelete = new ArrayList<>();
        memoryResultsModify = new ArrayList<>();

        cpuUsageResultsCreate = new ArrayList<>();
        cpuUsageResultsModify = new ArrayList<>();
        cpuUsageResultsDelete = new ArrayList<>();

        runTimeResultsTodoCreateCategories = new ArrayList<>();
        runTimeResultsTodoDeleteCategories = new ArrayList<>();
        runTimeResultsTodoModifyCategories = new ArrayList<>();

        memoryResultsCreateCategories = new ArrayList<>();
        memoryResultsDeleteCategories = new ArrayList<>();
        memoryResultsModifyCategories = new ArrayList<>();

        cpuUsageResultsCreateCategories = new ArrayList<>();
        cpuUsageResultsModifyCategories = new ArrayList<>();
        cpuUsageResultsDeleteCategories = new ArrayList<>();

        System.out.println("Lists Setup Done");
    }

    @BeforeEach
    public void beforeEach() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 4567;

        Runtime runtime = Runtime.getRuntime();

        while(true) {
            try {
                this.p = runtime.exec("java -jar runTodoManagerRestAPI-1.5.5.jar");
                Thread.sleep(1000);
                break;
            } catch (IOException | InterruptedException e) {
                p.destroy();
            }
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

                while(true) {
                    try {
                        this.p = runtime.exec("java -jar runTodoManagerRestAPI-1.5.5.jar");
                        Thread.sleep(1000);
                        break;
                    } catch (IOException | InterruptedException e) {
                        p.destroy();
                    }
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

        if(runTimeResultsTodoCreate.size() > 0)
            createRunTimeCSV(runTimeResultsTodoCreate, "csv/runTimeResultsTodoCreate.csv");
        if(runTimeResultsTodoModify.size() > 0)
            createRunTimeCSV(runTimeResultsTodoModify, "csv/runTimeResultsTodoModify.csv");
        if(runTimeResultsTodoDelete.size() > 0)
            createRunTimeCSV(runTimeResultsTodoDelete, "csv/runTimeResultsTodoDelete.csv");

        if(memoryResultsCreate.size() > 0)
            createMemoryUsageCSV(memoryResultsCreate, "csv/memoryResultsCreate.csv");
        if(memoryResultsModify.size() > 0)
            createMemoryUsageCSV(memoryResultsModify, "csv/memoryResultsModify.csv");
        if(memoryResultsDelete.size() > 0)
            createMemoryUsageCSV(memoryResultsDelete, "csv/memoryResultsDelete.csv");

        if(cpuUsageResultsCreate.size() > 0)
            createCPUUsageCSV(cpuUsageResultsCreate, "csv/cpuUsageResultsCreate.csv");
        if(cpuUsageResultsModify.size() > 0)
            createCPUUsageCSV(cpuUsageResultsModify, "csv/cpuUsageResultsModify.csv");
        if(cpuUsageResultsDelete.size() > 0)
            createCPUUsageCSV(cpuUsageResultsDelete, "csv/cpuUsageResultsDelete.csv");


        if(runTimeResultsTodoCreateCategories.size() > 0)
            createRunTimeCSV(runTimeResultsTodoCreateCategories, "csv/runTimeResultsTodoCreateCategories.csv");
        if(runTimeResultsTodoModifyCategories.size() > 0)
            createRunTimeCSV(runTimeResultsTodoModifyCategories, "csv/runTimeResultsTodoModifyCategories.csv");
        if(runTimeResultsTodoDeleteCategories.size() > 0)
            createRunTimeCSV(runTimeResultsTodoDeleteCategories, "csv/runTimeResultsTodoDeleteCategories.csv");

        if(memoryResultsCreateCategories.size() > 0)
            createMemoryUsageCSV(memoryResultsCreateCategories, "csv/memoryResultsCreateCategories.csv");
        if(memoryResultsModifyCategories.size() > 0)
            createMemoryUsageCSV(memoryResultsModifyCategories, "csv/memoryResultsModifyCategories.csv");
        if(memoryResultsDeleteCategories.size() > 0)
            createMemoryUsageCSV(memoryResultsDeleteCategories, "csv/memoryResultsDeleteCategories.csv");

        if(cpuUsageResultsCreateCategories.size() > 0)
            createCPUUsageCSV(cpuUsageResultsCreateCategories, "csv/cpuUsageResultsCreateCategories.csv");
        if(cpuUsageResultsModifyCategories.size() > 0)
            createCPUUsageCSV(cpuUsageResultsModifyCategories, "csv/cpuUsageResultsModifyCategories.csv");
        if(cpuUsageResultsDeleteCategories.size() > 0)
            createCPUUsageCSV(cpuUsageResultsDeleteCategories, "csv/cpuUsageResultsDeleteCategories.csv");
    }

    @Test
    public void createTodoRunTime() {
        createNTodos(NUMBER_OF_OBJECTS[test_number-1]);

        System.out.println("Finished Setup");

        long startTime = System.currentTimeMillis();

        for(int i = 0; i < test_number; i++) {
            int iterations = NUMBER_OF_OBJECTS[i];

            long createTodoTime = RuntimeTests.createNTodo(iterations, "Mock Title", true, "Mock Description");
            runTimeResultsTodoCreate.add(new CSVWriterObject(i, String.valueOf(iterations), createTodoTime, 0));

            System.out.println("Finished "+iterations);
        }

        long endTime = System.currentTimeMillis();
        runTimeResultsTodoCreate.add(new CSVWriterObject(test_number, String.valueOf("TOTAL"), endTime-startTime, 0));
    }

    @Test
    public void modifyTodoRuntTime() {
        createNTodos(NUMBER_OF_OBJECTS[test_number-1]);

        long startTime = System.currentTimeMillis();

        for(int i = 0; i < test_number; i++) {
            int iterations = NUMBER_OF_OBJECTS[i];

            long createTodoTime = RuntimeTests.modifyTodoDescriptionNTimes(iterations, 1, "Mock Desc");
            runTimeResultsTodoModify.add(new CSVWriterObject(i, String.valueOf(iterations), createTodoTime, 0));

            System.out.println("Finished "+iterations);
        }

        long endTime = System.currentTimeMillis();
        runTimeResultsTodoModify.add(new CSVWriterObject(test_number, String.valueOf("TOTAL"), endTime-startTime, 0));
    }

    @Test
    public void deleteTodoRunTime() {
        createNTodos(NUMBER_OF_OBJECTS[test_number-1]);

        long startTime = System.currentTimeMillis();

        for(int i = 0; i < test_number; i++) {
            int iterations = NUMBER_OF_OBJECTS[i];

            long createTodoTime = RuntimeTests.deleteNTodo(iterations, 1);
            runTimeResultsTodoDelete.add(new CSVWriterObject(i, String.valueOf(iterations), createTodoTime, 0));

            System.out.println("Finished "+iterations);
        }

        long endTime = System.currentTimeMillis();
        runTimeResultsTodoDelete.add(new CSVWriterObject(test_number, String.valueOf("TOTAL"), endTime-startTime, 0));
    }

    @Test
    public void createTodoMemory() {
        createNTodos(NUMBER_OF_OBJECTS[test_number-1]);

        System.out.println("Finished Setup");

        for(int i = 0; i < test_number; i++) {
            int iterations = NUMBER_OF_OBJECTS[i];

            long createTodoTime = MemoryTests.createNTodo(iterations, "Mock Title", true, "Mock Description");
            memoryResultsCreate.add(new CSVWriterObject(i, String.valueOf(iterations), createTodoTime, 0));

            System.out.println("Finished "+iterations);
        }
  }

    @Test
    public void modifyTodoMemory() {
        createNTodos(NUMBER_OF_OBJECTS[test_number-1]);

        for(int i = 0; i < test_number; i++) {
            int iterations = NUMBER_OF_OBJECTS[i];

            long createTodoTime = MemoryTests.modifyNTodoDescription(iterations, 1, "Mock Desc");
            memoryResultsModify.add(new CSVWriterObject(i, String.valueOf(iterations), createTodoTime, 0));

            System.out.println("Finished "+iterations);
        }
    }

    @Test
    public void deleteTodoMemory() {
        createNTodos(NUMBER_OF_OBJECTS[test_number-1]);

        for(int i = 0; i < test_number; i++) {
            int iterations = NUMBER_OF_OBJECTS[i];

            long createTodoTime = MemoryTests.deleteNTodo(iterations, 1);
            memoryResultsDelete.add(new CSVWriterObject(i, String.valueOf(iterations), createTodoTime, 0));

            System.out.println("Finished "+iterations);
        }
    }

    @Test
    public void createTodoUsage() throws InterruptedException {
        createNTodos(NUMBER_OF_OBJECTS[test_number-1]);

        System.out.println("Finished Setup");



        for(int i = 0; i < test_number; i++) {
            int iterations = NUMBER_OF_OBJECTS[i];

            double createTodoTime = CPUUsageTests.createNTodo(iterations, "Mock Title", true, "Mock Description");
            cpuUsageResultsCreate.add(new CSVWriterObject(i, String.valueOf(iterations),0, createTodoTime));

            System.out.println("Finished "+iterations);
        }
   }

    @Test
    public void modifyTodoUsage() {
        createNTodos(NUMBER_OF_OBJECTS[test_number-1]);

        for(int i = 0; i < test_number; i++) {
            int iterations = NUMBER_OF_OBJECTS[i];

            double createTodoTime = CPUUsageTests.modifyNTodoDescription(iterations, 1, "Mock Desc");
            cpuUsageResultsModify.add(new CSVWriterObject(i, String.valueOf(iterations), 0, createTodoTime));

            System.out.println("Finished "+iterations);
        }
    }

    @Test
    public void deleteTodoUsage() {
        createNTodos(NUMBER_OF_OBJECTS[test_number-1]);

        for(int i = 0; i < test_number; i++) {
            int iterations = NUMBER_OF_OBJECTS[i];

            double createTodoTime = CPUUsageTests.deleteNTodo(iterations, 1);
            cpuUsageResultsDelete.add(new CSVWriterObject(i, String.valueOf(iterations), 0, createTodoTime));

            System.out.println("Finished "+iterations);
        }
  }

    @Test
    public void createCategoryRunTime() {
        createNCategories(NUMBER_OF_OBJECTS[test_number-1]);

        System.out.println("Finished Setup");

        long startTime = System.currentTimeMillis();

        for(int i = 0; i < test_number; i++) {
            int iterations = NUMBER_OF_OBJECTS[i];

            long createTodoTime = RuntimeTests.createNCategory(iterations, "Mock Title", "Mock Description");
            runTimeResultsTodoCreateCategories.add(new CSVWriterObject(i, String.valueOf(iterations), createTodoTime, 0));

            System.out.println("Finished "+iterations);
        }

        long endTime = System.currentTimeMillis();
        runTimeResultsTodoCreateCategories.add(new CSVWriterObject(test_number, String.valueOf("TOTAL"), endTime-startTime, 0));
    }

    @Test
    public void modifyCategoryRunTime() {
        createNCategories(NUMBER_OF_OBJECTS[test_number-1]);

        long startTime = System.currentTimeMillis();

        for(int i = 0; i < test_number; i++) {
            int iterations = NUMBER_OF_OBJECTS[i];

            long createTodoTime = RuntimeTests.modifyNCategoryDescription(iterations, 1, "Mock Desc");
            runTimeResultsTodoModifyCategories.add(new CSVWriterObject(i, String.valueOf(iterations), createTodoTime, 0));

            System.out.println("Finished "+iterations);
        }

        long endTime = System.currentTimeMillis();
        runTimeResultsTodoModifyCategories.add(new CSVWriterObject(test_number, String.valueOf("TOTAL"), endTime-startTime, 0));
    }

    @Test
    public void deleteCategoryRunTime() {
        createNCategories(NUMBER_OF_OBJECTS[test_number-1]);

        long startTime = System.currentTimeMillis();

        for(int i = 0; i < test_number; i++) {
            int iterations = NUMBER_OF_OBJECTS[i];

            long createTodoTime = RuntimeTests.deleteNCategories(iterations, 1);
            runTimeResultsTodoDeleteCategories.add(new CSVWriterObject(i, String.valueOf(iterations), createTodoTime, 0));

            System.out.println("Finished "+iterations);
        }

        long endTime = System.currentTimeMillis();
        runTimeResultsTodoDeleteCategories.add(new CSVWriterObject(test_number, String.valueOf("TOTAL"), endTime-startTime, 0));
    }

    @Test
    public void createCategoryMemory() {
        createNCategories(NUMBER_OF_OBJECTS[test_number-1]);

        System.out.println("Finished Setup");

        for(int i = 0; i < test_number; i++) {
            int iterations = NUMBER_OF_OBJECTS[i];

            long createTodoTime = MemoryTests.createNCategories(iterations, "Mock Title",  "Mock Description");
            memoryResultsCreateCategories.add(new CSVWriterObject(i, String.valueOf(iterations), createTodoTime, 0));

            System.out.println("Finished "+iterations);
        }
    }

    @Test
    public void modifyCategoryMemory() {
        createNCategories(NUMBER_OF_OBJECTS[test_number-1]);

        for(int i = 0; i < test_number; i++) {
            int iterations = NUMBER_OF_OBJECTS[i];

            long createTodoTime = MemoryTests.modifyNCategoriesDescriptions(iterations, 1, "Mock Desc");
            memoryResultsModifyCategories.add(new CSVWriterObject(i, String.valueOf(iterations), createTodoTime, 0));

            System.out.println("Finished "+iterations);
        }
    }

    @Test
    public void deleteCategoryMemory() {
        createNCategories(NUMBER_OF_OBJECTS[test_number-1]);

        for(int i = 0; i < test_number; i++) {
            int iterations = NUMBER_OF_OBJECTS[i];

            long createTodoTime = MemoryTests.deleteNCategories(iterations, 1);
            memoryResultsDeleteCategories.add(new CSVWriterObject(i, String.valueOf(iterations), createTodoTime, 0));

            System.out.println("Finished "+iterations);
        }
    }

    @Test
    public void createCategoryUsage() {
        createNCategories(NUMBER_OF_OBJECTS[test_number-1]);

        System.out.println("Finished Setup");

        for(int i = 0; i < test_number; i++) {
            int iterations = NUMBER_OF_OBJECTS[i];

            double createTodoTime = CPUUsageTests.createNCategories(iterations, "Mock Title", "Mock Description");
            cpuUsageResultsCreateCategories.add(new CSVWriterObject(i, String.valueOf(iterations),0, createTodoTime));

            System.out.println("Finished "+iterations);
        }
    }

    @Test
    public void modifyCategoryUsage() {
        createNCategories(NUMBER_OF_OBJECTS[test_number-1]);

        for(int i = 0; i < test_number; i++) {
            int iterations = NUMBER_OF_OBJECTS[i];

            double createTodoTime = CPUUsageTests.modifyNCategoriesDescription(iterations, 1, "Mock Desc");
            cpuUsageResultsModifyCategories.add(new CSVWriterObject(i, String.valueOf(iterations), 0, createTodoTime));

            System.out.println("Finished "+iterations);
        }
    }

    @Test
    public void deleteNCategories() {
        createNCategories(NUMBER_OF_OBJECTS[test_number-1]);

        for(int i = 0; i < test_number; i++) {
            int iterations = NUMBER_OF_OBJECTS[i];

            double createTodoTime = CPUUsageTests.deleteNCategories(iterations, 1);
            cpuUsageResultsDeleteCategories.add(new CSVWriterObject(i, String.valueOf(iterations), 0, createTodoTime));

            System.out.println("Finished "+iterations);
        }
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
