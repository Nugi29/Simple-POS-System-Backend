package edu.nugi.service.impl;

import edu.nugi.dto.Admin;
import edu.nugi.entity.AdminEntity;
import edu.nugi.repository.AdminRepository;
import edu.nugi.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    final AdminRepository repository;
    final ModelMapper mapper;

    @Override
    public List<Admin> getAll() {
        ArrayList<Admin> adminList = new ArrayList<>();
        List<AdminEntity> all = repository.findAll();

        all.forEach(adminEntity -> {
            adminList.add(mapper.map(adminEntity, Admin.class));
        });
        return adminList;
    }

    @Override
    public void addAdmin(Admin admin) {
        repository.save(mapper.map(admin, AdminEntity.class));
    }

    @Override
    public void updateAdmin(Admin admin) {
        repository.save(mapper.map(admin, AdminEntity.class));
    }

    @Override
    public void deleteAdmin(Integer id) {
        repository.deleteById(id);
    }

}
