/*
 * File   : $Source: /alkacon/cvs/opencms/src-setup/org/opencms/setup/xml/CmsXmlAddResourceHandlers.java,v $
 * Date   : $Date: 2011/03/23 14:50:09 $
 * Version: $Revision: 1.7 $
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
import org.opencms.configuration.CmsSystemConfiguration;
import org.opencms.configuration.I_CmsXmlConfiguration;
import org.opencms.file.history.CmsHistoryResourceHandler;
import org.opencms.main.CmsPermalinkResourceHandler;
import org.opencms.workplace.CmsWorkplaceLoginHandler;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;

/**
 * Adds the new init resource handler classes, from 6.2.3 to 7.0.x.<p>
 * 
 * @author Michael Moossen
 * 
 * @version $Revision: 1.7 $ 
 * 
 * @since 6.9.2
 */
public class CmsXmlAddResourceHandlers extends A_CmsSetupXmlUpdate {

    /** List of xpaths to update. */
    private List m_xpaths;

    /**
     * @see org.opencms.setup.xml.I_CmsSetupXmlUpdate#getName()
     */
    public String getName() {

        return "Add new init resource handler classes";
    }

    /**
     * @see org.opencms.setup.xml.I_CmsSetupXmlUpdate#getXmlFilename()
     */
    public String getXmlFilename() {

        return CmsSystemConfiguration.DEFAULT_XML_FILE_NAME;
    }

    /**
     * @see org.opencms.setup.xml.A_CmsSetupXmlUpdate#executeUpdate(org.dom4j.Document, java.lang.String, boolean)
     */
    @Override
    protected boolean executeUpdate(Document document, String xpath, boolean forReal) {

        Node node = document.selectSingleNode(xpath);
        if (node == null) {
            if (xpath.equals(getXPathsToUpdate().get(0))) {
                CmsSetupXmlHelper.setValue(
                    document,
                    xpath + "/@" + I_CmsXmlConfiguration.A_CLASS,
                    CmsWorkplaceLoginHandler.class.getName());
            } else if (xpath.equals(getXPathsToUpdate().get(1))) {
                CmsSetupXmlHelper.setValue(
                    document,
                    xpath + "/@" + I_CmsXmlConfiguration.A_CLASS,
                    CmsPermalinkResourceHandler.class.getName());
            } else if (xpath.equals(getXPathsToUpdate().get(2))) {
                CmsSetupXmlHelper.setValue(
                    document,
                    xpath + "/@" + I_CmsXmlConfiguration.A_CLASS,
                    CmsHistoryResourceHandler.class.getName());
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

        // /opencms/system/resourceinit
        StringBuffer xp = new StringBuffer(256);
        xp.append("/").append(CmsConfigurationManager.N_ROOT);
        xp.append("/").append(CmsSystemConfiguration.N_SYSTEM);
        xp.append("/").append(CmsSystemConfiguration.N_RESOURCEINIT);
        return xp.toString();
    }

    /**
     * @see org.opencms.setup.xml.A_CmsSetupXmlUpdate#getXPathsToUpdate()
     */
    @Override
    protected List getXPathsToUpdate() {

        if (m_xpaths == null) {
            // "/opencms/system/resourceinit/resourceinithandler[@class='...']";
            StringBuffer xp = new StringBuffer(256);
            xp.append("/").append(CmsConfigurationManager.N_ROOT);
            xp.append("/").append(CmsSystemConfiguration.N_SYSTEM);
            xp.append("/").append(CmsSystemConfiguration.N_RESOURCEINIT);
            xp.append("/").append(CmsSystemConfiguration.N_RESOURCEINITHANDLER);
            xp.append("[@").append(I_CmsXmlConfiguration.A_CLASS);
            xp.append("='");
            m_xpaths = new ArrayList();
            m_xpaths.add(xp.toString() + CmsWorkplaceLoginHandler.class.getName() + "']");
            m_xpaths.add(xp.toString() + CmsPermalinkResourceHandler.class.getName() + "']");
            m_xpaths.add(xp.toString() + CmsHistoryResourceHandler.class.getName() + "']");
        }
        return m_xpaths;
    }

}