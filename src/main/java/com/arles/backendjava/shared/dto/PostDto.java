package com.arles.backendjava.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class PostDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String post_id;
    private String title;
    private String content;
    private Date created_at;
    private Date expires_at;

    private UserDto user;
    private ExposureDto exposure;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPost_id() {
        return this.post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getExpires_at() {
        return this.expires_at;
    }

    public void setExpires_at(Date expires_at) {
        this.expires_at = expires_at;
    }

    public UserDto getUser() {
        return this.user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public ExposureDto getExposure() {
        return this.exposure;
    }

    public void setExposure(ExposureDto exposure) {
        this.exposure = exposure;
    }

}
