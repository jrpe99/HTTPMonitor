package dk.jrpe.monitor.ejb.remote;

import javax.ejb.Remote;

/**
 *
 * @author Jörgen Persson
 */
@Remote
public interface MontorSessionBeanRemote {

    String test();
    
}
