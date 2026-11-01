package dev.portfolio.notes.error;

public class NoteNotFoundException extends RuntimeException {

	public NoteNotFoundException() {
		super("Note was not found");
	}
}
