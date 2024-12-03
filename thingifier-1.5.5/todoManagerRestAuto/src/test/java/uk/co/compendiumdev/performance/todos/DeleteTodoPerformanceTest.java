package uk.co.compendiumdev.performance.todos;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.compendiumdev.Environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.fail;
import static uk.co.compendiumdev.performance.TestVars.SAMPLES;
import static uk.co.compendiumdev.performance.TestVars.SLEEP_TIME;

public class DeleteTodoPerformanceTest {

    private static final String ALL_TODOS_PATH = "/todos";
    private static final String SPECIFIC_TODO_PATH = "/todos/{id}";
    private static final String CLEAR_PATH = "/admin/data/thingifier";
    private static final String TODOS = "todos";

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String DONE = "doneStatus";

    private static final String TITLE_EXAMPLE = "water plants";
    private static final String DESCRIPTION_EXAMPLE = "water bedroom plants";

    private static final String TRUE = "true";

    List<Integer> todoIds = new ArrayList<Integer>();
    private static long globalStartTime;


    @BeforeAll
    public static void startTimer(){
        globalStartTime = System.currentTimeMillis();
    }

    @BeforeEach
    public void clearDataFromEnv(){
        RestAssured.baseURI = Environment.getBaseUri();
        if(RestAssured.baseURI == null) fail("To Do Manager isn't running!");

        post(CLEAR_PATH);

        final JsonPath clearedData = when().get(ALL_TODOS_PATH)
                .then().statusCode(200).extract().body().jsonPath();

        final int todos = clearedData.getList(TODOS).size();

        Assumptions.assumeTrue(todos == 0);

        // populate
        for(int i = 1; i<= SAMPLES; i++){
            todoIds.add(createTodo());
        }
    }


    @Test
    public void deleteTodos() throws InterruptedException {
        System.out.println("Sample Number, Global time (ms), transaction time (ms):");
        int i = 0;
        for(Integer id: todoIds){
            deleteTodos(i, id);
            i++;
            Thread.sleep(SLEEP_TIME);
        }
    }

    private static int createTodo(){
        final HashMap<String, Object> givenBody = new HashMap<>();
        givenBody.put(TITLE, TITLE_EXAMPLE);
        givenBody.put(DESCRIPTION, DESCRIPTION_EXAMPLE);
        givenBody.put(DONE, true);

        Response r = given().
                body(givenBody).
                when().
                post(ALL_TODOS_PATH).thenReturn();

        String id = r.then()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.SC_CREATED)
                .body(
                        TITLE, equalTo(TITLE_EXAMPLE),
                        DESCRIPTION, equalTo(DESCRIPTION_EXAMPLE),
                        DONE, equalTo(TRUE)
                )
                .extract()
                .path(ID);

        return Integer.parseInt(id);
    }

    private static void deleteTodos(int experimentNumber, int todoId) {
        long globalTime = System.currentTimeMillis() - globalStartTime;

        long createStartTime = System.currentTimeMillis();

        Response r = given().
                pathParam(ID, todoId).
                when().
                delete(SPECIFIC_TODO_PATH)
                .thenReturn();
        r.then().contentType(ContentType.JSON)
                .statusCode(HttpStatus.SC_OK);

        long createTime = System.currentTimeMillis() - createStartTime;

        System.out.printf("%d,%d,%d\n", experimentNumber, globalTime, createTime);
    }
}