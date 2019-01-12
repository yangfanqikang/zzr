package com.zzr.xyz.zzr.service;

import com.github.pagehelper.Page;
import com.zzr.xyz.zzr.dao.ArticleRepository;
import com.zzr.xyz.zzr.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    public List<Article> findAll(){
        return articleRepository.findAll();
    }

    public Article save(Article article){
        return articleRepository.save(article);
    }

    public Article getOne(Integer id){return articleRepository.getOne(id);}

    public int update(String title, String content, String keyword, String des,Integer id){return articleRepository.update( title,content,keyword, des,id);}

    public int deleteArticleById(Integer id){return articleRepository.deleteArticleById(id);}

    public Page<Article> findArticleNoQ(Integer page, Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        return  articleRepository.findArticleNoC(pageable);
    }

}
