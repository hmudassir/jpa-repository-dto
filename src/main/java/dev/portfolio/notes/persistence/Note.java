package dev.portfolio.notes.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notes")
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 120)
	private String title;

	@Column(nullable = false, length = 1000)
	private String body;

	@Column(name = "reviewer_only", nullable = false, length = 200)
	private String reviewerOnly;

	protected Note() {
	}

	public Note(String title, String body, String reviewerOnly) {
		this.title = title;
		this.body = body;
		this.reviewerOnly = reviewerOnly;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}

	public String getReviewerOnly() {
		return reviewerOnly;
	}
}
