package com.clouddatamigration.migration.sourceadapter;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;

import com.clouddatamigration.migration.model.SourceSystem;

public class VirtualMachineImageSourceSystem implements SourceSystem {

	@Override
	public String getId() {
		return "VIRTUAL_MACHINE_IMAGE_SOURCE";
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
	public HashMap<String, String> getTablesAsCSV(ServletOutputStream out) {
		return null;
	}

	@Override
	public String getSqlCommands(ServletOutputStream out) {
		return null;
	}

	@Override
	public String getInstructions() {
		return "In order to migrate an existing virtual machine image you need to follow the import instructions from the target system provider.";
	}

}
