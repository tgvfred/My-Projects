package com.disney.api.restServices.github.content;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Content {
	private String name;
	private String path;
	private String sha;
	private Integer size;
	private String url;
	private String htmlUrl;
	private String gitUrl;
	@JsonProperty("download_url")
	private String downloadUrl;
	private String type;
	private String content;
	private String encoding;
	private Links links;

	/**
	 * 
	 * @return
	 * The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 * The name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return
	 * The path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 
	 * @param path
	 * The path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 
	 * @return
	 * The sha
	 */
	public String getSha() {
		return sha;
	}

	/**
	 * 
	 * @param sha
	 * The sha
	 */
	public void setSha(String sha) {
		this.sha = sha;
	}

	/**
	 * 
	 * @return
	 * The size
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * 
	 * @param size
	 * The size
	 */
	public void setSize(Integer size) {
		this.size = size;
	}

	/**
	 * 
	 * @return
	 * The url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 
	 * @param url
	 * The url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 
	 * @return
	 * The htmlUrl
	 */
	public String getHtmlUrl() {
		return htmlUrl;
	}

	/**
	 * 
	 * @param htmlUrl
	 * The html_url
	 */
	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

	/**
	 * 
	 * @return
	 * The gitUrl
	 */
	public String getGitUrl() {
		return gitUrl;
	}

	/**
	 * 
	 * @param gitUrl
	 * The git_url
	 */
	public void setGitUrl(String gitUrl) {
		this.gitUrl = gitUrl;
	}

	/**
	 * 
	 * @return
	 * The downloadUrl
	 */
	public String getDownloadUrl() {
		return downloadUrl;
	}

	/**
	 * 
	 * @param downloadUrl
	 * The download_url
	 */
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	/**
	 * 
	 * @return
	 * The type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @param type
	 * The type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * @return
	 * The content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 
	 * @param content
	 * The content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 
	 * @return
	 * The encoding
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * 
	 * @param encoding
	 * The encoding
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * 
	 * @return
	 * The links
	 */
	public Links getLinks() {
		return links;
	}

	/**
	 * 
	 * @param links
	 * The _links
	 */
	public void setLinks(Links links) {
		this.links = links;
	}


	public class Links {

		private String self;
		private String git;
		private String html;

		/**
		 * 
		 * @return
		 * The self
		 */
		public String getSelf() {
			return self;
		}

		/**
		 * 
		 * @param self
		 * The self
		 */
		public void setSelf(String self) {
			this.self = self;
		}

		/**
		 * 
		 * @return
		 * The git
		 */
		public String getGit() {
			return git;
		}

		/**
		 * 
		 * @param git
		 * The git
		 */
		public void setGit(String git) {
			this.git = git;
		}

		/**
		 * 
		 * @return
		 * The html
		 */
		public String getHtml() {
			return html;
		}

		/**
		 * 
		 * @param html
		 * The html
		 */
		public void setHtml(String html) {
			this.html = html;
		}

	}	
}
