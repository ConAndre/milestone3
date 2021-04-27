package tree;

import java.util.ArrayList;
import java.util.List;

public class OrgChartImpl implements OrgChart{

	//Employee is your generic 'E'..
	private List<GenericTreeNode<Employee>> nodes = new ArrayList<>();


	@Override
	public void addRoot(Employee e) {

	}

	@Override
	public void clear() {

	}

	@Override
	public void addDirectReport(Employee manager, Employee newPerson) {

	}

	@Override
	public void removeEmployee(Employee firedPerson) {

	}

	@Override
	public void showOrgChartDepthFirst() {

	}

	@Override
	public void showOrgChartBreadthFirst() {

	}
}
