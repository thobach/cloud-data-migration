package com.clouddatamigration.migration.targetadapter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.clouddatamigration.migration.model.SourceSystem;
import com.clouddatamigration.migration.model.TargetSystem;
import com.clouddatamigration.migration.sourceadapter.MysqlLocalSourceSystem;

public class AwsRdsSqlServerTargetSystem extends MysqlLocalTargetSystem
		implements TargetSystem {

	public static void main(String[] args) {

		// tests
		SourceSystem mysqlLocalSourceSystem = new MysqlLocalSourceSystem();
		HashMap<String, String> myConnectionProperties = new HashMap<String, String>();
		myConnectionProperties.put("Username", "root");
		myConnectionProperties.put("Password", "password");
		myConnectionProperties.put("Host", "localhost");
		myConnectionProperties.put("Port", "3306");
		myConnectionProperties.put("Database", "clouddatamigration");
		myConnectionProperties.put("Compatibility_Mode", "mssql");
		mysqlLocalSourceSystem.connect(myConnectionProperties, null);
		String sqlCommands = mysqlLocalSourceSystem.getSqlCommands(null);

		AwsRdsSqlServerTargetSystem sqlServerRDSTargetSystem = new AwsRdsSqlServerTargetSystem();
		myConnectionProperties.put("Host",
				"bn8jpaeuwk.database.windows.net");
		myConnectionProperties.put("Database", "clouddatamigration");
		myConnectionProperties.put("Username", "cdm");
		myConnectionProperties.put("Password", "Password1");
		myConnectionProperties.put("Port", "1433");
		sqlServerRDSTargetSystem.connect(myConnectionProperties, null);

		System.out.println(sqlServerRDSTargetSystem.migrate(sqlCommands, null));
	}

	private HashMap<String, String> connectionProperties = null;

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
				out.println("Trying to connect to target SQL Server database.");
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

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connect = DriverManager.getConnection(
					"jdbc:sqlserver://" + connectionProperties.get("Host")
							+ ":" + connectionProperties.get("Port")
							+ ";DatabaseName="
							+ connectionProperties.get("Database") + "",
					connectionProperties.get("Username"),
					connectionProperties.get("Password"));
			Statement statement = connect.createStatement();
			ResultSet resultSet = statement
					.executeQuery("SELECT * FROM sys.databases");

			for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
				if (out != null) {
					out.print(resultSet.getMetaData().getColumnName(i) + "\t");
					out.flush();
				}
			}
			if (out != null) {
				out.println("");
				out.flush();
			}

			while (resultSet.next()) {
				for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
					if (out != null) {
						out.print(resultSet.getString(i) + "\t");
						out.flush();
					}
				}
				if (out != null) {
					out.println("");
					out.flush();
				}
			}

			if (out != null) {
				out.println("Successfully connected to target database.");
				out.flush();
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
					out.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			return false;
		} catch (ClassNotFoundException e) {
			if (out != null) {
				try {
					out.println("SQL Server JDBC Driver not found: "
							+ e.getLocalizedMessage());
					out.flush();
				} catch (IOException e1) {
				}
			}
			e.printStackTrace();
		} catch (SQLException e) {
			if (out != null) {
				try {
					out.println("SQL command could not be executed: "
							+ e.getLocalizedMessage());
					out.flush();
				} catch (IOException e1) {
				}
			}
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean migrate(String sqlCommands, HttpServletResponse resp) {

		// collect constraints to add them add the end of the script
		Pattern pattern = Pattern
				.compile(
						"CREATE TABLE \"([^\"]*?)\" \\((\\r|\\n)([^;]*?)  CONSTRAINT \"(.*?)\" FOREIGN KEY \\(\"(.*?)\"\\) REFERENCES \"(.*?)\" \\(\"(.*?)\"\\)(.*?)\\);",
						Pattern.MULTILINE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(sqlCommands);
		ArrayList<String> constraints = new ArrayList<String>();
		String tableName = "";
		while (matcher.find()) {
			tableName = matcher.group(1);
			Pattern pattern2 = Pattern
					.compile(
							"CONSTRAINT \"(.*?)\" FOREIGN KEY \\(\"(.*?)\"\\) REFERENCES \"(.*?)\" \\(\"(.*?)\"\\)",
							Pattern.MULTILINE | Pattern.DOTALL);
			Matcher matcher2 = pattern2.matcher(matcher.group());
			while (matcher2.find()) {
				constraints.add("ALTER TABLE \"" + tableName + "\"" + " ADD "
						+ matcher2.group(0));
			}
		}

		sqlCommands = sqlCommands
		// replace unsupported data types
				.replace(" int(11) ", " int ")
				// remove key definition
				.replaceAll("  KEY \"(.*?)\" \\(\"(.*?)\"\\),(\\r|\\n)", "")
				// drop unique key constraint
				.replaceAll(",(\\r|\\n)  UNIQUE KEY \"(.*?)\" \\(\"(.*?)\"\\)",
						"")
				// drop foreign key constraints
				.replaceAll(
						"(?s),?(\\r|\\n)  CONSTRAINT \"(.*?)\" FOREIGN KEY \\(\"(.*?)\"\\) REFERENCES \"(.*?)\" \\(\"(.*?)\"\\)(.*?)\\);",
						");")
				// escape characters
				.replaceAll("\\\\'", "''");

		for (String constraint : constraints) {
			sqlCommands += constraint + ";\n";
		}

		ServletOutputStream out = null;

		try {

			if (resp != null) {
				out = resp.getOutputStream();
			}

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connect = DriverManager.getConnection(
					"jdbc:sqlserver://" + connectionProperties.get("Host")
							+ ":" + connectionProperties.get("Port")
							+ ";DatabaseName="
							+ connectionProperties.get("Database") + "",
					connectionProperties.get("Username"),
					connectionProperties.get("Password"));
			Statement statement = connect.createStatement();

			if (out != null) {
				out.println("Connected to RDS SQL Server target system.");
				out.flush();
			}

			for (String sqlCommand : sqlCommands.split(";")) {
				if (sqlCommand != null && !sqlCommand.isEmpty()) {
					try {
						ResultSet resultSet = statement
								.executeQuery(sqlCommand);
						if (resultSet != null) {
							resultSet.close();
						}
						if (out != null) {
							// out.println("Executed command: " + sqlCommand);
							// out.flush();
						}
					} catch (Exception e) {
						if (!e.getLocalizedMessage()
								.equals("Es wurde kein Resultset von der Anweisung zurückgegeben.")) {
							if (out != null) {
								out.println("Could not execute command: '"
										+ sqlCommand + "' due to: "
										+ e.getLocalizedMessage());
								out.flush();
							}
						}
					}
				}
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}

			if (out != null) {
				out.println("Data import to AWS RDS SQL Server was successful, although some SQL statements might not have been executed (see above).");
				out.flush();
			}

			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String getId() {
		return "AWS_RDS_SQLSERVER_TARGET";
	}

	@Override
	public String getInstructions() {
		return "In order to migrate from a mysql database to Amazon Relational Database Service (SQL Server engine) "
				+ "you need to setup a RDS SQL Server instance and provide a username and password, the host name (e.g. db-instance-name.servername.region.rds.amazonaws.com), "
				+ "the port (default port is 1433) and the name of the database to migrate to. "
				+ "Make sure that your mysql database does not use any non-standard SQL features and that our servers (IP address block: 173.194.0.0/16) have temporary access to your mysql server. "
				+ "During the migration the follwing substitutions are done: "
				+ "\' is escaped by '', data type int(11) is replaced by int, simple KEY definitions are removed, "
				+ "UNIQUE KEY definitions are removed, foreign key constraints are moved to the end of the migration.";
	}
}
