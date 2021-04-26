package com.lxl.myblog.po;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Table
@Entity(name = "t_blog")
public class Blog {

    @Id
    @GeneratedValue//自动生成
    private Long id;//博客id

    private String title;//博客标题

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;//博客内容
    private String firstPicture;//首页图片
    private String flag;//标签
    private Integer views;//被查看次数
    private Boolean appreciation = false;//赞赏是否开启
    private Boolean shareStatement = false;//转载声明是否开启
    private Boolean commentable = false;//评论是否开启
    private Boolean published = false;//是否发布
    private Boolean recommend = false;//是否推荐
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    private String description;//描述
    @ManyToOne
    private Type type;

    //级联新增,删除
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Tag> tags = new ArrayList<>();

    @Transient
    private String tagIds;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Comment> comments = new ArrayList<>();

    public void init(){
        this.tagIds = tagsToIds();
    }
    private String tagsToIds(){
        StringBuffer stringBuffer = new StringBuffer();
        boolean flag = false;
        for (Tag t:tags){
            if (flag){
                stringBuffer.append(",");
            }else {
                flag = true;
            }
            stringBuffer.append(t.getId());
        }
        tagIds = stringBuffer.toString();
        return tagIds;
    }
}
