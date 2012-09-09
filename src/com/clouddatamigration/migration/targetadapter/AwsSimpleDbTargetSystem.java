package com.clouddatamigration.migration.targetadapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.clouddatamigration.migration.model.SourceSystem;
import com.clouddatamigration.migration.model.TargetSystem;
import com.clouddatamigration.migration.sourceadapter.MysqlLocalSourceSystem;

public class AwsSimpleDbTargetSystem implements TargetSystem {

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

		AwsSimpleDbTargetSystem oracleRDSTargetSystem = new AwsSimpleDbTargetSystem();
		myConnectionProperties
				.put("Host",
						"cloud-data-migration-oracle.c2xxqycdl2pv.eu-west-1.rds.amazonaws.com");
		myConnectionProperties.put("SID", "CLOUDDA2");
		oracleRDSTargetSystem.connect(myConnectionProperties, null);
		System.out.println(oracleRDSTargetSystem.migrate(sqlCommands, null));
	}

	private HashMap<String, String> connectionProperties = null;

	@Override
	public ArrayList<String> getConnectionParameters() {
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add("AWSSecretKey");
		parameters.add("AWSAccessKeyId");
		parameters.add("DomainPrefix");
		return parameters;
	}

	@Override
	public boolean connect(HashMap<String, String> connectionProperties,
			ServletOutputStream out) {
		if (out != null) {
			try {
				out.println("Trying to connect to target Simple DB data store.");
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

			AmazonSimpleDB sdb = new AmazonSimpleDBClient(
					new BasicAWSCredentials(
							connectionProperties.get("AWSAccessKeyId"),
							connectionProperties.get("AWSSecretKey")));

			// test connection using this API call
			for (String domain : sdb.listDomains().getDomainNames()) {
				out.println("Found domain: " + domain);
				out.flush();
			}

			if (out != null) {
				out.println("Successfully connected to target database.");
				out.flush();
			}
		} catch (Exception e) {
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
	public boolean supportsSql() {
		return false;
	}

	@Override
	public boolean migrate(String sqlCommands, HttpServletResponse out) {
		System.out
				.println("Migration to Amazon SimpleDB using SQL commands is not supported.");
		try {
			out.getOutputStream()
					.println(
							"Migration to Amazon SimpleDB using SQL commands is not supported.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean migrate(HashMap<String, String> csvTables,
			HttpServletResponse resp) {

		AmazonSimpleDB sdb = new AmazonSimpleDBClient(new BasicAWSCredentials(
				connectionProperties.get("AWSAccessKeyId"),
				connectionProperties.get("AWSSecretKey")));

		AmazonS3 s3 = new AmazonS3Client(new BasicAWSCredentials(
				connectionProperties.get("AWSAccessKeyId"),
				connectionProperties.get("AWSSecretKey")));

		ServletOutputStream out = null;

		try {

			if (resp != null) {
				out = resp.getOutputStream();
			}

			for (String tableName : csvTables.keySet()) {

				// every table is converted to a domain with a given domain
				// prefix (aka database name)
				String domainName = connectionProperties.get("DomainPrefix")
						+ tableName;
				sdb.createDomain(new CreateDomainRequest(domainName));
				if (out != null) {
					out.println("Created domain '" + domainName
							+ "' for former table '" + tableName + "'.");
					out.flush();
				}

				String tableAsCSV = csvTables.get(tableName);
				ArrayList<String> headingsPerTable = new ArrayList<String>();

				// parse CSV file, assume first line contains headings
				int lineNumber = 0;
				Reader tableAsCSVReader = new StringReader(tableAsCSV);
				for (CSVRecord line : CSVFormat.MYSQL.parse(tableAsCSVReader)) {

					List<ReplaceableAttribute> attributesPerLine = new ArrayList<ReplaceableAttribute>();

					int fieldNumber = 0;

					for (String fieldOfLine : line) {
						if (lineNumber == 0) {
							headingsPerTable.add(fieldOfLine.replace("\"", ""));
						} else {
							if (fieldOfLine.length() > 1024) {
								// upload content to S3 and put a link into the
								// field instead of the real content
								s3.createBucket(domainName.toLowerCase());
								String uuid = UUID.randomUUID().toString();
								ByteArrayInputStream in = new ByteArrayInputStream(
										fieldOfLine.getBytes("UTF-8"));
								ObjectMetadata metaData = new ObjectMetadata();
								s3.putObject(domainName.toLowerCase(), uuid,
										in, metaData);
								fieldOfLine = "s3://" + domainName + "/" + uuid;
							}
							attributesPerLine.add(new ReplaceableAttribute(
									headingsPerTable.get(fieldNumber),
									fieldOfLine.replace("\"", ""), false));
						}
						fieldNumber++;
					}

					// assumes first row is the primary key (first line contains
					// only keys)
					if (lineNumber != 0) {
						sdb.putAttributes(new PutAttributesRequest(domainName,
								attributesPerLine.get(0).getValue(),
								attributesPerLine));
					}

					if (out != null) {
						out.println("Added data to domain '" + domainName
								+ "': '" + attributesPerLine + "'.");
						out.flush();
					}

					lineNumber++;
				}
			}
			if (out != null) {
				out.println("Data import to Amazon SimpleDB was successful.");
				out.flush();
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			if (resp != null) {
				try {
					out = resp.getOutputStream();
					out.println(e.getLocalizedMessage());
					out.println(Arrays.toString(e.getStackTrace()));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String getId() {
		return "AWS_SIMPLEDB_TARGET";
	}

	@Override
	public String getInstructions() {
		return "In order to migrate from a mysql database to Amazon SimpleDB "
				+ "you need to setup a SimpleDB instance and provide a AWS Access Key Id, AWS Secret Key and optional a domain prefix that could be the name of the existing database. "
				+ "Make sure that our servers (IP address block: 173.194.0.0/16) have temporary access to your SimpleDB instance. "
				+ "Note: as the domain name the domain prefix + table name is used and for the item name the first value of the first column of every row is used (as it is assumed that this is the primary key). "
				+ "If the item size exceeds the SimpleDB limits the import will fail. For more information see tbd.";
	}
}
