/* com.cutty.bravo.components.concurrent.pool.ValidationService.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Nov 11, 2010 2:57:41 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 cutty Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.concurrent.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * <p>
 * <a href="ValidationService.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class ValidationService {
	
	 /**   
     * 全局节点表   
     */   
	
    public static final Map<String, Node> NODE_MAP = new ConcurrentHashMap<String, Node>();    
    private ThreadPoolService threadPoolService;    
    public ValidationService(ThreadPoolService threadPoolService) {    
        this.threadPoolService = threadPoolService;    
    }    
   
    /**   
     * 给出一个入口节点的WSDL，通过广度遍历的方式验证与其相关的各个节点   
     *    
     * @param wsdl 入口节点WSDL   
     */   
    public void validate(List<String> wsdl) {    
        List<String> visitedNodes = new ArrayList<String>();    
        List<String> nextRoundNodes = new ArrayList<String>();    
        nextRoundNodes.addAll(wsdl);    
        while (nextRoundNodes.size() > 0) {    
            List<ValidationTask> tasks = getTasks(nextRoundNodes);    
            List<Node> nodes = threadPoolService.invokeAll(tasks);    
   
            visitedNodes.addAll(nextRoundNodes);    
            nextRoundNodes.clear();    
            getNextRoundNodes(nodes, visitedNodes, nextRoundNodes);    
        }    
    }    
   
    private List<String> getNextRoundNodes(List<Node> nodes,    
            List<String> visitedNodes, List<String> nextRoundNodes) {    
        for (Node node : nodes) {    
            for (String wsdl : node.getDependencies()) {    
                if (!visitedNodes.contains(wsdl)) {    
                    nextRoundNodes.add(wsdl);    
                }    
            }    
        }    
        return nextRoundNodes;    
    }    
   
    private List<ValidationTask> getTasks(List<String> nodes) {    
        List<ValidationTask> tasks = new ArrayList<ValidationTask>(nodes.size());    
        for (String wsdl : nodes) {    
            tasks.add(new ValidationTask(wsdl));    
        }    
        return tasks;    
    }    

}
