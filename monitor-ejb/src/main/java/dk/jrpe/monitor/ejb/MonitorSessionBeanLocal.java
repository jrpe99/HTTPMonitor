/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.jrpe.monitor.ejb;

import javax.ejb.Local;

/**
 *
 * @author Z6PRN
 */
@Local
public interface MontorSessionBeanLocal {

    String test();
    
}
