package st.rh.servicio;

import java.util.List;

import st.rh.modelo.Empleado;

public interface IEmpleadoServicio {
    public List<Empleado> listarEmpleados();
    public Empleado buscarEmpleadoPorId(Integer idEmpleado);
    public Empleado guardarEmpleado(Empleado empleado);
    public void eliminarEmpleado(Empleado empleado);
}
