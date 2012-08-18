package com.clouddatamigration.migration.model;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public interface TargetSystem {

	/**
	 * Returns a unique ID that also describes the adapter, e.g.
	 * AWS_RDS_MYSQL_TARGET
	 * 
	 * @return
	 */
	String getId();

	/**
	 * Returns true if SQL commands are supported and false if a CSV data import
	 * is required
	 * 
	 * @return
	 */
	boolean supportsSql();

	/**
	 * Returns a list of required or optional connection parameters, the meaning
	 * of each parameter and whether it is required or not is described in the
	 * instructions.
	 * 
	 * @return
	 */
	ArrayList<String> getConnectionParameters();

	/**
	 * Tries to connect to the data store and makes sure that not only the
	 * credentials (connection properties) are valid, but also that some kind of
	 * action can be called that does not change anything.
	 * 
	 * @param connectionProperties
	 * @param out
	 * @return true if successful, false if not
	 */
	boolean connect(HashMap<String, String> connectionProperties,
			ServletOutputStream out);

	/**
	 * Migration using SQL commands, tries to preserve constraints
	 * 
	 * @param sqlCommands
	 * @param resp
	 *            {@link HttpServletResponse} with the possibility to write to
	 *            the output stream, send files or redirect the user
	 * @return true if successful, false if not
	 */
	boolean migrate(String sqlCommands, HttpServletResponse resp);

	/**
	 * Returns printable (html) instructions what the connection parameters
	 * mean, what the import adapter does and what it is capable of.
	 * 
	 * @return
	 */
	String getInstructions();

	/**
	 * Migration using CSV format, ignores constraints
	 * 
	 * @param csvTables
	 * @param resp
	 *            {@link HttpServletResponse} with the possibility to write to
	 *            the output stream, send files or redirect the user
	 * @return true if successful, false if not
	 */
	boolean migrate(HashMap<String, String> csvTables, HttpServletResponse resp);
}
