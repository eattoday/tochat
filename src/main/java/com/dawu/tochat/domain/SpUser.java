package com.dawu.tochat.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @TableName sp_user
 */
@TableName(value = "sp_user")
@Component
@ApiModel("用户实体类")
public class SpUser implements Serializable {

    /**
     *
     */
    @ApiModelProperty(value = "用户ID",notes = "eq")
    @TableId(type = IdType.ASSIGN_UUID)
    private String userId;
    /**
     *
     */
    @TableField("user_name")
    @ApiModelProperty(value = "用户ID",notes = "like")
    private String userName;

    /**
     *
     */
    private String password;

    /**
     *
     */
    private String nickName;

    /**
     *
     */
    private String avatar;

    /**
     *
     */
    private String signature;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public String getUserId() {
        return userId;
    }

    /**
     *
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     *
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     */
    public String getNickName() {
        return nickName;
    }

    /**
     *
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     *
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     *
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     *
     */
    public String getSignature() {
        return signature;
    }

    /**
     *
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }


}
