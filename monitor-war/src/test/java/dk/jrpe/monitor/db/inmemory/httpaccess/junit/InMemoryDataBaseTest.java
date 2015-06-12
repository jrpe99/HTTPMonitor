package dk.jrpe.monitor.db.inmemory.httpaccess.junit;

import static org.junit.Assert.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import dk.jrpe.monitor.db.inmemory.httpaccess.InMemoryDataBase;
import dk.jrpe.monitor.source.httpaccess.to.HTTPAccessTOFactory;

public class InMemoryDataBaseTest {
	private InMemoryDataBase instance;

	@Before
	public void setUp() throws Exception {
		this.instance = InMemoryDataBase.getInstance();
	}

	@Test
	public void httpSuccess() {
		this.instance.clear();
		this.instance.updateHttpSuccess(HTTPAccessTOFactory.createSimulated());
		this.instance.updateHttpSuccess(HTTPAccessTOFactory.createSimulated());
		
		List<HTTPAccessTO> httpSuccessList = this.instance.getHttpSuccess();
		assertEquals(httpSuccessList.size(), 2);
		
		this.instance.updateHttpSuccess(new HTTPAccessTO.Builder().setIPAdress("100").build());
		this.instance.updateHttpSuccess(new HTTPAccessTO.Builder().setIPAdress("100").build());

		httpSuccessList = this.instance.getHttpSuccess();
		assertEquals(3, httpSuccessList.size());

		httpSuccessList.stream().filter(to -> to.getIpAddress().equals("100")).forEach(to -> {
			assertEquals(new Long("2"), to.getRequests());
		});
	}

	@Test
	public void httpFailed() {
		this.instance.clear();
		this.instance.updateHttpFailed(HTTPAccessTOFactory.createSimulated());
		this.instance.updateHttpFailed(HTTPAccessTOFactory.createSimulated());
		
		List<HTTPAccessTO> httpFailedList = this.instance.getHttpFailed();
		assertEquals(httpFailedList.size(), 2);
		
		this.instance.updateHttpFailed(new HTTPAccessTO.Builder().setIPAdress("100").build());
		this.instance.updateHttpFailed(new HTTPAccessTO.Builder().setIPAdress("100").build());

		httpFailedList = this.instance.getHttpFailed();
		assertEquals(3, httpFailedList.size());

		httpFailedList.stream().filter(to -> to.getIpAddress().equals("100")).forEach(to -> {
			assertEquals(new Long("2"), to.getRequests());
		});
	}

	@Test
	public void httpSuccessPerMinute() {
		this.instance.clear();
        ZonedDateTime now = ZonedDateTime.now().withSecond(0).withNano(0);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String dateToMinute = formatter.format(now);
        
		this.instance.updateHttpSuccessPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());
		this.instance.updateHttpSuccessPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());
		this.instance.updateHttpSuccessPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());
		this.instance.updateHttpSuccessPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());

        String from = formatter.format(now);

        List<HTTPAccessTO> httpSuccessList = this.instance.getHttpSuccessPerMinute(null, from, null);
		assertEquals(1, httpSuccessList.size());
		for (HTTPAccessTO to : httpSuccessList) {
			assertEquals(new Long("4"), to.getRequests());
		}
		
		dateToMinute = formatter.format(now.plusMinutes(1));
		
		this.instance.updateHttpSuccessPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());
		this.instance.updateHttpSuccessPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());
		this.instance.updateHttpSuccessPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());
		this.instance.updateHttpSuccessPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());

        from = formatter.format(now.plusMinutes(1));
        httpSuccessList = this.instance.getHttpSuccessPerMinute(null, from, null);
		assertEquals(1, httpSuccessList.size());
		for (HTTPAccessTO to : httpSuccessList) {
			assertEquals(new Long("4"), to.getRequests());
		}
		
        from = formatter.format(now);

        httpSuccessList = this.instance.getHttpSuccessPerMinute(null, from, null);
		assertEquals(2, httpSuccessList.size());
		for (HTTPAccessTO to : httpSuccessList) {
			assertEquals(new Long("4"), to.getRequests());
		}
	}

	@Test
	public void httpFailedPerMinute() {
		this.instance.clear();
        ZonedDateTime now = ZonedDateTime.now().withSecond(0).withNano(0);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String dateToMinute = formatter.format(now);
        
		this.instance.updateHttpFailedPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());
		this.instance.updateHttpFailedPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());
		this.instance.updateHttpFailedPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());
		this.instance.updateHttpFailedPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());

        String from = formatter.format(now);

        List<HTTPAccessTO> httpFailedList = this.instance.getHttpFailedPerMinute(null, from, null);
		assertEquals(1, httpFailedList.size());
		for (HTTPAccessTO to : httpFailedList) {
			assertEquals(new Long("4"), to.getRequests());
		}
		
		dateToMinute = formatter.format(now.plusMinutes(1));
		
		this.instance.updateHttpFailedPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());
		this.instance.updateHttpFailedPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());
		this.instance.updateHttpFailedPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());
		this.instance.updateHttpFailedPerMinute(new HTTPAccessTO.Builder().setDateToMinute(dateToMinute).build());

        from = formatter.format(now.plusMinutes(1));
        httpFailedList = this.instance.getHttpFailedPerMinute(null, from, null);
		assertEquals(1, httpFailedList.size());
		for (HTTPAccessTO to : httpFailedList) {
			assertEquals(new Long("4"), to.getRequests());
		}
		
        from = formatter.format(now);

        httpFailedList = this.instance.getHttpFailedPerMinute(null, from, null);
		assertEquals(2, httpFailedList.size());
		for (HTTPAccessTO to : httpFailedList) {
			assertEquals(new Long("4"), to.getRequests());
		}
	}
}
