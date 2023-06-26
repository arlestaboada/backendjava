package com.arles.backendjava.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class PostDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String postID;
    private String title;
    private String content;
    private Date createdAt;
    private Date expiresAt;

    private UserDto user;
    private ExposureDto exposure;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPostID() {
        return this.postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
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

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getExpiresAt() {
        return this.expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
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
