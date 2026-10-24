package st.rh.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import st.rh.modelo.Empleado;

public interface EmpleadoRepositorio extends JpaRepository<Empleado, Integer> {
    
}
