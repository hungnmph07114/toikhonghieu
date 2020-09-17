package com.qlns.qlnsitsol.Controller;

import com.qlns.qlnsitsol.DTO.*;
import com.qlns.qlnsitsol.Service.KhenThuongService;
import com.qlns.qlnsitsol.Service.LuongService;
import com.qlns.qlnsitsol.Service.NhanVienService;
import com.qlns.qlnsitsol.entity.*;
import com.qlns.qlnsitsol.messager.Messager;
import com.qlns.qlnsitsol.repository.*;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/luong")
@CrossOrigin(origins = "*")
public class ControllerLuong {
    @Autowired
    RepositoryLuong repositoryLuong;
    @Autowired
    KhenThuongService khenThuongService;
    @Autowired
    NhanVienService nhanVienService;
    @Autowired
    LuongService luongService;
    @Autowired
    RepositoryDiemDanh repositoryDiemDanh;
    @Autowired
    RepositoryKyluat repositoryKyluat;
    @Autowired
    RepositoryHeSoLuong repositoryHeSoLuong;
    @Autowired
    RepositoryPhuCap repositoryPhuCap;
    @Autowired
    RepositoryTamUng repositoryTamUng;
    @GetMapping("/bangluong")
    public ResponseEntity<?> luong(@RequestParam(defaultValue = "") String date){

        List<Luong> bangluong = repositoryLuong.getAllById_NhanVien_ActionAndId_Date(true,date);
        if (bangluong.isEmpty()){
            return  new ResponseEntity(new Message("Tháng này chưa có bảng lương"),HttpStatus.NOT_FOUND);
        }
        if (!repositoryLuong.existsById_Date(date)){
            return  new ResponseEntity(new Message("Tháng này chưa có bảng lương"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bangluong,HttpStatus.OK);
    }
    @PostMapping("/khenthuong")
    public ResponseEntity<?> addkhenthuong(@RequestBody KhenThuongDTO dto){
        System.out.println(dto.getDate());
        System.out.println(dto.getHinhthuc());
        System.out.println(dto.getSotien());
//
        LuongId id1 = new LuongId();
        NhanVien nv = nhanVienService.getOne(dto.getNhanvienid()).get();

        id1.setDate(dto.getDate());
        id1.setNhanVien(nv);

        LuongId luongId = new LuongId();
        luongId.setNhanVien(nv);
        luongId.setDate(dto.getDate());
        if(!repositoryLuong.existsById_NhanVien_IdAndId_Date(dto.getNhanvienid(),dto.getDate())){
            Luong newLuong = new Luong();
            newLuong.setId(luongId);
            luongService.save(newLuong);
        }
        Luong luong = repositoryLuong.getOne(luongId);
        KhenThuongKl khenThuongKl = new KhenThuongKl();
        khenThuongKl.setHinhThuc(dto.getHinhthuc());
        khenThuongKl.setNoiDung(dto.getNoidung());
        khenThuongKl.setSoTien(dto.getSotien());
        khenThuongService.save(khenThuongKl);
        luong.setKhenThuongKl(khenThuongKl);
        luongService.save(luong);

//
//
        return new ResponseEntity<>(khenThuongKl,HttpStatus.OK);
    }
    @PostMapping("/ngaycong")
    public ResponseEntity<?> addKyLuat(@RequestBody DiemDanhDTO dto){

        NhanVien nv = nhanVienService.getOne(dto.getNhanvienid()).get();
        DiemDanhId dd1 = new DiemDanhId();
        dd1.setDate(dto.getDate());
        dd1.setNhanVien(nv);

        LuongId luongId = new LuongId();
        luongId.setNhanVien(nv);
        luongId.setDate(dto.getDate());
        if(!repositoryLuong.existsById_NhanVien_IdAndId_Date(dto.getNhanvienid(),dto.getDate())){
            Luong newLuong = new Luong();
            newLuong.setId(luongId);
            luongService.save(newLuong);
        }
        Luong luong = repositoryLuong.getOne(luongId);
//        DiemDanh dd = luongService.getOne().get();

        String ngaycong = String.valueOf(repositoryDiemDanh.countByIdAndDilam(dd1,true).get());

        luong.setId(luongId);
        luong.setNgayCong(ngaycong);
        luongService.save(luong);
        return new ResponseEntity<>(luong,HttpStatus.OK);
    }

    @PostMapping("/kyluat")
    public ResponseEntity<?> addKyLuat(@RequestBody KyluatDTO dto){
        LuongId id1 = new LuongId();
        NhanVien nv = nhanVienService.getOne(dto.getNhanvienid()).get();
        KyLuat kyluat = new KyLuat();
//        System.out.println(dto.getNgayPhat());
//
//        SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
//        Date date = dto.getNgayPhat();
//        String dateFormat = formatter.format(date);

        id1.setDate(dto.getNgayPhat());
        id1.setNhanVien(nv);
        LuongId luongId = new LuongId();
        luongId.setNhanVien(nv);
        luongId.setDate(dto.getNgayPhat());
        if(!repositoryLuong.existsById_NhanVien_IdAndId_Date(dto.getNhanvienid(),dto.getDate())){
            Luong newLuong = new Luong();
            newLuong.setId(luongId);
            luongService.save(newLuong);
        }
        Luong luong = repositoryLuong.getOne(luongId);

        kyluat.setHinhthuckyluat(dto.getHinhthuckyluat());
        kyluat.setLyDo(dto.getLyDo());
        kyluat.setNgayPhat(dto.getNgayPhat());
        kyluat.setTienPhat(dto.getTienPhat());
        repositoryKyluat.save(kyluat);
        luong.setKyLuat(kyluat);
        luongService.save(luong);
        return new ResponseEntity<>(kyluat,HttpStatus.OK);
    }
    @PostMapping("/hesoluong")
    public ResponseEntity<?> addKyLuat(@RequestBody HeSoLuongDTO dto){
        LuongId id1 = new LuongId();
        System.out.println(dto.getNgayDieuChinhLuong());
        System.out.println(dto.getHeSoLuong());
        NhanVien nv = nhanVienService.getOne(dto.getNhanvienid()).get();

        id1.setDate(dto.getDate());
        id1.setNhanVien(nv);

        LuongId luongId = new LuongId();
        luongId.setNhanVien(nv);
        luongId.setDate(dto.getDate());
        if(!repositoryLuong.existsById_NhanVien_IdAndId_Date(dto.getNhanvienid(),dto.getDate())){
            Luong newLuong = new Luong();
            newLuong.setId(luongId);
            luongService.save(newLuong);
        }
        Luong luong = repositoryLuong.getOne(luongId);
        HeSoLuong hsl = new HeSoLuong();
        hsl.setHeSoLuong(dto.getHeSoLuong());
        hsl.setNgayDieuChinhLuong(dto.getDate());

        repositoryHeSoLuong.save(hsl);
        luong.setHeSoLuong(hsl);
        luongService.save(luong);
        return new ResponseEntity<>(hsl,HttpStatus.OK);
    }
    @PostMapping("/phucap")
    public ResponseEntity<?> addKyLuat(@RequestBody PhuCapDTO dto){
        System.out.println(dto.getTienPhuCap());
        LuongId id1 = new LuongId();
        NhanVien nv = nhanVienService.getOne(dto.getNhanvienid()).get();
        id1.setDate(dto.getDate());
        id1.setNhanVien(nv);

        LuongId luongId = new LuongId();
        luongId.setNhanVien(nv);
        luongId.setDate(dto.getDate());
        if(!repositoryLuong.existsById_NhanVien_IdAndId_Date(dto.getNhanvienid(),dto.getDate())){
            Luong newLuong = new Luong();
            newLuong.setId(luongId);
            luongService.save(newLuong);
        }
        Luong luong = repositoryLuong.getOne(luongId);
        PhuCap pc = new PhuCap();
        pc.setNgayPhuCap(dto.getDate());
        pc.setTienPhuCap(dto.getTienPhuCap());


        repositoryPhuCap.save(pc);
        luong.setPhuCap(pc);
        luongService.save(luong);
        return new ResponseEntity<>(pc,HttpStatus.OK);
    }
    @PostMapping("/tamung")
    public ResponseEntity<?> addKyLuat(@RequestBody TamUngDTO dto){
        LuongId id1 = new LuongId();
        NhanVien nv = nhanVienService.getOne(dto.getNhanvienid()).get();
        TamUng tamUng = new TamUng();
        id1.setDate(dto.getDate());
        id1.setNhanVien(nv);

        LuongId luongId = new LuongId();
        luongId.setNhanVien(nv);
        luongId.setDate(dto.getDate());
        if(!repositoryLuong.existsById_NhanVien_IdAndId_Date(dto.getNhanvienid(),dto.getDate())){
            Luong newLuong = new Luong();
            newLuong.setId(luongId);
            luongService.save(newLuong);
        }
        Luong luong = repositoryLuong.getOne(luongId);

        tamUng.setLyDo(dto.getLyDo());
        tamUng.setNgayTamUng(dto.getNgayTamUng());
        tamUng.setTienTamung(dto.getTienTamung());

        repositoryTamUng.save(tamUng);
        luongService.save(luong);
        return new ResponseEntity<>(tamUng,HttpStatus.OK);
    }


}
