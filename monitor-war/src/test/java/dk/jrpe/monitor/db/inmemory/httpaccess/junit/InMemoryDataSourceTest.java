package dk.jrpe.monitor.db.inmemory.httpaccess.junit;

import static org.junit.Assert.assertEquals;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dk.jrpe.monitor.db.datasource.DataSourceFactory;
import dk.jrpe.monitor.db.datasource.HttpAccessDataSource;
import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import dk.jrpe.monitor.source.httpaccess.to.HTTPAccessTOFactory;

public class InMemoryDataSourceTest {
	private HttpAccessDataSource source;

	@Before
	public void setUp() throws Exception {
		DataSourceFactory.set(DataSourceFactory.Type.IN_MEMORY);
		this.source = DataSourceFactory.get();
	}

	@Test
	public void httpSuccess() {
		this.source.clear();
		this.source.updateHttpSuccess(HTTPAccessTOFactory.createSimulated());
		this.source.updateHttpSuccess(HTTPAccessTOFactory.createSimulated());
		
		List<HTTPAccessTO> httpSuccessList = this.source.getHttpSuccess();
		assertEquals(httpSuccessList.size(), 2);
		
		this.source.updateHttpSuccess(new HTTPAccessTO.Builder().setIPAdress("100").build());
		this.source.updateHttpSuccess(new HTTPAccessTO.Builder().setIPAdress("100").build());

		httpSuccessList = this.source.getHttpSuccess();
		assertEquals(3, httpSuccessList.size());

		httpSuccessList.stream().filter(to -> to.getIpAddress().equals("100")).forEach(to -> {
			assertEquals(new Long("2"), to.getRequests());
		});
	}

	@Test
	public void httpFailed() {
		this.source.clear();
		this.source.updateHttpFailed(HTTPAccessTOFactory.createSimulated());
		this.source.updateHttpFailed(HTTPAccessTOFactory.createSimulated());
		
		List<HTTPAccessTO> httpFailedList = this.source.getHttpFailed();
		assertEquals(httpFailedList.size(), 2);
		
		this.source.updateHttpFailed(new HTTPAccessTO.Builder().setIPAdress("100").build());
		this.source.updateHttpFailed(new HTTPAccessTO.Builder().setIPAdress("100").build());

		httpFailedList = this.source.getHttpFailed();
		assertEquals(3, httpFailedList.size());

		httpFailedList.stream().filter(to -> to.getIpAddress().equals("100")).forEach(to -> {
			assertEquals(new Long("2"), to.getRequests());
		});
	}

	@Test
	public void httpSuccessPerMinute() {
		this.source.clear();
        ZonedDateTime now = ZonedDateTime.now().withSecond(0).withNano(0);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String dateToMinute = formatter.format(now);
        
		this.source.updateHttpSuccessPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());
		this.source.updateHttpSuccessPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());
		this.source.updateHttpSuccessPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());
		this.source.updateHttpSuccessPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());

        String from = formatter.format(now);

        List<HTTPAccessTO> httpSuccessList = this.source.getHttpSuccessPerMinute(null, from, null);
		assertEquals(1, httpSuccessList.size());
		for (HTTPAccessTO to : httpSuccessList) {
			assertEquals(new Long("4"), to.getRequests());
		}
		
		dateToMinute = formatter.format(now.plusMinutes(1));
		
		this.source.updateHttpSuccessPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());
		this.source.updateHttpSuccessPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());
		this.source.updateHttpSuccessPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());
		this.source.updateHttpSuccessPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());

        from = formatter.format(now.plusMinutes(1));
        httpSuccessList = this.source.getHttpSuccessPerMinute(null, from, null);
		assertEquals(1, httpSuccessList.size());
		for (HTTPAccessTO to : httpSuccessList) {
			assertEquals(new Long("4"), to.getRequests());
		}
		
        from = formatter.format(now);

        httpSuccessList = this.source.getHttpSuccessPerMinute(null, from, null);
		assertEquals(2, httpSuccessList.size());
		for (HTTPAccessTO to : httpSuccessList) {
			assertEquals(new Long("4"), to.getRequests());
		}
	}

	@Test
	public void httpFailedPerMinute() {
		this.source.clear();
        ZonedDateTime now = ZonedDateTime.now().withSecond(0).withNano(0);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String dateToMinute = formatter.format(now);
        
		this.source.updateHttpFailedPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());
		this.source.updateHttpFailedPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());
		this.source.updateHttpFailedPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());
		this.source.updateHttpFailedPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());

        String from = formatter.format(now);

        List<HTTPAccessTO> httpFailedList = this.source.getHttpFailedPerMinute(null, from, null);
		assertEquals(1, httpFailedList.size());
		for (HTTPAccessTO to : httpFailedList) {
			assertEquals(new Long("4"), to.getRequests());
		}
		
		dateToMinute = formatter.format(now.plusMinutes(1));
		
		this.source.updateHttpFailedPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());
		this.source.updateHttpFailedPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());
		this.source.updateHttpFailedPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());
		this.source.updateHttpFailedPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());

        from = formatter.format(now.plusMinutes(1));
        httpFailedList = this.source.getHttpFailedPerMinute(null, from, null);
		assertEquals(1, httpFailedList.size());
		for (HTTPAccessTO to : httpFailedList) {
			assertEquals(new Long("4"), to.getRequests());
		}
		
        from = formatter.format(now);

        httpFailedList = this.source.getHttpFailedPerMinute(null, from, null);
		assertEquals(2, httpFailedList.size());
		for (HTTPAccessTO to : httpFailedList) {
			assertEquals(new Long("4"), to.getRequests());
		}
	}
}
