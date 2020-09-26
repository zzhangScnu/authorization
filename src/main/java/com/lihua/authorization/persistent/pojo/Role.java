package com.lihua.authorization.persistent.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Role {

    @Id
    private Long id;

    private String role;

    @JsonIgnoreProperties(value = {"roleList"})
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "role_resource",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "resource_id", referencedColumnName = "id")})
    private List<Resource> resourceList;

    //    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roleList") 这是一对多的情况
    @JsonIgnoreProperties(value = {"roleList"})
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT) // cannot simultaneously fetch multiple bags: 默认JOIN模式时多张表外连接时字段重名，无法映射
    @JoinTable(name = "user_role",
            //joinColumns,当前对象在中间表中的外键
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            //inverseJoinColumns，对方对象在中间表的外键
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private List<User> userList;
}
