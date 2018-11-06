package humanity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	public static Scanner sc = new Scanner(System.in);
	public static Scanner sct = new Scanner(System.in);

	public static void main(String[] args) {
		Connection conn;
		Statement stmt;
		String dbURL = "jdbc:mysql://localhost:3306/project_humanity";
		String user = "root";
		String password = "redmage";
		try {
			conn = DriverManager.getConnection(dbURL, user, password);
			stmt = conn.createStatement();

			boolean flag = true;
			while (flag) {
				flag = mainMenu(stmt);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static boolean mainMenu(Statement stmt) {
		int i = 0;

		System.out.println("Welcome to Project Humanity!");
		System.out.println("Press [1] for Students.");
		System.out.println("Press [2] for Workers.");
		System.out.println("Press anything else to exit.");

		try {
			i = sc.nextInt();
		} catch (InputMismatchException e) {
			return false;
		}

		switch (i) {
		case 1:
			return studentMenu(stmt);

		case 2:
			return workerMenu(stmt);

		default:
			return false;

		}

	}

	public static boolean studentMenu(Statement stmt) {

		int i = 0;
		String q1 = "SELECT COUNT(student_id) AS total FROM `student`; ";
		try {
			ResultSet r1 = stmt.executeQuery(q1);
			while (r1.next()) {
				int total = r1.getInt("total");
				System.out.println("\nThere are " + total + " students in the project!");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage() + "\n");
		}
		System.out.println("Press [1] to view the students");
		System.out.println("Press [2] to insert a student to the database");
		System.out.println("Press [3] to update a student's info");
		System.out.println("Press [4] to delete a student");
		System.out.println("Press anything else to go back.");

		try {
			i = sc.nextInt();
		} catch (InputMismatchException e) {
			return true;
		}

		switch (i) {

		case 1:

			String q2 = "SELECT student_id, last_name, first_name, faculty_num FROM `student` "
					+ "ORDER BY student_id ASC; ";
			try {
				ResultSet r2 = stmt.executeQuery(q2);
				while (r2.next()) {
					int j = r2.getInt("student_id");
					String lastName = r2.getString("last_name");
					String firstName = r2.getString("first_name");
					String faculty = r2.getString("faculty_num");
					System.out.println(j + ". Last Name: {" + lastName + "}, First Name: {" + firstName
							+ "}, Faculty Number: {" + faculty + "}");

				}
			} catch (SQLException e) {
				System.out.println(e.getMessage() + "\n");
			}
			System.out.println();
			break;

		case 2:
			System.out.println("Type the student's first name (more than 2 alphabetic characters)");
			String temp = sct.next();
			String firstName = temp.substring(0, 1).toUpperCase() + temp.substring(1);
			System.out.println("Type the student's last name (more than 3 alphabetic characters)");
			String temp2 = sct.next();
			String lastName = temp2.substring(0, 1).toUpperCase() + temp2.substring(1);
			System.out.println("Type the student's faculty number (between 5 and 10 characters)");
			String faculty = sct.next();
			String q3 = "INSERT INTO `student`(first_name, last_name, faculty_num) " + "VALUES('" + firstName + "', '"
					+ lastName + "', '" + faculty + "'); ";
			try {
				stmt.executeUpdate(q3);
				System.out.println("Student was inserted successfully\n");
			} catch (SQLException e) {
				System.out.println(e.getMessage() + "\n");
			}
			break;

		case 3:
			System.out.println("Type the student's index that you wish to update as shown on the list or 0 to go back");
			int id = sc.nextInt();
			if (id > 0) {
				System.out.println("Type the student's new first name (more than 2 alphabetic characters)");
				temp = sct.next();
				String newFirstName = temp.substring(0, 1).toUpperCase() + temp.substring(1);
				System.out.println("Type the student's new last name (more than 3 alphabetic characters)");
				temp2 = sct.next();
				String newLastName = temp2.substring(0, 1).toUpperCase() + temp2.substring(1);
				System.out.println("Type the student's new faculty number (between 5 and 10 characters)");
				String newFaculty = sct.next();
				String q4 = "UPDATE `student` " + "SET first_name = '" + newFirstName + "', " + "last_name = '"
						+ newLastName + "', " + "faculty_num = '" + newFaculty + "' " + "WHERE student_id = " + id
						+ "; ";
				try {
					stmt.executeUpdate(q4);
					System.out.println("Student was updated successfully\n");
				} catch (SQLException e) {
					System.out.println(e.getMessage() + "\n");
				}
			}
			break;

		case 4:
			System.out.println("Type the student's index that you wish to delete as shown on the list or 0 to go back");
			int id2 = sc.nextInt();
			if (id2 > 0) {
				String q5 = "DELETE FROM `student` " + "WHERE student_id = " + id2 + "; ";
				try {
					stmt.executeUpdate(q5);
					System.out.println("Student was deleted successfully\n");
				} catch (SQLException e) {
					System.out.println(e.getMessage() + "\n");
				}
			}
			break;

		default:
			break;

		}
		return true;
	}

	public static boolean workerMenu(Statement stmt) {
		DecimalFormat df = new DecimalFormat("##.00");
		int i = 0;
		String q1 = "SELECT COUNT(worker_id) AS total FROM `worker`; ";
		try {
			ResultSet r1 = stmt.executeQuery(q1);
			while (r1.next()) {
				int total = r1.getInt("total");
				System.out.println("\nThere are " + total + " workers in the project!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Press [1] to view the workers");
		System.out.println("Press [2] to insert a worker to the database");
		System.out.println("Press [3] to update a worker's info");
		System.out.println("Press [4] to delete a worker");
		System.out.println("Press anything else to go back.");

		try {
			i = sc.nextInt();
		} catch (InputMismatchException e) {
			return true;
		}

		switch (i) {

		case 1:

			String q2 = "SELECT worker_id, last_name, first_name, week_salary, hours_per_day FROM `worker` "
					+ "ORDER BY worker_id ASC; ";
			try {
				ResultSet r2 = stmt.executeQuery(q2);
				while (r2.next()) {
					int j = r2.getInt("worker_id");
					String lastName = r2.getString("last_name");
					String firstName = r2.getString("first_name");
					double salary = r2.getDouble("week_salary");
					int hours = r2.getInt("hours_per_day");
					double sh = salary / (5 * hours);
					System.out.println(j + ". Last Name: {" + lastName + "}, First Name: {" + firstName + "},"
							+ " Weekly Salary: {" + df.format(salary) + "}, Hours per day: {" + hours
							+ "}, Salary per hour: {" + df.format(sh) + "}");

				}
			} catch (SQLException e) {
				System.out.println(e.getMessage() + "\n");
			}
			System.out.println();
			break;

		case 2:
			System.out.println("Type the worker's first name (more than 2 alphabetic characters)");
			String temp = sct.next();
			String firstName = temp.substring(0, 1).toUpperCase() + temp.substring(1);
			System.out.println("Type the worker's last name (more than 3 alphabetic characters)");
			String temp2 = sct.next();
			String lastName = temp2.substring(0, 1).toUpperCase() + temp2.substring(1);
			System.out.println("Type the worker's weekly salary (more than 10.00)");
			double salary = sc.nextDouble();
			System.out.println("Type the worker's hours per day (between 1 and 12)");
			int hours = sc.nextInt();
			String q3 = "INSERT INTO `worker`(first_name, last_name, week_salary, hours_per_day) " + "VALUES('"
					+ firstName + "', '" + lastName + "', '" + salary + "', '" + hours + "'); ";
			try {
				stmt.executeUpdate(q3);
				System.out.println("Worker was inserted successfully\n");
			} catch (SQLException e) {
				System.out.println(e.getMessage() + "\n");
			}
			break;

		case 3:
			System.out.println("Type the worker's index that you wish to update as shown on the list or 0 to go back");
			int id = sc.nextInt();
			if (id > 0) {
				System.out.println("Type the worker's new first name (more than 2 alphabetic characters)");
				temp = sct.next();
				String newFirstName = temp.substring(0, 1).toUpperCase() + temp.substring(1);
				System.out.println("Type the worker's new last name (more than 3 alphabetic characters)");
				temp2 = sct.next();
				String newLastName = temp2.substring(0, 1).toUpperCase() + temp2.substring(1);
				System.out.println("Type the worker's new weekly salary (more than 10.00)");
				double newSalary = sc.nextDouble();
				System.out.println("Type the worker's new hours per day (between 1 and 12)");
				int newHours = sc.nextInt();
				String q4 = "UPDATE `worker` " + "SET first_name = '" + newFirstName + "', " + "last_name = '"
						+ newLastName + "', " + "week_salary = " + newSalary + ", " + "hours_per_day = " + newHours
						+ " " + "WHERE worker_id = " + id + "; ";
				try {
					stmt.executeUpdate(q4);
					System.out.println("Worker was updated successfully\n");
				} catch (SQLException e) {
					System.out.println(e.getMessage() + "\n");
				}
			}
			break;

		case 4:
			System.out.println("Type the worker's index that you wish to delete as shown on the list or 0 to go back");
			int id2 = sc.nextInt();
			if (id2 > 0) {
				String q5 = "DELETE FROM `worker` " + "WHERE worker_id = " + id2 + "; ";
				try {
					stmt.executeUpdate(q5);
					System.out.println("Worker was deleted successfully\n");
				} catch (SQLException e) {
					System.out.println(e.getMessage() + "\n");
				}
			}
			break;

		default:
			break;

		}
		return true;
	}

}
