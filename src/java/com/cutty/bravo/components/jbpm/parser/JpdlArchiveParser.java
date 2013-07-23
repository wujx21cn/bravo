/* com.cutty.bravo.components.jbpm.parser.JpdlArchiveParser.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-10-3 下午12:42:37, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Travelsky Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.jbpm.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.jpdl.JpdlException;
import org.jbpm.jpdl.par.ProcessArchive;
import org.jbpm.jpdl.par.ProcessArchiveParser;
import org.xml.sax.InputSource;


/** 
 *
 * <p>
 * <a href="JpdlArchiveParser.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class JpdlArchiveParser  implements ProcessArchiveParser {

	private static final long serialVersionUID = 1L;

	public ProcessDefinition readFromArchive(ProcessArchive processArchive, ProcessDefinition processDefinition) {
		try {
			// getting the value
			byte[] processBytes = processArchive.getEntry("processdefinition.xml");

			if (processBytes == null) {
				throw new JpdlException("no processdefinition.xml inside process archive");
			}

			// creating the JpdlXmlReader
			InputStream processInputStream = new ByteArrayInputStream(processBytes);
			InputSource processInputSource = new InputSource(processInputStream);
			JpdlXmlPlugReader jpdlXmlReader = new JpdlXmlPlugReader(processInputSource, processArchive);

			processDefinition = jpdlXmlReader.readProcessDefinition();

			// close all the streams
			jpdlXmlReader.close();
			processInputStream.close();

		} catch (IOException e) {
			throw new JpdlException("io problem while reading processdefinition.xml: " + e.getMessage(), e);
		}

		return processDefinition;
	}
}