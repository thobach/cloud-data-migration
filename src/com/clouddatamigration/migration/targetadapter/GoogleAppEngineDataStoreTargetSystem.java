package com.clouddatamigration.migration.targetadapter;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.clouddatamigration.migration.model.TargetSystem;

public class GoogleAppEngineDataStoreTargetSystem implements TargetSystem {

	@Override
	public String getId() {
		return "GOOGLE_APP_ENGINE_DATASTORE_TARGET";
	}

	@Override
	public ArrayList<String> getConnectionParameters() {
		ArrayList<String> parameters = new ArrayList<String>();
		return parameters;
	}

	@Override
	public boolean connect(HashMap<String, String> connectionProperties,
			ServletOutputStream out) {
		return true;
	}

	@Override
	public boolean migrate(String sqlCommands, HttpServletResponse resp) {
		return true;
	}

	@Override
	public boolean supportsSql() {
		return true;
	}

	@Override
	public boolean migrate(HashMap<String, String> csvTables,
			HttpServletResponse resp) {
		return true;
	}

	@Override
	public String getInstructions() {
		return "In order to import your data from a CSV or XML file into the Google App Engine Datastore you need to follow the "
				+ "<a href=\"https://developers.google.com/appengine/docs/python/tools/uploadingdata#Configuring_the_Bulk_Loader\">instructions provided by Google App Engine</a>. "
				+ "In a nutshell you need to export every table into a CSV or XML file, "
				+ "create an import configuration file including data transformation rules for every table and "
				+ "run a python script to upload your data for every table.";
	}
}
