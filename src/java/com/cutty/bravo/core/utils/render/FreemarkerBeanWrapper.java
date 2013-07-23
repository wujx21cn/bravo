/* com.cutty.bravo.core.utils.render.FreemarkerBeanWrapper.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-22 上午05:38:07, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.utils.render;

import java.util.Map;
import java.util.Set;

import freemarker.core.CollectionAndSequence;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.MapModel;
import freemarker.ext.util.ModelFactory;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleSequence;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateCollectionModel;
import freemarker.template.TemplateHashModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 *
 * <p>
 * <a href="FreemarkerBeanWrapper.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class FreemarkerBeanWrapper  extends BeansWrapper {


    @Override
	public TemplateModel wrap(Object object) throws TemplateModelException {
        if (object instanceof TemplateBooleanModel) {
            return super.wrap(object);
        }

        // attempt to get the best of both the SimpleMapModel and the MapModel of FM.
        if (object instanceof Map) {
            return getInstance(object, FriendlyMapModel.FACTORY);
        }

        return super.wrap(object);
    }

    /**
     * Attempting to get the best of both worlds of FM's MapModel and SimpleMapModel, by reimplementing the isEmpty(),
     * keySet() and values() methods. ?keys and ?values built-ins are thus available, just as well as plain Map
     * methods.
     */
    private final static class FriendlyMapModel extends MapModel implements TemplateHashModelEx {
        static final ModelFactory FACTORY = new ModelFactory() {
            public TemplateModel create(Object object, ObjectWrapper wrapper) {
                return new FriendlyMapModel((Map) object, (BeansWrapper) wrapper);
            }
        };

        public FriendlyMapModel(Map map, BeansWrapper wrapper) {
            super(map, wrapper);
        }

        @Override
		public boolean isEmpty() {
            return ((Map) object).isEmpty();
        }

        @Override
		protected Set keySet() {
            return ((Map) object).keySet();
        }

        @Override
		public TemplateCollectionModel values() {
            return new CollectionAndSequence(new SimpleSequence(((Map) object).values(), wrapper));
        }
    }
}