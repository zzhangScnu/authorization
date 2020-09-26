package com.lihua.authorization.persistent.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Resource {

    @Id
    private Long id;

    private String url;

    @JsonIgnoreProperties(value = {"resourceList"})
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_resource",
            joinColumns = {@JoinColumn(name = "resource_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roleList;
}
