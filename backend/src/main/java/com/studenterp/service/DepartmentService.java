package com.studenterp.service;

import com.studenterp.entity.Department;
import com.studenterp.exception.ResourceNotFoundException;
import com.studenterp.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public Department findById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", id));
    }

    public Department create(Department department) {
        if (departmentRepository.existsByCode(department.getCode())) {
            throw new IllegalArgumentException("Department code already exists");
        }
        return departmentRepository.save(department);
    }

    public Department update(Long id, Department updated) {
        Department department = findById(id);
        department.setCode(updated.getCode());
        department.setName(updated.getName());
        department.setDescription(updated.getDescription());
        department.setActive(updated.getActive());
        return departmentRepository.save(department);
    }

    public void delete(Long id) {
        Department department = findById(id);
        departmentRepository.delete(department);
    }
}
