package com.qlnsitsol.demo.serviceIplm;


import com.qlnsitsol.demo.Service.DiemDanhService;
import com.qlnsitsol.demo.entity.DiemDanh;
import com.qlnsitsol.demo.repository.RepositoryDiemDanh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiemDanhImpl implements DiemDanhService {
    @Autowired
    RepositoryDiemDanh repositoryDiemDanh;
    @Override
    public void save(DiemDanh dd) {
        repositoryDiemDanh.save(dd);
    }
}
