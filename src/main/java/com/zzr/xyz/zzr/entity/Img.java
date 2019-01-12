package com.zzr.xyz.zzr.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table
@Entity
@Component
public class Img {
    @Id
    @GeneratedValue
    private Integer id;
    private String imgUrl;
    private Integer articleId;
}
