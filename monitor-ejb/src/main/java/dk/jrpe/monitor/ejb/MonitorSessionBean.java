package dk.jrpe.monitor.ejb;

import dk.jrpe.monitor.ejb.remote.MontorSessionBeanRemote;
import javax.ejb.Stateless;

/**
 *
 * @author Jörgen Persson
 */
@Stateless
public class MonitorSessionBean implements MontorSessionBeanRemote, MonitorSessionBeanLocal {

    @Override
    public String test() {
        return "MontorSessionBean response";
    }
}
