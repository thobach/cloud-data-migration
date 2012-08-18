package com.clouddatamigration.migration.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class MigrationService {

	private HashMap<String, SourceSystem> sources = new HashMap<String, SourceSystem>();
	private HashMap<String, TargetSystem> targets = new HashMap<String, TargetSystem>();
	private HashMap<String, String> sqlCommands = new HashMap<String, String>();
	private HashMap<String, HashMap<String, String>> csvTables = new HashMap<String, HashMap<String, String>>();

	public void registerSource(SourceSystem sourceSystem) {
		sources.put(sourceSystem.getId(), sourceSystem);
	}

	public void registerTarget(TargetSystem targetSystem) {
		targets.put(targetSystem.getId(), targetSystem);
	}

	public Set<String> getSourceIds() {
		return sources.keySet();
	}

	public Set<String> getTargetIds() {
		return targets.keySet();
	}

	public void exportData(String sessionId, String sourceSystemId,
			HashMap<String, String> connectionPropertiesSource,
			HttpServletResponse resp) {
		try {
			ServletOutputStream out = resp.getOutputStream();

			if (sessionId == null || sessionId.isEmpty()) {
				out.println("WARNING: Session timed out Please login again.");
				out.flush();
				return;
			}

			SourceSystem source = sources.get(sourceSystemId);
			if (source != null
					&& source.connect(connectionPropertiesSource, out)) {
				sqlCommands.put(sessionId, source.getSqlCommands(out));
				csvTables.put(sessionId, source.getTablesAsCSV(out));
			} else {
				out.println("WARNING: Could not connect to source database.");
				out.flush();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void importData(String sessionId, String targetSystemId,
			HashMap<String, String> connectionPropertiesTarget,
			HttpServletResponse resp) {
		try {
			ServletOutputStream out = resp.getOutputStream();
			TargetSystem target = targets.get(targetSystemId);
			if (target != null
					&& target.connect(connectionPropertiesTarget, out)) {
				if (target.supportsSql()) {
					if (!target.migrate(sqlCommands.get(sessionId), resp)) {
						try {
							out.println("WARNING: Migration using SQL was not successful.");
							out.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} else {
					if (!target.migrate(csvTables.get(sessionId), resp)) {
						try {
							out.println("WARNING: Migration using CSV format was not successful.");
							out.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				try {
					out.println("WARNING: Could not connect to target database.");
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public ArrayList<String> getSourceConnectionParams(String sourceSystemId) {
		if (sources.containsKey(sourceSystemId)) {
			return sources.get(sourceSystemId).getConnectionParameters();
		} else {
			return new ArrayList<String>();
		}
	}

	public ArrayList<String> getTargetConnectionParams(String targetSystemId) {
		if (targets.containsKey(targetSystemId)) {
			return targets.get(targetSystemId).getConnectionParameters();
		} else {
			return new ArrayList<String>();
		}
	}

	public String getSourceInstructions(String sourceSystemId) {
		return sources.get(sourceSystemId).getInstructions();
	}

	public String getTargetInstructions(String targetSystemId) {
		return targets.get(targetSystemId).getInstructions();
	}

}
