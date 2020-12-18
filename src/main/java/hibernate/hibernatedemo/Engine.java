package hibernate.hibernatedemo;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;

import hibernate.entities.Employee;
import hibernate.entities.Town;

public class Engine implements Runnable {

	private final EntityManager entityManager;

	public Engine(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void run() {

		over5000();
		ExtractResAndDev();
		// remove();
		// containsEmployee();
	}

	private void containsEmployee() {
		Scanner scanner = new Scanner(System.in);
		String name = scanner.nextLine();
		try {
			this.entityManager.getTransaction().begin();
			Employee emp = (Employee) this.entityManager
					.createQuery("FROM Employee Where concat(first_name,' ',last_name) = :name")
					.setParameter("name", name).getSingleResult();
			this.entityManager.getTransaction().commit();
			System.out.println("Yes");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("No");
		}
	}

	private void ExtractResAndDev() {
		this.entityManager.getTransaction().begin();
		List<Employee> emp = (List<Employee>) this.entityManager
				.createQuery("FROM Employee Where job_title = :name ORDER BY salary,id")
				.setParameter("name", "Research and Development Manager").getResultList();
		for (Employee employee : emp) {
			System.out.println(employee.getFirstName());
			System.out.println(employee.getLastName());
			System.out.println(employee.getDepartment().getName());
			System.out.println(employee.getSalary());
		}
		this.entityManager.getTransaction().commit();
	}

	private void over5000() {
		this.entityManager.getTransaction().begin();
		List<String> emp = (List<String>) this.entityManager
				.createQuery("SELECT firstName FROM Employee  Where salary>50000").getResultList();
		for (String employee : emp) {
			System.out.println(employee);

		}
		this.entityManager.getTransaction().commit();

	}

	private void remove() {
		this.entityManager.getTransaction().begin();
		List<Town> townList = (List<Town>) this.entityManager.createQuery("FROM Town ").getResultList();
		System.out.println(townList.size());
		for (Town town : townList) {
			if (town.getName().length() <= 5) {
				this.entityManager.detach(town);
				System.out.println("detaching..");
				System.out.println(townList.size());
			}
		}
		for (Town town : townList) {
			town.setName(CapitalName(town.getName()));
		}
		this.entityManager.getTransaction().commit();
	}

	private String CapitalName(String name) {
		String cap = name.substring(0, 1).toUpperCase() + name.substring(1);

		return cap;
	}

	private void CreateAddressUpdateEmp() {

	}

}
