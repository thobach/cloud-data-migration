package com.clouddatamigration.migration.targetadapter;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.clouddatamigration.migration.model.TargetSystem;

public class AwsEc2TargetSystem implements TargetSystem {

	@Override
	public String getId() {
		return "AWS_EC2_IMPORT_TARGET";
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
		return "In order to import an existing virtual machine image follow the "
				+ "<a href=\"http://aws.amazon.com/ec2/vmimport/\">instructions provided by Amazon Web Services</a>. "
				+ "In a nutshell you need to download the 'VM Import Command Line Tools', "
				+ "import the VMDK, VHD or RAW file via the ec2-import-instance API, "
				+ "retrieve the Amazon EC2 instance id, launch the image from S3 (instance store-backed) and "
				+ "persist it's state to EBS and delete the image file from S3.";
	}
}
