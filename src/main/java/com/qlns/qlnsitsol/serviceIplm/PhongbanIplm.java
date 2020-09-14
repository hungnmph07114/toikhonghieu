package com.qlns.qlnsitsol.serviceIplm;

import com.qlns.qlnsitsol.Service.PhongBanService;
import com.qlns.qlnsitsol.entity.PhongBan;
import com.qlns.qlnsitsol.repository.RepositoryPhongBan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PhongbanIplm  implements PhongBanService {
    @Autowired
    RepositoryPhongBan repositoryPhongBan;
    @Override
    public List<PhongBan> list() {
        return repositoryPhongBan.findAll();
    }


    @Override
    public Optional<PhongBan> getOne(long id) {
        return repositoryPhongBan.findById(id);
    }

    @Override
    public Optional<PhongBan> getByName(String s) {
        return repositoryPhongBan.findByTenPhongBan(s);
    }

    @Override
    public void save(PhongBan phongBan) {
repositoryPhongBan.save(phongBan);
    }

    @Override
    public void delete(long id) {
        repositoryPhongBan.deleteById(id);

    }

    @Override
    public boolean existsById(long id) {
        return repositoryPhongBan.existsById(id);
    }

    @Override
    public boolean existsByName(String s) {
        return repositoryPhongBan.existsByTenPhongBan(s);
    }

    @Override
    public Page<PhongBan> page(Pageable pageable) {
        return repositoryPhongBan.findAll(pageable);
    }
}
