package com.zzr.xyz.zzr.dao;

import com.github.pagehelper.Page;
import com.zzr.xyz.zzr.entity.Article;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository("ArticleRepository")
public interface ArticleRepository extends JpaRepository<Article,Integer>, JpaSpecificationExecutor<Article> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update article set title= ?1,content= ?2,keyword= ?3,des= ?4 where id= ?5",nativeQuery = true)
    int update(@Param("title") String title, @Param("content") String content, @Param("keyword") String keyword, @Param("des") String des, @Param("id") Integer id);


    @Transactional
    @Modifying
    @Query(value = "delete from article where id =?1",nativeQuery = true)
    int deleteArticleById(@Param("id") int id);


    @Query(value = "SELECT * FROM article ",
            countQuery = "SELECT count(*) FROM article",
            nativeQuery = true)
    Page<Article> findArticleNoC(Pageable pageable);
}
