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

public class AwsRdsOracleTargetSystem extends MysqlLocalTargetSystem implements
		TargetSystem {

	public static void main(String[] args) {

		// tests
		SourceSystem mysqlLocalSourceSystem = new MysqlLocalSourceSystem();
		HashMap<String, String> myConnectionProperties = new HashMap<String, String>();
		myConnectionProperties.put("Username", "root");
		myConnectionProperties.put("Password", "password");
		myConnectionProperties.put("Host", "localhost");
		myConnectionProperties.put("Port", "3306");
		myConnectionProperties.put("Database", "clouddatamigration");
		myConnectionProperties.put("Compatibility_Mode", "oracle");
		mysqlLocalSourceSystem.connect(myConnectionProperties, null);
		String sqlCommands = mysqlLocalSourceSystem.getSqlCommands(null);

		AwsRdsOracleTargetSystem oracleRDSTargetSystem = new AwsRdsOracleTargetSystem();
		myConnectionProperties
				.put("Host",
						"cloud-data-migration-oracle.c2xxqycdl2pv.eu-west-1.rds.amazonaws.com");
		myConnectionProperties.put("SID", "CLOUDDA2");
		myConnectionProperties.put("Username", "testdb2");
		myConnectionProperties.put("Port", "1521");
		oracleRDSTargetSystem.connect(myConnectionProperties, null);

		System.out.println(oracleRDSTargetSystem.migrate(sqlCommands, null));
	}

	private HashMap<String, String> connectionProperties = null;

	@Override
	public ArrayList<String> getConnectionParameters() {
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add("Username");
		parameters.add("Password");
		parameters.add("Host");
		parameters.add("Port");
		parameters.add("SID");
		return parameters;
	}

	@Override
	public boolean connect(HashMap<String, String> connectionProperties,
			ServletOutputStream out) {
		if (out != null) {
			try {
				out.println("Trying to connect to target oracle database.");
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

			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connect = DriverManager.getConnection(
					"jdbc:oracle:thin:@" + connectionProperties.get("Host")
							+ ":" + connectionProperties.get("Port") + ":"
							+ connectionProperties.get("SID") + "",
					connectionProperties.get("Username"),
					connectionProperties.get("Password"));
			Statement statement = connect.createStatement();
			ResultSet resultSet = statement
					.executeQuery("select object_name, object_type from dba_objects where owner = '"
							+ connectionProperties.get("Username")
									.toUpperCase()
							+ "' order by object_type, object_name");

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
					out.println("Oracle JDBC Driver not found: "
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
				.replace("\" text", "\" CLOB")
				.replace("\" datetime", "\" DATE")
				// remove non-defined cascading information
				.replace(" ON DELETE NO ACTION ON UPDATE NO ACTION", "")
				// remove key definition
				.replaceAll("  KEY \"(.*?)\" \\(\"(.*?)\"\\),(\\r|\\n)", "")
				// switch order of nullable and default value
				.replaceAll("NOT NULL DEFAULT '(.*?)',",
						"DEFAULT '$1' NOT NULL,")
				// drop unique key constraint
				.replaceAll(",(\\r|\\n)  UNIQUE KEY \"(.*?)\" \\(\"(.*?)\"\\)",
						"")
				// drop foreign key constraints
				.replaceAll(
						"(?s),?(\\r|\\n)  CONSTRAINT \"(.*?)\" FOREIGN KEY \\(\"(.*?)\"\\) REFERENCES \"(.*?)\" \\(\"(.*?)\"\\)(.*?)\\);",
						");")
				// escape characters
				.replaceAll("\\\\'", "''")
				// apply function to dates
				.replaceAll(
						",'(19|20)\\d\\d([- /.])(0[1-9]|1[012])\\2(0[1-9]|[12][0-9]|3[01]) [0-2]?[0-9]:[0-6]?[0-9]:[0-6]?[0-9]'",
						",TO_DATE($0, 'yyyy-mm-dd hh24:mi:ss')")
				.replaceAll(",TO_DATE\\(,", ",TO_DATE(")
				// add schema
				.replaceAll(
						"CREATE TABLE \"([a-z|A-Z|_|-|.]*)\" \\(",
						"CREATE TABLE \""
								+ connectionProperties.get("Username")
										.toUpperCase() + "\".\"$1\" (")
				.replaceAll(
						"INSERT INTO \"([a-z|A-Z|_|-|.]*)\" VALUES",
						"INSERT INTO \""
								+ connectionProperties.get("Username")
										.toUpperCase() + "\".\"$1\" VALUES");

		for (String constraint : constraints) {
			sqlCommands += constraint + ";\n";
		}

		ServletOutputStream out = null;

		try {

			if (resp != null) {
				out = resp.getOutputStream();
			}

			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connect = DriverManager.getConnection(
					"jdbc:oracle:thin:@" + connectionProperties.get("Host")
							+ ":" + connectionProperties.get("Port") + ":"
							+ connectionProperties.get("SID") + "",
					connectionProperties.get("Username"),
					connectionProperties.get("Password"));
			Statement statement = connect.createStatement();

			if (out != null) {
				out.println("Connected to RDS Oracle target system.");
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
					} catch (NullPointerException e) {
						e.printStackTrace();
						// ignore (SQL command has no return value)
					} catch (Exception e) {
						if (out != null) {
							out.println("Could not execute command: '"
									+ sqlCommand + "' due to: "
									+ e.getLocalizedMessage());
							out.flush();
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
				out.println("Data import to AWS RDS Oracle was successful, although some SQL statements might not have been executed (see above).");
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
		return "AWS_RDS_ORACLE_TARGET";
	}

	@Override
	public String getInstructions() {
		return "In order to migrate from a mysql database to Amazon Relational Database Service (Oracle engine) "
				+ "you need to setup a RDS Oracle instance and provide a username and password, the host name (e.g. db-instance-name.servername.region.rds.amazonaws.com), "
				+ "the port (default port is 1521) and the name of the database (also called SID) to migrate to. "
				+ "Make sure that your mysql database does not use any non-standard SQL features and that our servers (IP address block: 173.194.0.0/16) have temporary access to your mysql server. "
				+ "During the migration the follwing substitutions are done: "
				+ "\' is escaped by '', data type int(11) is replaced by int, data type text is replaced by CLOB, data type datetime is replaced by DATE, "
				+ "'ON DELETE NO ACTION ON UPDATE NO ACTION' is removed as well as simple KEY definitions, NOT NULL DEFAULT * is replaced by DEFAULT * NOT NULL, "
				+ "UNIQUE KEY definitions are removed, foreign key constraints are moved to the end of the migration and "
				+ "datetime values are imported using the TO_DATE function.";
	}
}
