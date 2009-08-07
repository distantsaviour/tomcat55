/*
 * Copyright 1999-2001,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.catalina.storeconfig;

import java.io.PrintWriter;

import org.apache.catalina.Loader;
import org.apache.catalina.loader.WebappLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Store Loader Element *
 * 
 * @author Peter Rossbach
 */
public class LoaderSF extends StoreFactoryBase {

    private static Log log = LogFactory.getLog(LoaderSF.class);

    /**
     * Store the only the Loader elements, when not default
     * 
     * @see NamingResourcesSF#storeChilds(PrintWriter, int, Object,
     *      RegistryDescription)
     */
    public void store(PrintWriter aWriter, int indent, Object aElement)
            throws Exception {
        StoreDescription elementDesc = getRegistry().findDescription(
                aElement.getClass());
        if (elementDesc != null) {
            Loader loader = (Loader) aElement;
            if (!isDefaultLoader(loader)) {
                if (log.isDebugEnabled())
                    log.debug("store " + elementDesc.getTag() + "( " + aElement
                            + " )");
                getStoreAppender().printIndent(aWriter, indent + 2);
                getStoreAppender().printTag(aWriter, indent + 2, loader,
                        elementDesc);
            }
        } else {
            if (log.isWarnEnabled()) {
                log
                        .warn("Descriptor for element"
                                + aElement.getClass()
                                + " not configured or element class not StandardManager!");
            }
        }
    }

    /**
     * Is this an instance of the default <code>Loader</code> configuration,
     * with all-default properties?
     * 
     * @param loader
     *            Loader to be tested
     */
    protected boolean isDefaultLoader(Loader loader) {

        if (!(loader instanceof WebappLoader)) {
            return (false);
        }
        WebappLoader wloader = (WebappLoader) loader;
        if ((wloader.getDelegate() != false)
                || !wloader.getLoaderClass().equals(
                        "org.apache.catalina.loader.WebappClassLoader")) {
            return (false);
        }
        return (true);
    }
}