package com.zzr.xyz.zzr.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.zzr.xyz.zzr.entity.Article;
import com.zzr.xyz.zzr.service.ArticleService;
import io.lettuce.core.dynamic.annotation.Param;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin
@Controller
public class adminController {
    private final Logger log = LoggerFactory.getLogger(adminController.class);
    @Autowired
    ArticleService articleService;


    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin/index";
    }




    /**
     * 根据id删除文章
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public String deleteArticleById(@Param("id") Integer id){
        Integer index = articleService.deleteArticleById(id);
        if (index>0){
            return "redirect:danye-list";
        }else {
            return "";
        }
    }

    /**
     * 编辑文章
     * @param article
     * @return
     */
    @PostMapping("/update")
    public String update(@ModelAttribute Article article){
        Integer id = article.getId();
        String title = article.getTitle();
        String content = article.getContent();
        String des = article.getDes();
        String keyword = article.getKeyword();
        String category = article.getCategory();

        Article article1 = new Article();
        article1.setId(id);
        article1.setTitle(article.getTitle());
        article1.setCategory(article.getCategory());
        article1.setContent(article.getContent());
        article1.setDes(article.getDes());
        article1.setKeyword(article.getKeyword());
        System.out.println(article1);
        Integer index = articleService.update(title,content,keyword, des,id);
        if (index>0){
            return "redirect:danye-list";
        }else {
            return "admin/danye-edit";
        }
    }
    /**
     * 跳转
     * @param
     * @return
     */
    @GetMapping("/edit")
    public String edit(Model model,@Param("id") Integer id){
        Article article = articleService.getOne(id);
        model.addAttribute("article", article);
        return "admin/danye-edit";
    }
    /**
     * 文章列表
     *
     * @param model
     * @return
     */
    @GetMapping("danye-list")
    public String danyeList(Model model) {
        List<Article> articles = articleService.findAll();
        model.addAttribute("articles", articles);
        return "admin/danye-list";
    }

    /**
     * 添加文章
     *
     * @param
     * @return
     */
    @PostMapping(value = "addArticle", produces = "application/json;charset=UTF-8")
    public String addArticle(@ModelAttribute Article article) {

        String title = article.getTitle();
        String content = article.getContent();
        String des = article.getDes();
        String keyword = article.getKeyword();
        String category = article.getCategory();
        Date date = new Date();

        Article article1 = new Article();
        article1.setTitle(title);
        article1.setCategory(category);
        article1.setContent(content);
        article1.setDes(des);
        article1.setKeyword(keyword);
        article1.setCreateTime(date);
        System.out.println(article1);
        articleService.save(article1);
//         return new Result(true, StatusCode.OK,"增加成功", articleService.save(article1));
        return "redirect:/danye-list";
    }

    @GetMapping("danye-detail")
    public String danyeDetail() {
        return "index";
    }

    @GetMapping("123")
    public String tian123(){
        return "123";
    }

    @ResponseBody
    @RequestMapping(value="/uploadFile")
    public String uploadFile(HttpServletRequest request, @Param("file") MultipartFile file) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
        String res = sdf.format(new Date());
        //服务器上使用
//         String rootPath =request.getServletContext().getRealPath("/resource/uploads/");//target的目录
        //本地使用
//        String rootPath ="c:/test/";
//        String rootPath = ResourceUtils.getURL("classpath:").getPath();
        String rootPath = ResourceUtils.getURL("classpath:").getPath();
        System.out.println(rootPath);
          //原始名称
        String originalFilename = file.getOriginalFilename();
        //新的文件名称
        String newFileName = res + originalFilename.substring(originalFilename.lastIndexOf("."));
          //创建年月文件夹
        Calendar date = Calendar.getInstance();
        File dateDirs = new File(date.get(Calendar.YEAR) + File.separator + (date.get(Calendar.MONTH) + 1));
        //新文件
        File newFile = new File(rootPath + File.separator + dateDirs + File.separator + newFileName);
        //判断目标文件所在的目录是否存在
        if (!newFile.getParentFile().exists()) {
        //如果目标文件所在的目录不存在，则创建父目录
            newFile.getParentFile().mkdirs();
        }
        System.out.println(newFile);
        //将内存中的数据写入磁盘
        file.transferTo(newFile);
        //完整的url
        String fileUrl = "/upload/" + date.get(Calendar.YEAR) + "/" + (date.get(Calendar.MONTH) + 1) + "/" + newFileName;
          Map<String, Object> map = new HashMap<String, Object> ();
          Map<String, Object> map2 = new HashMap<String, Object> ();
          map.put("code", 0);//0表示成功，1失败
          map.put("msg", "上传成功");//提示消息
          map.put("data", map2);
          map2.put("src", fileUrl);//图片url
          map2.put("title", newFileName);//图片名称，这个会显示在输入框里
          String result = new JSONObject(map).toString();
          return result;
        }


    /**
     * 分页查询
     * @param model
     * @param page
     * @return
     */
    @GetMapping("findAll")
    public String findAll(Model model,@Param("page") Integer page, Integer size){
        Page<Article> articles = articleService.findArticleNoQ(page,size);
        List<Article> articles2 = articleService.findAll();
        model.addAttribute("articles",articles);
        model.addAttribute("pageSize",size);
        model.addAttribute("curPage",page);
        model.addAttribute("totalCount",articles2.size());
        return "admin/danye-list";
    }
}
