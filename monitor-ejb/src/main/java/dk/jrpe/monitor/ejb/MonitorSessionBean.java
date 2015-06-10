package dk.jrpe.monitor.ejb;

import javax.ejb.Stateless;

import dk.jrpe.monitor.ejb.remote.MontorSessionBeanRemote;

/**
 *
 * @author Jörgen Persson
 */
@Stateless
public class MonitorSessionBean implements MonitorSessionBeanLocal, MontorSessionBeanRemote {

	@Override
	public String test() {
		return "Test MonitorSessionBean";
	}
}
