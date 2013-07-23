/* com.cutty.bravo.components.concurrent.pool.ValidationStarter.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Nov 11, 2010 3:00:09 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 cutty Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.concurrent.pool;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 *
 * <p>
 * <a href="ValidationStarter.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class ValidationStarter implements Runnable {    
    private List<String>      entries;    
    private ValidationService validationService;    
    private CountDownLatch    signal;    
    
   
    public ValidationStarter(List<String> entries, ValidationService validationService,    
            CountDownLatch signal) {    
        this.entries = entries;    
        this.validationService = validationService;    
        this.signal = signal;    
    }    
   
    public void run() {    
        validationService.validate(entries);    
        signal.countDown();    
    }    


}
