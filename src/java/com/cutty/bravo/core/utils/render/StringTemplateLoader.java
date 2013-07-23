/* com.cutty.bravo.core.utils.render.StringTemplateLoader.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-22 上午05:41:30, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.utils.render;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import freemarker.cache.TemplateLoader;

/**
 *
 * <p>
 * <a href="StringTemplateLoader.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class StringTemplateLoader  implements TemplateLoader {

    public void closeTemplateSource(Object templateSource) throws IOException {
        return;
    }
    public Object findTemplateSource(String name) throws IOException {
        return name;
    }
    public long getLastModified(Object templateSource) {
        return System.currentTimeMillis();
    }
    public Reader getReader(Object templateSource, String encoding)
            throws IOException {
        String ftlString=(String)templateSource;
        return new StringReader(ftlString);
    }
}