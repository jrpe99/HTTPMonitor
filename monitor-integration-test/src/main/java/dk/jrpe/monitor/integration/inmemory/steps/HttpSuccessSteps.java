package dk.jrpe.monitor.integration.inmemory.steps;

import dk.jrpe.monitor.db.datasource.DataSourceFactory;
import dk.jrpe.monitor.db.datasource.HttpAccessDataSource;
import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import dk.jrpe.monitor.service.chart.ChartEnum;
import dk.jrpe.monitor.source.httpaccess.to.HTTPAccessTOFactory;
import dk.jrpe.monitor.task.HttpRequestsMonitorTask;
import dk.jrpe.monitor.util.SortHelper;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.List;

import static org.junit.Assert.*;

public class HttpSuccessSteps {
    private HttpAccessDataSource source;

    @Given("the in-memory database is initialized")
    public void initialize() {
        DataSourceFactory.set(DataSourceFactory.Type.IN_MEMORY);
        this.source = DataSourceFactory.get();
    }

    @When("a client update with new HTTP success data $ip")
    public void updateDatabase(String ip) {
        this.source.clear();
        this.source.updateHttpSuccess(HTTPAccessTOFactory.createSimulated());
        this.source.updateHttpSuccess(HTTPAccessTOFactory.createSimulated());
        this.source.updateHttpSuccess(new HTTPAccessTO.Builder().setIPAdress(ip).build());
        this.source.updateHttpSuccess(new HTTPAccessTO.Builder().setIPAdress(ip).build());
    }

    @Then("the data is stored in the database $ip")
    public void dataIsStored(String ip) {
        List<HTTPAccessTO> httpSuccessList = this.source.getHttpSuccess();
        /*
        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
        assertEquals(3, httpSuccessList.size());

        httpSuccessList.stream().filter(to -> to.getIpAddress().equals(ip)).forEach(to -> {
            assertEquals(new Long("2"), to.getRequests());
        });
    }

    @Then ("new statistics can be fetched for ip $ip and JSON generated")
    public void getJSONStatistics(String ip) {
        HttpRequestsMonitorTask task = new HttpRequestsMonitorTask(this.source, null, 1);
        List<HTTPAccessTO> successRowList = SortHelper.onRequestCount(this.source.getHttpSuccess());
        ChartEnum.PIE_SUCCESS.toJSON(successRowList);
        String json = ChartEnum.PIE_SUCCESS.getJson();
        String expected = "{        \"value\": 2,        \"color\":\"#0000FA\",        \"highlight\": \"#FF5A5E\",        \"label\": \""+ip+"\"    }";
        System.out.println(json);
        System.out.println(expected);
        assertTrue(json.indexOf(expected) != -1);
    }
}
