/* com.cutty.bravo.components.concurrent.pool.Node.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Nov 11, 2010 2:53:09 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 cutty Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.concurrent.pool;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * <p>
 * <a href="Node.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class Node {
	
    private String name;    
    private String wsdl;    
    private String result = "PASS";    
    private String[] dependencies = new String[] {};    
    private Lock lock = new ReentrantLock();    
    /**   
     * 默认构造方法   
     */   
    public Node() {    
    }    
        
    /**   
     * 构造节点对象，设置名称及WSDL   
     */   
    public Node(String name, String wsdl) {    
        this.name = name;    
        this.wsdl = wsdl;    
    }    
   
    /**   
     * 返回包含节点名称、WSDL以及验证结果的字符串   
     */   
    @Override   
    public String toString() {    
        String toString = "Node: " + name + " WSDL: " + wsdl + " Result: " + result;    
        return toString;    
    }    
        
    // Getter & Setter    
    public String getName() {    
        return name;    
    }    
   
    public void setName(String name) {    
        this.name = name;    
    }    
   
    public String getWsdl() {    
        return wsdl;    
    }    
   
    public void setWsdl(String wsdl) {    
        this.wsdl = wsdl;    
    }    
   
    public String getResult() {    
        return result;    
    }    
   
    public void setResult(String result) {    
        this.result = result;    
    }    
   
    public String[] getDependencies() {    
        return dependencies;    
    }    
   
    public void setDependencies(String[] dependencies) {    
        this.dependencies = dependencies;    
    }    
   
    public Lock getLock() {    
        return lock;    
    }    

}
