package uk.co.compendiumdev.performance.projects;

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

public class DeleteProjectPerformanceTest {

    private static final String ALL_PROJECTS_PATH = "/projects";
    private static final String SPECIFIC_PROJECT_PATH = "/projects/{id}";
    private static final String CLEAR_PATH = "/admin/data/thingifier";
    private static final String PROJECTS = "projects";

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String COMPLETED = "completed";
    private static final String ACTIVE = "active";

    private static final String TEST_TITLE = "ECSE429";
    private static final String TEST_DESCRIPTION = "Deliverable C";

    private static final String TRUE = "true";
    private static final String FALSE = "false";

    List<Integer> projectIds = new ArrayList<Integer>();
    private static long globalStartTime;


    @BeforeAll
    public static void startTimer(){
        globalStartTime = System.currentTimeMillis();
    }

    @BeforeEach
    public void clearDataFromEnvThenPopulate() throws InterruptedException {
        RestAssured.baseURI = Environment.getBaseUri();
        if(RestAssured.baseURI == null) fail("To Do Manager isn't running!");

        post(CLEAR_PATH);

        final JsonPath clearedData = when().get(ALL_PROJECTS_PATH)
                .then().statusCode(200).extract().body().jsonPath();

        final int projects = clearedData.getList(PROJECTS).size();

        Assumptions.assumeTrue(projects == 0);

        // populate
        for(int i = 1; i<= SAMPLES; i++){
            projectIds.add(createProject());
            Thread.sleep(SLEEP_TIME);
        }
    }

    @Test
    public void deleteProjects() throws InterruptedException {
        System.out.println("Sample Number, Global time (ms), transaction time (ms):");
        int i = 0;
        for(Integer id: projectIds){
            deleteProject(i, id);
            i++;
            Thread.sleep(SLEEP_TIME);
        }
    }

    private static int createProject(){
        final HashMap<String, Object> givenBody = new HashMap<>();
        givenBody.put(TITLE, TEST_TITLE);
        givenBody.put(DESCRIPTION, TEST_DESCRIPTION);
        givenBody.put(ACTIVE, true);
        givenBody.put(COMPLETED, false);

        Response r = given().
                body(givenBody).
                when().
                post(ALL_PROJECTS_PATH)
                .thenReturn();

        String id  = r.then().contentType(ContentType.JSON)
                .statusCode(HttpStatus.SC_CREATED)
                .body(
                        TITLE, equalTo(TEST_TITLE),
                        DESCRIPTION, equalTo(TEST_DESCRIPTION),
                        ACTIVE, equalTo(TRUE),
                        COMPLETED, equalTo(FALSE)
                )
                .extract()
                .path(ID);

        return Integer.parseInt(id);
    }

    private static void deleteProject(int experimentNumber, int projectId){
        // sample time
        long globalTime = System.currentTimeMillis() - globalStartTime;

        long postStartTime = System.currentTimeMillis();

        Response r  = given().
                pathParam(ID, projectId).
                when().
                delete(SPECIFIC_PROJECT_PATH)
                .thenReturn();

        long postTime = System.currentTimeMillis() - postStartTime;

        r.then()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.SC_OK);

        if(experimentNumber != -1) {
            System.out.printf("%d,%d,%d\n",
                    experimentNumber, globalTime, postTime);
        }
    }
}