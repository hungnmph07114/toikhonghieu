package com.qlns.qlnsitsol.serviceIplm;

import com.qlns.qlnsitsol.Service.DiemDanhService;
import com.qlns.qlnsitsol.entity.DiemDanh;
import com.qlns.qlnsitsol.repository.RepositoryDiemDanh;
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
