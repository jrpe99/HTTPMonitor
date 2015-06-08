package dk.jrpe.monitor.source.httpaccess.client;

import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import dk.jrpe.monitor.service.command.CommandHandler;

public abstract class ServiceClient {
    public void sendToServer(HTTPAccessTO to) throws Exception {
        if (to.getHttpStatus().equals("200")) {
        	sendToServer(to, CommandHandler.CommandEnum.SEND_HTTP_SUCCESS_DATA.toString());
        	sendToServer(to, CommandHandler.CommandEnum.SEND_HTTP_SUCCESS_PER_MINUTE_DATA.toString());
        } else {
        	sendToServer(to, CommandHandler.CommandEnum.SEND_HTTP_FAILED_DATA.toString());
        	sendToServer(to, CommandHandler.CommandEnum.SEND_HTTP_FAILED_PER_MINUTE_DATA.toString());
        }
    }

    /**
     * Send HTTP access data to the server.
     * @param to
     * @param command must be one of {@link dk.jrpe.monitor.service.command.CommandHandler.CommandEnum}
     * @throws Exception
     */
	public abstract void sendToServer(HTTPAccessTO to, String command) throws Exception;
}
