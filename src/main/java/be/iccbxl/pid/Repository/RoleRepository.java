package be.iccbxl.pid.Repository;

import org.springframework.data.repository.CrudRepository;

import be.iccbxl.pid.Model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	Role findByRole (String role);

}
