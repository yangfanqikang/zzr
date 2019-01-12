package com.zzr.xyz.zzr.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Component
@Table
public class Article {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer userId;
    private String title;
    private String thumb;
    private String keyword;
    private String des;
    private String category;
    private String content;
    private Date createTime;
}
