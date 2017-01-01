/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.pims.dao;

import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.pims.model.StatusMaster;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
/**
 * @author deepak
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class StatusMasterDAO implements Serializable
{
	@PersistenceContext
	private EntityManager entityManager;
	    
	public Session  getCurrentSession() {
		return entityManager.unwrap(Session.class);
	}
	
	public void createStatusMaster(StatusMaster statusMaster)
	{
		try
		{
			getCurrentSession().save(statusMaster);
			

		}
		catch (Exception e)
		{
			
			throw new ApplicationRuntimeException(STR_HIB_EXP+e.getMessage(),e);
		}

	}

	public void updateStatusMaster(StatusMaster statusMaster)
	{
		try
		{
			getCurrentSession().saveOrUpdate(statusMaster);
		}
		catch (Exception e)
		{
			
			throw new ApplicationRuntimeException(STR_HIB_EXP+e.getMessage(),e);
		}

	}

	public void removeStatusMaster(StatusMaster statusMaster)
	{
		try
		{

			getCurrentSession().delete(statusMaster);
			

		}
		catch (Exception e)
		{
			
			throw new ApplicationRuntimeException(STR_HIB_EXP+e.getMessage(),e);
		}

	}

	public StatusMaster getStatusMaster(int stID)
	{
		try
		{

			StatusMaster sm=(StatusMaster)getCurrentSession().get(StatusMaster.class,Integer.valueOf(stID));

			return sm ;
		}
		catch (Exception e)
		{
				
			throw new ApplicationRuntimeException(STR_HIB_EXP+e.getMessage(),e);
		}


	}

	public StatusMaster getStatusMaster(String name)
		{
			try
			{
				Query qry = getCurrentSession().createQuery("from StatusMaster P where P.name =:name ");
				qry.setString("name", name);
				return (StatusMaster)qry.uniqueResult();
			}
			catch (Exception e)
			{
					throw new ApplicationRuntimeException(STR_HIB_EXP+e.getMessage(),e);
			}


	}






	private final static String STR_HIB_EXP = "Hibernate Exception : ";




}