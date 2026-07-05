package com.epas.config;

import com.epas.entity.*;
import com.epas.repository.DepartmentRepository;
import com.epas.repository.PermissionRepository;
import com.epas.repository.RolePermissionRepository;
import com.epas.repository.RoleRepository;
import com.epas.repository.UserRepository;
import com.epas.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    // Inject dependencies here
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RolePermissionRepository rolePermissionRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        initializeDepartments();
        initializeRoles();
        initializePermissions();
        initializeAdminUser();
        assignAdminRole();
        assignPermissionsToAdminRole();
    }

    private void initializeDepartments() {

        if (departmentRepository.count() > 0) {
            return;
        }

        Department it = Department.builder()
                .name("IT")
                .code("IT")
                .description("Information Technology Department")
                .build();

        Department finance = Department.builder()
                .name("Finance")
                .code("FIN")
                .description("Finance Department")
                .build();

        Department hr = Department.builder()
                .name("Human Resources")
                .code("HR")
                .description("Human Resources Department")
                .build();

        Department procurement = Department.builder()
                .name("Procurement")
                .code("PROC")
                .description("Procurement Department")
                .build();

        Department warehouse = Department.builder()
                .name("Warehouse")
                .code("WH")
                .description("Warehouse Department")
                .build();

        departmentRepository.saveAll(List.of(
                it,
                finance,
                hr,
                procurement,
                warehouse
        ));

        System.out.println("✅ Default departments created.");
    }

    private void initializeRoles() {

        if (roleRepository.count() > 0) {
            return;
        }

        Role admin = Role.builder()
                .name("Administrator")
                .code("ADMIN")
                .description("System Administrator")
                .priority(1)
                .systemRole(true)
                .build();

        Role manager = Role.builder()
                .name("Manager")
                .code("MANAGER")
                .description("Department Manager")
                .priority(2)
                .systemRole(true)
                .build();

        Role employee = Role.builder()
                .name("Employee")
                .code("EMPLOYEE")
                .description("Organization Employee")
                .priority(3)
                .systemRole(true)
                .build();

        Role procurement = Role.builder()
                .name("Procurement Officer")
                .code("PROCUREMENT")
                .description("Procurement Team")
                .priority(4)
                .systemRole(true)
                .build();

        Role finance = Role.builder()
                .name("Finance Officer")
                .code("FINANCE")
                .description("Finance Team")
                .priority(5)
                .systemRole(true)
                .build();

        roleRepository.saveAll(List.of(
                admin,
                manager,
                employee,
                procurement,
                finance
        ));

        System.out.println("✅ Default roles created.");
    }

    private void initializePermissions() {

        if (permissionRepository.count() > 0) {
            return;
        }

        List<Permission> permissions = List.of(

                // =========================
                // USER
                // =========================
                createPermission("View Users", "USER_VIEW", "View users"),
                createPermission("Create User", "USER_CREATE", "Create users"),
                createPermission("Update User", "USER_UPDATE", "Update users"),
                createPermission("Delete User", "USER_DELETE", "Delete users"),

                // =========================
                // ROLE
                // =========================
                createPermission("View Roles", "ROLE_VIEW", "View roles"),
                createPermission("Create Role", "ROLE_CREATE", "Create roles"),
                createPermission("Update Role", "ROLE_UPDATE", "Update roles"),
                createPermission("Delete Role", "ROLE_DELETE", "Delete roles"),

                // =========================
                // PERMISSION
                // =========================
                createPermission("View Permissions", "PERMISSION_VIEW", "View permissions"),
                createPermission("Create Permission", "PERMISSION_CREATE", "Create permissions"),
                createPermission("Update Permission", "PERMISSION_UPDATE", "Update permissions"),
                createPermission("Delete Permission", "PERMISSION_DELETE", "Delete permissions"),

                // =========================
                // DEPARTMENT
                // =========================
                createPermission("View Departments", "DEPARTMENT_VIEW", "View departments"),
                createPermission("Create Department", "DEPARTMENT_CREATE", "Create departments"),
                createPermission("Update Department", "DEPARTMENT_UPDATE", "Update departments"),
                createPermission("Delete Department", "DEPARTMENT_DELETE", "Delete departments")
        );

        permissionRepository.saveAll(permissions);

        System.out.println("✅ Default permissions created.");
    }

    private Permission createPermission(String name,
                                        String code,
                                        String description) {

        return Permission.builder()
                .name(name)
                .code(code)
                .description(description)
                .build();
    }

    private void initializeAdminUser() {

        if (userRepository.count() > 0) {
            return;
        }

        Department department = departmentRepository
                .findByCode("IT")
                .orElseThrow(() -> new RuntimeException("IT Department not found"));

        User admin = User.builder()
                .employeeCode("EMP0001")
                .firstName("System")
                .lastName("Administrator")
                .email("admin@epas.com")
                .mobile("9999999999")
                .password(passwordEncoder.encode("Admin@123"))
                .department(department)
                .designation("System Administrator")
                .enabled(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .failedLoginAttempts(0)
                .build();

        userRepository.save(admin);

        System.out.println("✅ Default admin user created.");
    }

    private void assignAdminRole() {

        if (userRoleRepository.count() > 0) {
            return;
        }

        User adminUser = userRepository.findByEmail("admin@epas.com")
                .orElseThrow(() -> new RuntimeException("Admin user not found."));

        Role adminRole = roleRepository.findByCode("ADMIN")
                .orElseThrow(() -> new RuntimeException("ADMIN role not found."));

        UserRole userRole = UserRole.builder()
                .user(adminUser)
                .role(adminRole)
                .build();

        userRoleRepository.save(userRole);

        System.out.println("✅ ADMIN role assigned to admin user.");
    }

    private void assignPermissionsToAdminRole() {

        if (rolePermissionRepository.count() > 0) {
            return;
        }

        Role adminRole = roleRepository.findByCode("ADMIN")
                .orElseThrow(() -> new RuntimeException("ADMIN role not found"));

        List<Permission> permissions = permissionRepository.findAll();

        List<RolePermission> rolePermissions = new ArrayList<>();

        for (Permission permission : permissions) {

            RolePermission rolePermission = RolePermission.builder()
                    .role(adminRole)
                    .permission(permission)
                    .build();

            rolePermissions.add(rolePermission);
        }

        rolePermissionRepository.saveAll(rolePermissions);

        System.out.println("✅ All permissions assigned to ADMIN role.");
    }
}