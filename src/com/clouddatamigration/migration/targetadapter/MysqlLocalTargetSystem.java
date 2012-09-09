package com.clouddatamigration.migration.targetadapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.clouddatamigration.classification.model.Project;
import com.clouddatamigration.migration.model.SourceSystem;
import com.clouddatamigration.migration.model.TargetSystem;
import com.clouddatamigration.migration.sourceadapter.MysqlLocalSourceSystem;

public class MysqlLocalTargetSystem implements TargetSystem {

	private static String path;
	private static String exportPath;
	private static String bashPath;

	static {
		try {
			Properties properties = new Properties();
			InputStream propertiesStream = Project.class
					.getResourceAsStream("/clouddatamigration.properties");
			properties.load(propertiesStream);
			propertiesStream.close();
			path = properties.getProperty("mysql.mysqldump.path");
			bashPath = properties.getProperty("bash.path");
			exportPath = properties.getProperty("mysql.export.path");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		// tests
		SourceSystem mysqlLocalSourceSystem = new MysqlLocalSourceSystem();
		HashMap<String, String> myConnectionProperties = new HashMap<String, String>();
		myConnectionProperties.put("Username", "root");
		myConnectionProperties.put("Password", "password");
		myConnectionProperties.put("Host", "localhost");
		myConnectionProperties.put("Port", "3306");
		myConnectionProperties.put("Database", "clouddatamigration");
		mysqlLocalSourceSystem.connect(myConnectionProperties, null);
		String sqlCommands = mysqlLocalSourceSystem.getSqlCommands(null);

		TargetSystem mysqlLocalTargetSystem = new MysqlLocalTargetSystem();
		myConnectionProperties.put("Database", "clouddatamigrationimport");
		mysqlLocalTargetSystem.connect(myConnectionProperties, null);
		System.out.println(mysqlLocalTargetSystem.migrate(sqlCommands, null));
	}

	private HashMap<String, String> connectionProperties = null;

	@Override
	public String getId() {
		return "MYSQL_LOCAL_TARGET";
	}

	@Override
	public ArrayList<String> getConnectionParameters() {
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add("Username");
		parameters.add("Password");
		parameters.add("Host");
		parameters.add("Port");
		parameters.add("Database");
		return parameters;
	}

	@Override
	public boolean connect(HashMap<String, String> connectionProperties,
			ServletOutputStream out) {
		if (out != null) {
			try {
				out.println("Trying to connect to target mysql database.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.connectionProperties = connectionProperties;
		try {
			if (out != null) {
				out.println("Target settings: " + connectionProperties);
				out.flush();
			}
			Process child = Runtime.getRuntime().exec(
					path + "/mysql -h" + connectionProperties.get("Host")
							+ " -P" + connectionProperties.get("Port") + " -u"
							+ connectionProperties.get("Username") + " -p"
							+ connectionProperties.get("Password")
							+ " -eStatus "
							+ connectionProperties.get("Database"));

			InputStream in = child.getErrorStream();
			Scanner scanner = new Scanner(in, "UTF-8").useDelimiter("\\A");
			if (scanner.hasNext()) {
				if (out != null) {
					out.println(scanner.next());
					out.flush();
				}
				return false;
			}

			in = child.getInputStream();
			scanner = new Scanner(in, "UTF-8").useDelimiter("\\A");
			if (scanner.hasNext()) {
				if (out != null) {
					out.println(scanner.next());
				}
			}
			if (out != null) {
				out.println("Successfully connected to mysql target database.");
			}
		} catch (IOException e) {
			// show stack trace in the logs
			e.printStackTrace();

			if (out != null) {
				// show stack trace in the browser
				StringWriter stringWriter = new StringWriter();
				PrintWriter printWriter = new PrintWriter(stringWriter);
				e.printStackTrace(printWriter);
				try {
					out.println(stringWriter.toString());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			return false;
		}
		return true;
	}

	@Override
	public boolean supportsSql() {
		return true;
	}

	@Override
	public boolean migrate(HashMap<String, String> csvTables,
			HttpServletResponse resp) {
		System.out
				.println("Migration to MySQL using CSV tables is not supported.");
		try {
			resp.getOutputStream().println(
					"Migration to MySQL using CSV tables is not supported.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean migrate(String sqlCommands, HttpServletResponse resp) {
		ServletOutputStream out = null;
		try {
			if (resp != null) {
				out = resp.getOutputStream();
			}
			File sqlFile = new File(exportPath + "/dump.sql");
			if (out != null) {
				out.println("Creating temporary file with SQL commands: "
						+ sqlFile);
				out.flush();
			}
			sqlFile.createNewFile();
			FileWriter fw = new FileWriter(sqlFile);
			fw.write(sqlCommands);
			fw.close();

			String[] cmd = {
					bashPath + "/bash",
					"-c",
					path + "/mysql -h" + connectionProperties.get("Host")
							+ " -P" + connectionProperties.get("Port") + " -u"
							+ connectionProperties.get("Username") + " -p"
							+ connectionProperties.get("Password") + " "
							+ connectionProperties.get("Database") + " < "
							+ sqlFile.getAbsolutePath() };
			if (out != null) {
				out.println("Executing command: " + Arrays.toString(cmd));
				out.flush();
			}

			Process child = Runtime.getRuntime().exec(cmd);
			// wait until execution is done (returns), before deleting files
			// that the process needs
			child.waitFor();

			if (out != null) {
				out.println("Execution done.");
				out.flush();
			}

			if (sqlFile.delete()) {
				if (out != null) {
					out.println("Deleted temporary file with SQL commands: "
							+ sqlFile);
					out.flush();
				}
			} else {
				if (out != null) {
					out.println("WARNING! Could not delete temporary file with SQL commands: "
							+ sqlFile);
					out.flush();
				}
			}

			InputStream in = child.getErrorStream();
			Scanner scanner = new Scanner(in, "UTF-8").useDelimiter("\\A");
			if (scanner.hasNext()) {
				if (out != null) {
					out.println(scanner.next());
					out.flush();
				}
				return false;
			}

			in = child.getInputStream();
			scanner = new Scanner(in, "UTF-8").useDelimiter("\\A");
			if (scanner.hasNext()) {
				if (out != null) {
					out.println(scanner.next());
					out.flush();
				}
				return true;
			}
			return true;
		} catch (IOException e) {
			// show stack trace in log
			e.printStackTrace();

			if (out != null) {
				// show stack trace in the browser
				StringWriter stringWriter = new StringWriter();
				PrintWriter printWriter = new PrintWriter(stringWriter);
				e.printStackTrace(printWriter);
				try {
					out.println(stringWriter.toString());
					out.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String getInstructions() {
		return "In order to connect to a mysql database you need to provide a username and password, "
				+ "the host name (e.g. localhost, or mysql.domain.com, etc.), the port (default port is 3306) "
				+ "and the name of the database into which the data should be migrated to. "
				+ "The database has to be created by you in advance. Make sure that our servers "
				+ "(IP address block: 173.194.0.0/16) have temporary access to your mysql server.";
	}
}
