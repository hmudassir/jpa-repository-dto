package dev.portfolio.notes.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.portfolio.notes.service.NoteService;
import jakarta.validation.Valid;

@RestController
public class NoteController {

	private final NoteService notes;

	public NoteController(dev.portfolio.notes.service.NoteService notes) {
		this.notes = notes;
	}

	@PostMapping("/api/notes")
	public ResponseEntity<NoteResponse> create(@Valid @RequestBody CreateNoteRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(notes.create(request));
	}

	@GetMapping("/api/notes/{id}")
	public NoteResponse get(@PathVariable Long id) {
		return notes.get(id);
	}
}
