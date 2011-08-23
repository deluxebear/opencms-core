/*
 * File   : $Source: /alkacon/cvs/opencms/src-setup/org/opencms/setup/xml/CmsXmlAddHtmlConverters.java,v $
 * Date   : $Date: 2011/03/23 14:50:08 $
 * Version: $Revision: 1.3 $
 *
 * This library is part of OpenCms -
 * the Open Source Content Management System
 *
 * Copyright (c) 2002 - 2011 Alkacon Software GmbH (http://www.alkacon.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about Alkacon Software GmbH, please see the
 * company website: http://www.alkacon.com
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.opencms.setup.xml;

import org.opencms.configuration.CmsConfigurationManager;
import org.opencms.configuration.CmsVfsConfiguration;
import org.opencms.configuration.I_CmsXmlConfiguration;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;

/**
 * Adds the new html converters.<p>
 * 
 * @author Michael Moossen
 * 
 * @version $Revision: 1.3 $ 
 * 
 * @since 6.9.2
 */
public class CmsXmlAddHtmlConverters extends A_CmsSetupXmlUpdate {

    /** List of xpaths to update. */
    private List m_xpaths;

    /**
     * @see org.opencms.setup.xml.I_CmsSetupXmlUpdate#getName()
     */
    public String getName() {

        return "Add new html converters";
    }

    /**
     * @see org.opencms.setup.xml.I_CmsSetupXmlUpdate#getXmlFilename()
     */
    public String getXmlFilename() {

        return CmsVfsConfiguration.DEFAULT_XML_FILE_NAME;
    }

    /**
     * @see org.opencms.setup.xml.A_CmsSetupXmlUpdate#executeUpdate(org.dom4j.Document, java.lang.String, boolean)
     */
    @Override
    protected boolean executeUpdate(Document document, String xpath, boolean forReal) {

        Node node = document.selectSingleNode(xpath);
        if (node == null) {
            for (int i = 0; i < m_htmlConverters.length; i++) {
                if (xpath.equals(getXPathsToUpdate().get(i))) {
                    CmsSetupXmlHelper.setValue(
                        document,
                        xpath + "/@" + I_CmsXmlConfiguration.A_NAME,
                        m_htmlConverters[i]);
                    CmsSetupXmlHelper.setValue(
                        document,
                        xpath + "/@" + I_CmsXmlConfiguration.A_CLASS,
                        "org.opencms.util.CmsHtmlConverterJTidy");
                }
            }
            return true;
        }
        return false;
    }

    /**
     * @see org.opencms.setup.xml.A_CmsSetupXmlUpdate#getCommonPath()
     */
    @Override
    protected String getCommonPath() {

        // "/opencms/vfs/resources/html-converters"
        StringBuffer xp = new StringBuffer(256);
        xp.append("/").append(CmsConfigurationManager.N_ROOT);
        xp.append("/").append(CmsVfsConfiguration.N_VFS);
        xp.append("/").append(CmsVfsConfiguration.N_RESOURCES);
        xp.append("/").append(CmsVfsConfiguration.N_HTML_CONVERTERS);
        return xp.toString();
    }

    /** List of converters to add. */
    private String[] m_htmlConverters = {"true", "xhtml", "cleanup", "replace-paragraphs"};

    /**
     * @see org.opencms.setup.xml.A_CmsSetupXmlUpdate#getXPathsToUpdate()
     */
    @Override
    protected List getXPathsToUpdate() {

        if (m_xpaths == null) {
            // "/opencms/vfs/resources/html-converters/html-converter[@name='...']";
            StringBuffer xp = new StringBuffer(256);
            xp.append("/").append(CmsConfigurationManager.N_ROOT);
            xp.append("/").append(CmsVfsConfiguration.N_VFS);
            xp.append("/").append(CmsVfsConfiguration.N_RESOURCES);
            xp.append("/").append(CmsVfsConfiguration.N_HTML_CONVERTERS);
            xp.append("/").append(CmsVfsConfiguration.N_HTML_CONVERTER);
            xp.append("[@").append(I_CmsXmlConfiguration.A_NAME);
            xp.append("='");
            m_xpaths = new ArrayList();
            for (int i = 0; i < m_htmlConverters.length; i++) {
                m_xpaths.add(xp.toString() + m_htmlConverters[i] + "']");
            }
        }

        return m_xpaths;
    }

}