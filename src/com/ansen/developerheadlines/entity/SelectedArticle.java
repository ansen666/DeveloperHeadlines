package com.ansen.developerheadlines.entity;

/**
 * 精选文章实体类
 * @author ansen
 * @create time 2016-04-19
 */
public class SelectedArticle {
	private int id;// 唯一id
	private String title;// 标题
	private int likeNumber;// 点赞数量
	private int commentNumber;// 评论数量
	private String avatarUrl;// 用户头像url
	
	public SelectedArticle(){}
	
	public SelectedArticle(int id, String title, int likeNumber,int commentNumber, String avatarUrl) {
		this.id = id;
		this.title = title;
		this.likeNumber = likeNumber;
		this.commentNumber = commentNumber;
		this.avatarUrl = avatarUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getLikeNumber() {
		return likeNumber;
	}

	public void setLikeNumber(int likeNumber) {
		this.likeNumber = likeNumber;
	}

	public int getCommentNumber() {
		return commentNumber;
	}

	public void setCommentNumber(int commentNumber) {
		this.commentNumber = commentNumber;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

}
