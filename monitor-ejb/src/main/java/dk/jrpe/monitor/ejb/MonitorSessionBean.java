package dk.jrpe.monitor.ejb;

import javax.ejb.Stateless;

import dk.jrpe.monitor.ejb.remote.MonitorSessionBeanRemote;

/**
 *
 * @author Jörgen Persson
 */
@Stateless
public class MonitorSessionBean implements MonitorSessionBeanLocal, MonitorSessionBeanRemote {

	@Override
	public String test() {
		return "Test MonitorSessionBean";
	}
}
