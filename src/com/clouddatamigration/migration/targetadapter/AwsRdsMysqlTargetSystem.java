package com.clouddatamigration.migration.targetadapter;

import com.clouddatamigration.migration.model.TargetSystem;

public class AwsRdsMysqlTargetSystem extends MysqlLocalTargetSystem implements
		TargetSystem {

	@Override
	public String getId() {
		return "AWS_RDS_MYSQL_TARGET";
	}

	@Override
	public String getInstructions() {
		return "In order to migrate from a mysql database to Amazon Relational Database Service (MySQL engine) "
				+ "you need to setup a RDS MySQL instance and provide a username and password, the host name (e.g. db-instance-name.servername.region.rds.amazonaws.com), "
				+ "the port (default port is 3306) and the name of the database to migrate to. "
				+ "Make sure that our servers (IP address block: 173.194.0.0/16) have temporary access to your mysql server. "
				+ "For more information see <a href=\"http://aws.amazon.com/articles/2933?_encoding=UTF8&jiveRedirect=1\">&quot;Amazon RDS Customer Data Import Guide for MySQL&quot;</a>";
	}
}
