package ejercicios;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import tablas.Departamentos;
import tablas.Empleados;
import utils.HibernateUtils;

public class LanzaHibernate {

	private static Session sesion;

	public static void main(String[] args) {

		// Abrimos la sesion con hibernate
		sesion = HibernateUtils.getSessionFactory().openSession();

		// llamada al ejercicio 1
		consultaEmpleados(sesion);

		// llamada al ejercicio 2
		consultaEmpleadosConComision(sesion);

		// llamada al ejercicio 3
		cambiarCiudadDpto(sesion);

		// llamada al ejercicio 4
		nuevoDpto(sesion, (byte) 100, "NUEVO DPTO", "VALENCIA");

	}

	public static void consultaEmpleados(Session sesion) {
		Query consulta = sesion.createQuery("from Empleados");
		List lista = consulta.list();
		Iterator it = lista.iterator();

		System.out.println("DATOS DE TODOS LOS EMPLEADOS");
		while (it.hasNext()) {
			Empleados empleado = (Empleados) it.next();
			System.out.println("NOMBRE: " + empleado.getApellido() + " - OFICIO: " + empleado.getOficio()
					+ " - 	Nº DIRECTOR: " + empleado.getDir() + " - FECHA ALTA: " + empleado.getFechaAlt()
					+ " - SALARIO: " + empleado.getSalario() + "€" + " - COMISION: " + empleado.getComision()
					+ "€ - DEPARTAMENTO: " + empleado.getDepartamentos().getDnombre());
		}
		sesion.close();
		System.exit(0);
	}

	public static void consultaEmpleadosConComision(Session sesion) {
		Query consulta = sesion.createQuery("from Empleados");
		List lista = consulta.list();
		Iterator it = lista.iterator();

		System.out.println("DATOS DE TODOS LOS EMPLEADOS CON COMISION");
		while (it.hasNext()) {
			Empleados empleado = (Empleados) it.next();
			if (empleado.getComision() != null && empleado.getComision() != 0) {
				System.out.println("NOMBRE: " + empleado.getApellido() + " - OFICIO: " + empleado.getOficio()
						+ " - 	Nº DIRECTOR: " + empleado.getDir() + " - FECHA ALTA: " + empleado.getFechaAlt()
						+ " - SALARIO: " + empleado.getSalario() + "€" + " - COMISION: " + empleado.getComision()
						+ "€ - DEPARTAMENTO: " + empleado.getDepartamentos().getDnombre());
			}

		}
		sesion.close();
		System.exit(0);
	}

	public static void cambiarCiudadDpto(Session sesion) {
		// Creación de la transacción
		Transaction tx = sesion.beginTransaction();

		// Consulta para modificar
		String hqlModificacion = "update Departamentos d set d.loc = :nuevaLoc where d.deptNo = :dpto";
		Query consulta1 = sesion.createQuery(hqlModificacion);
		// Seteado de parametros para la consulta1
		consulta1.setParameter("nuevaLoc", "PARIS");
		consulta1.setParameter("dpto", (byte) 10);
		// Ejecución de la consulta, devuelve filas afectadas
		int filasMod = consulta1.executeUpdate();
		System.out.println("Filas modificadas: " + filasMod);

		// Validación de la transaccion
		tx.commit();

		// Cerrado de recursos
		sesion.close();
		System.exit(0);
	}

	private static void nuevoDpto(Session sesion2, byte noDpto, String nombreDpto, String localizacion) {
		// Nuevo departamento con constructor por defecto
		Departamentos nuevoDpto = new Departamentos();
		// Transaccion
		Transaction tx = sesion.beginTransaction();

		// Seteado de los parametros al Departamento
		nuevoDpto.setDeptNo(noDpto);
		nuevoDpto.setDnombre(nombreDpto);
		nuevoDpto.setLoc(localizacion);

		// Almacenamiento del objeto Departamentos
		sesion.save(nuevoDpto);
		// Validación de la transaccion
		tx.commit();
		System.out.println("Nuevo dpto creado...");
	}

}
