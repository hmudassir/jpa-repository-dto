package dev.portfolio.notes.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.portfolio.notes.api.CreateNoteRequest;
import dev.portfolio.notes.api.NoteResponse;
import dev.portfolio.notes.error.NoteNotFoundException;
import dev.portfolio.notes.persistence.Note;
import dev.portfolio.notes.persistence.NoteRepository;

@Service
public class NoteService {

	private final NoteRepository notes;

	public NoteService(NoteRepository notes) {
		this.notes = notes;
	}

	@Transactional
	public NoteResponse create(CreateNoteRequest request) {
		Note note = notes.save(new Note(request.title().trim(), request.body().trim(), "internal"));
		return response(note);
	}

	@Transactional(readOnly = true)
	public NoteResponse get(Long id) {
		return notes.findById(id).map(NoteService::response).orElseThrow(NoteNotFoundException::new);
	}

	private static NoteResponse response(Note note) {
		return new NoteResponse(note.getId(), note.getTitle(), note.getBody());
	}
}
