package com.clouddatamigration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.clouddatamigration.migration.model.MigrationService;
import com.clouddatamigration.migration.sourceadapter.MysqlLocalSourceSystem;
import com.clouddatamigration.migration.sourceadapter.VirtualMachineImageSourceSystem;
import com.clouddatamigration.migration.targetadapter.AwsEc2TargetSystem;
import com.clouddatamigration.migration.targetadapter.AwsRdsMysqlTargetSystem;
import com.clouddatamigration.migration.targetadapter.AwsRdsOracleTargetSystem;
import com.clouddatamigration.migration.targetadapter.AwsRdsSqlServerTargetSystem;
import com.clouddatamigration.migration.targetadapter.AwsSimpleDbTargetSystem;
import com.clouddatamigration.migration.targetadapter.GoogleAppEngineDataStoreTargetSystem;
import com.clouddatamigration.migration.targetadapter.GoogleCloudSQLTargetSystem;
import com.clouddatamigration.migration.targetadapter.MysqlLocalTargetSystem;

public class CloudDataMigrationContextListener implements
		ServletContextListener {

	public static final String MIGRATION_SERVICE = "MigrationService";

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// ignore
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

		MigrationService migrationService = new MigrationService();
		migrationService.registerSource(new MysqlLocalSourceSystem());
		migrationService.registerSource(new VirtualMachineImageSourceSystem());
		migrationService.registerTarget(new MysqlLocalTargetSystem());
		migrationService.registerTarget(new GoogleCloudSQLTargetSystem());
		migrationService.registerTarget(new AwsRdsMysqlTargetSystem());
		migrationService.registerTarget(new AwsRdsOracleTargetSystem());
		migrationService.registerTarget(new AwsRdsSqlServerTargetSystem());
		migrationService.registerTarget(new AwsSimpleDbTargetSystem());
		migrationService.registerTarget(new AwsEc2TargetSystem());
		migrationService.registerTarget(new GoogleAppEngineDataStoreTargetSystem());

		servletContextEvent.getServletContext().setAttribute(MIGRATION_SERVICE,
				migrationService);
	}

}
