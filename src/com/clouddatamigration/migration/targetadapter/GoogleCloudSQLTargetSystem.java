package com.clouddatamigration.migration.targetadapter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.clouddatamigration.migration.model.TargetSystem;

public class GoogleCloudSQLTargetSystem implements TargetSystem {

	@Override
	public String getId() {
		return "GOOGLE_CLOUD_SQL_TARGET";
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
		resp.setContentType("text/plain");
		resp.setHeader("Content-Disposition", "attachment;filename=dump.sql");
		try {
			resp.getOutputStream().write(sqlCommands.getBytes("UTF-8"));
			return true;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean supportsSql() {
		return true;
	}

	@Override
	public boolean migrate(HashMap<String, String> csvTables,
			HttpServletResponse resp) {
		System.out
				.println("Migration to Google Cloud SQL using CSV tables is not supported.");
		try {
			resp.getOutputStream()
					.println(
							"Migration to Google Cloud SQL using CSV tables is not supported.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String getInstructions() {
		return "In order to migrate from a mysql database to Google Cloud SQL you need to follow the "
				+ "<a href=\"https://developers.google.com/cloud-sql/docs/import_export\">instructions provided by Google</a>. "
				+ "In a nutshell you need to create a sql dump of your database (or select a source system and provide the required parameters), "
				+ "upload it to Google Cloud Storage and then go to your Google APIs console and "
				+ "import the dump file from the Google Cloud Storage bucket into your Google Cloud SQL instance.";
	}
}
