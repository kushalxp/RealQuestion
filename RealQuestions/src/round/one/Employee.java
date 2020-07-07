package round.one;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Employee {

	static HashMap<String, String> employeeManagerMap = new HashMap<String, String>();
	static HashMap<String, String> managerEmployeeMap = new HashMap<String, String>();
	static HashMap<String, String> employeeIdNameMap = new HashMap<String, String>();
	List<String> hardLineManagersId = new ArrayList<String>();
	List<String> reportingManagersNames = new ArrayList<String>();
	List<String> subOrdinatesId = new ArrayList<String>();
	List<String> subOrdinatesNames = new ArrayList<String>();

	public static void main(String[] args) throws IOException {

		Employee obj = new Employee();
		obj.readFile();
		System.out.println(
				obj.searchReportingManagers(employeeManagerMap, employeeIdNameMap, obj.hardLineManagersId, "7872"));
		System.out.println(obj.searchSubOrdinates(managerEmployeeMap, employeeIdNameMap, "7878"));
	}

	private List<String> searchReportingManagers(HashMap<String, String> employeeManagerIdMap,
			HashMap<String, String> employeeIdNameMap, List<String> hardLineManagers, String empId) {

		if (employeeManagerIdMap.containsKey(empId)) {
			hardLineManagers.add(employeeManagerIdMap.get(empId));
			empId = employeeManagerIdMap.get(empId);
			searchReportingManagers(employeeManagerIdMap, employeeIdNameMap, hardLineManagers, empId);
		} else {
			for (String employeeId : hardLineManagers) {
				reportingManagersNames.add(employeeIdNameMap.get(employeeId));
			}
			reportingManagersNames.remove(reportingManagersNames.size() - 1);
		}

		return reportingManagersNames;

	}

	private List<String> searchSubOrdinates(HashMap<String, String> managerEmployeeMap,
			HashMap<String, String> employeeIdNameMap, String ManagerId) {
		if (managerEmployeeMap.containsKey(ManagerId)) {
			subOrdinatesId.add(managerEmployeeMap.get(ManagerId));
			ManagerId = managerEmployeeMap.get(ManagerId);
			searchSubOrdinates(managerEmployeeMap, employeeIdNameMap, ManagerId);
		} else {
			for (String employeeId : subOrdinatesId) {
				subOrdinatesNames.add(employeeIdNameMap.get(employeeId));
			}
			subOrdinatesNames.remove(subOrdinatesNames.size() - 1);
		}
		return subOrdinatesNames;

	}

	private void readFile() throws FileNotFoundException, IOException {
		String line = "";
		try (BufferedReader br = new BufferedReader(new FileReader("/EmployeeData.csv"))) {
			while ((line = br.readLine()) != null) {
				String[] row = line.split(",");
				employeeManagerMap.put(row[2], row[3]);
				managerEmployeeMap.put(row[3], row[2]);
				employeeIdNameMap.put(row[2], row[1]);

			}
		}

	}
}
