package com.asleepyfish.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.NotBlank;

@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    //@NotBlank
    private String name;
    //@NotBlank
    private String password;
    private String salt;
}
