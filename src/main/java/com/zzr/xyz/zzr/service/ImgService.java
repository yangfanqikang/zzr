package com.zzr.xyz.zzr.service;

import com.zzr.xyz.zzr.dao.ImgReposirory;
import com.zzr.xyz.zzr.entity.Img;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImgService {
    @Autowired
    ImgReposirory imgReposirory;

    public Img save(Img img){
        return imgReposirory.save(img);
    }
}
