/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package dk.jrpe.monitor.source.httpaccess.simulate;

import dk.jrpe.monitor.webservice.endpoint.generated.HTTPAccessData;
import dk.jrpe.monitor.webservice.endpoint.generated.HTTPAccessDataPort;

public final class HTTPAccessDataServiceTester {
    
    // The CustomerService proxy will be injected either by spring or by a direct call to the setter 
    HTTPAccessDataPort service;
    
    public HTTPAccessDataPort getHTTPAccessDataService() {
        return service;
    }

    public void setHTTPAccessDataService(HTTPAccessDataPort service) {
        this.service = service;
    }

    public void testService() {
    	HTTPAccessData data = new HTTPAccessData();
    	data.setAction("POST");
    	data.setCommand("TEST");
    	
        service.sendHTTPAccessData(data);
        System.out.println("All calls were successful");
    }

}
