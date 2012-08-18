package com.clouddatamigration.migration.sourceadapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
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

import com.clouddatamigration.classification.model.Project;
import com.clouddatamigration.migration.model.SourceSystem;

public class MysqlLocalSourceSystem implements SourceSystem {

	private static String path;
	private static String tempPath;
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
			tempPath = properties.getProperty("mysql.export.path");
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
		System.out.println(sqlCommands);
	}

	private HashMap<String, String> connectionProperties = null;

	@Override
	public String getId() {
		return "MYSQL_LOCAL_SOURCE";
	}

	@Override
	public ArrayList<String> getConnectionParameters() {
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add("Username");
		parameters.add("Password");
		parameters.add("Host");
		parameters.add("Port");
		parameters.add("Database");
		parameters.add("Compatibility_Mode");
		return parameters;
	}

	@Override
	public boolean connect(HashMap<String, String> connectionProperties,
			ServletOutputStream out) {
		if (out != null) {
			try {
				out.println("Trying to connect to source mysql database.");
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.connectionProperties = connectionProperties;
		try {
			if (out != null) {
				out.println("Source settings: " + connectionProperties);
				out.flush();
			}

			String execString = path + "/mysql -h"
					+ connectionProperties.get("Host") + " -P"
					+ connectionProperties.get("Port") + " -u"
					+ connectionProperties.get("Username") + " -p"
					+ connectionProperties.get("Password") + " -eStatus "
					+ connectionProperties.get("Database");
			if (out != null) {
				out.println("Executing command: " + execString);
				out.flush();
			}
			Process child = Runtime.getRuntime().exec(execString);

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
			}
			if (out != null) {
				out.println("Successfully connected to mysql source database.");
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
		}
		return true;
	}

	@Override
	public HashMap<String, String> getTablesAsCSV(ServletOutputStream out) {
		HashMap<String, String> csvTables = new HashMap<String, String>();
		try {
			// create temporary export dir with rights for mysql to write csv
			// file to
			File exportDir = createTempDir(out);

			// dump one CSV file containing the data per table
			exportDataToCSVFiles(out);

			FilenameFilter csvFilter = new FilenameFilter() {
				@Override
				public boolean accept(File directory, String fileName) {
					return fileName.endsWith(".txt");
				}
			};

			for (File csvFile : exportDir.listFiles(csvFilter)) {
				// for every table get field names to prepend them to the CSV
				// file
				String columnsFile = exportHeadingsToFile(out, csvFile);

				// contains headings as well as data
				StringBuilder fileContent = new StringBuilder();

				// read file with headings
				appendTableHeadings(columnsFile, fileContent);

				// read CSV file
				appendTableData(csvFile, fileContent);

				csvTables.put(csvFile.getName().replace(".txt", ""),
						fileContent.toString());
			}

			deleteTempDir(exportDir, out);

			if (out != null) {
				out.println("Data export from MySQL was successful.");
				out.flush();
			}

			return csvTables;
		} catch (IOException e) {
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
		}
		return null;
	}

	private String exportHeadingsToFile(ServletOutputStream out, File csvFile)
			throws IOException {
		String columnsFile = tempPath + "/dump-"
				+ connectionProperties.get("Database") + "/columns-"
				+ csvFile.getName().replace(".txt", ".csv");

		String[] cmd = new String[] {
				bashPath + "/bash",
				"-c",
				path + "/mysql -h" + connectionProperties.get("Host") + " -P"
						+ connectionProperties.get("Port") + " -u"
						+ connectionProperties.get("Username") + " -p"
						+ connectionProperties.get("Password") + " "
						+ connectionProperties.get("Database")
						+ " -e\"select column_name into outfile '"
						+ columnsFile + "' "
						+ "from information_schema.columns "
						+ "where table_name='"
						+ csvFile.getName().replace(".txt", "")
						+ "' AND table_schema='"
						+ connectionProperties.get("Database") + "';\"" };

		if (out != null) {
			out.println("Executing command: " + Arrays.toString(cmd));
			out.flush();
		}
		Process child = Runtime.getRuntime().exec(cmd);
		InputStream in = child.getInputStream();
		Scanner scanner = new Scanner(in, "UTF-8").useDelimiter("\\A");
		if (scanner.hasNext()) {
			out.println(scanner.next());
		}
		return columnsFile;
	}

	private void appendTableData(File csvFile, StringBuilder fileContent)
			throws IOException {
		FileReader fileReader2 = new FileReader(csvFile);
		BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
		String line = null;
		while ((line = bufferedReader2.readLine()) != null) {
			fileContent.append(line + "\n");
		}
	}

	private void appendTableHeadings(String columnsFile,
			StringBuilder fileContent) throws IOException {
		FileReader fileReader = new FileReader(columnsFile);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String fields = "";
		String field = null;
		while ((field = bufferedReader.readLine()) != null) {
			fields += field + "|";
		}
		fileContent.append(fields.substring(0, fields.length() - 1).replace(
				"|", "\t")
				+ "\n");
	}

	private void deleteTempDir(File exportDir, ServletOutputStream out) {
		try {
			if (exportDir.listFiles() != null) {
				for (File file : exportDir.listFiles()) {
					if (file.delete()) {
						if (out != null) {
							out.println("Deleting temporary file " + file);
						}
					} else {
						if (out != null) {
							out.println("WARNING: Could not delete temporary file "
									+ file);
						}
					}
				}
				if (exportDir.delete()) {
					if (out != null) {
						out.println("Deleting temporary directory " + exportDir);
					}
				} else {
					if (out != null) {
						out.println("WARNING: Could not delete temporary directory "
								+ exportDir);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void exportDataToCSVFiles(ServletOutputStream out)
			throws IOException {
		String[] cmd = {
				bashPath + "/bash",
				"-c",
				path
						+ "/mysqldump -h"
						+ connectionProperties.get("Host")
						+ " -P"
						+ connectionProperties.get("Port")
						+ " -u"
						+ connectionProperties.get("Username")
						+ " -p"
						+ connectionProperties.get("Password")
						+ " --tab=" // --fields-terminated-by=, --fields-enclosed-by=\\\" 
						+ tempPath + "/dump-"
						+ connectionProperties.get("Database") + " "
						+ connectionProperties.get("Database") };

		if (out != null) {
			out.println("Executing command: " + Arrays.toString(cmd));
			out.flush();
		}

		Process child = Runtime.getRuntime().exec(cmd);

		InputStream in = child.getErrorStream();
		Scanner scanner = new Scanner(in, "UTF-8").useDelimiter("\\A");
		if (scanner.hasNext()) {
			if (out != null) {
				out.println(scanner.next());
				out.flush();
			}
		}

		in = child.getInputStream();
		scanner = new Scanner(in, "UTF-8").useDelimiter("\\A");
		if (scanner.hasNext()) {
			if (out != null) {
				out.println(scanner.next());
			}
		}
	}

	private File createTempDir(ServletOutputStream out) throws IOException {
		File exportDir = new File(tempPath + "/dump-"
				+ connectionProperties.get("Database"));

		deleteTempDir(exportDir, out);

		if (exportDir.mkdir()) {
			if (out != null) {

				// setting permission to allow user 'mysql' to create *.csv
				// files
				exportDir.setExecutable(true);
				exportDir.setWritable(true);
				exportDir.setReadable(true);

				String permissionCommand = "chmod 777 " + exportDir.toString();
				Process child = Runtime.getRuntime().exec(permissionCommand);

				if (out != null) {
					out.println("Executing command: " + permissionCommand);
					out.flush();
				}

				InputStream in = child.getErrorStream();
				Scanner scanner = new Scanner(in, "UTF-8").useDelimiter("\\A");
				if (scanner.hasNext()) {
					if (out != null) {
						out.println(scanner.next());
						out.flush();
					}
					return null;
				}

				in = child.getInputStream();
				scanner = new Scanner(in, "UTF-8").useDelimiter("\\A");
				if (scanner.hasNext()) {
					if (out != null) {
						out.println(scanner.next());
						out.flush();
					}
				}

				out.println("Created temporary directory " + exportDir);
			}
		} else {
			if (out != null) {
				out.println("WARNING: Could not create temporary directory "
						+ exportDir);
			}
		}

		return exportDir;
	}

	@Override
	public String getSqlCommands(ServletOutputStream out) {
		try {
			String execString = path
					+ "/mysqldump -h"
					+ connectionProperties.get("Host")
					+ " -P"
					+ connectionProperties.get("Port")
					+ " -u"
					+ connectionProperties.get("Username")
					+ " -p"
					+ connectionProperties.get("Password")
					+ " --hex-blob "
					+ (connectionProperties.get("Compatibility_Mode") != null
							&& !connectionProperties.get("Compatibility_Mode")
									.isEmpty() ? "--compatible="
							+ connectionProperties.get("Compatibility_Mode")
							+ "" : "")
					+ " --skip-extended-insert --skip-add-locks --skip-add-drop-table --skip-comments "
					+ connectionProperties.get("Database");

			if (out != null) {
				out.println("Executing command export whole database with all tables incl. data into SQL file: "
						+ execString);
				out.flush();
			}

			Process child = Runtime.getRuntime().exec(execString);

			InputStream in = child.getInputStream();
			Scanner scanner = new Scanner(in, "UTF-8").useDelimiter("\\A");
			if (scanner.hasNext()) {
				if (out != null) {
					out.println("Data export from MySQL was successful.");
					out.flush();
				}
				return scanner.next();
			}

			in = child.getErrorStream();
			scanner = new Scanner(in, "UTF-8").useDelimiter("\\A");
			if (scanner.hasNext()) {
				if (out != null) {
					out.println(scanner.next());
					out.flush();
					out.println("WARNING: Data export from MySQL failed.");
					out.flush();
				}
				return null;
			}

		} catch (IOException e) {
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
		}
		return null;

	}

	@Override
	public String getInstructions() {
		return "In order to connect to a mysql database you need to provide a username and password, "
				+ "the host name (e.g. localhost, or mysql.domain.com, etc.), the port (default port is 3306) "
				+ "and the name of the database to migrate. If you wish to migrate from a mysql server "
				+ "to a non-mysql server please set compatible mode to one of the following: "
				+ "ansi, mysql323, mysql40, postgresql, oracle, mssql, db2, maxdb, no_key_options, "
				+ "no_table_options, no_field_options. One can use several modes separated by commas. "
				+ "Make sure that our servers (IP address block: 173.194.0.0/16) "
				+ "have temporary access to your mysql server.";
	}

}
