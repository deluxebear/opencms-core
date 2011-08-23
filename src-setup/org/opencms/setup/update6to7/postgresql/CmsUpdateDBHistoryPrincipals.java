/*
 * File   : $Source: /alkacon/cvs/opencms/src-setup/org/opencms/setup/update6to7/postgresql/CmsUpdateDBHistoryPrincipals.java,v $
 * Date   : $Date: 2011/03/23 14:52:23 $
 * Version: $Revision: 1.6 $
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

package org.opencms.setup.update6to7.postgresql;

import org.opencms.setup.CmsSetupDb;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * PostgreSQL implementation to create the history principals table and contents.<p>
 * 
 * @author Ivan Codarin
 * @author Georgi Naplatanov
 *
 * @version $Revision: 1.6 $
 * 
 * @since 7.0.2
 */
public class CmsUpdateDBHistoryPrincipals extends org.opencms.setup.update6to7.generic.CmsUpdateDBHistoryPrincipals {

    /** Constant for the SQL query properties.<p> */
    private static final String QUERY_PROPERTY_FILE = "postgresql/cms_history_principals_queries.properties";

    /** Constant for the replacement in the sql query. */
    private static final String REPLACEMENT_TABLEINDEX_SPACE = "${indexTablespace}";

    /**
     * Constructor.<p>
     * 
     * @throws IOException if the sql queries properties file could not be read
     */
    public CmsUpdateDBHistoryPrincipals()
    throws IOException {

        super();
        loadQueryProperties(QUERY_PROPERTIES_PREFIX + QUERY_PROPERTY_FILE);
    }

    /**
     * @see org.opencms.setup.update6to7.generic.CmsUpdateDBHistoryPrincipals#createHistPrincipalsTable(org.opencms.setup.CmsSetupDb)
     */
    @Override
    protected void createHistPrincipalsTable(CmsSetupDb dbCon) throws SQLException {

        String indexTablespace = (String)m_poolData.get("indexTablespace");

        System.out.println(new Exception().getStackTrace()[0].toString());
        if (!dbCon.hasTableOrColumn(TABLE_CMS_HISTORY_PRINCIPALS, null)) {
            String createStatement = readQuery(QUERY_HISTORY_PRINCIPALS_CREATE_TABLE);

            HashMap replacer = new HashMap();
            replacer.put(REPLACEMENT_TABLEINDEX_SPACE, indexTablespace);

            dbCon.updateSqlStatement(createStatement, replacer, null);
        } else {
            System.out.println("table " + TABLE_CMS_HISTORY_PRINCIPALS + " already exists");
        }
    }
}