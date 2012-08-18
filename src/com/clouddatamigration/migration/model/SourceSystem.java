package com.clouddatamigration.migration.model;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;

public interface SourceSystem {

	String getId();

	ArrayList<String> getConnectionParameters();

	boolean connect(HashMap<String, String> connectionProperties,
			ServletOutputStream out);

	String getInstructions();

	/**
	 * Returns SQL commands to recreate the tables and their data in a
	 * relational database
	 * 
	 * @param out
	 * @return
	 */
	String getSqlCommands(ServletOutputStream out);

	/**
	 * Returns a map of tables with their exported data as CSV
	 * 
	 * @param out
	 * @return key = table name, value = CSV with headers in first line, values
	 *         in following lines
	 */
	HashMap<String, String> getTablesAsCSV(ServletOutputStream out);
}
