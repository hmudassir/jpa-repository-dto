package dev.portfolio.notes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.jayway.jsonpath.JsonPath;

import dev.portfolio.notes.persistence.NoteRepository;

@SpringBootTest
@AutoConfigureMockMvc
class NoteLayerTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private NoteRepository notes;

	@Test
	void theResponseIsARecordAndTheEntityKeepsTheHiddenColumn() throws Exception {
		MvcResult created = mvc.perform(post("/api/notes")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"title\":\"Hello\",\"body\":\"A saved note\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.title").value("Hello"))
				.andExpect(jsonPath("$.body").value("A saved note"))
				.andExpect(jsonPath("$.reviewerOnly").doesNotExist())
				.andReturn();
		Number id = JsonPath.read(created.getResponse().getContentAsString(), "$.id");

		assertThat(notes.findById(id.longValue()).orElseThrow().getReviewerOnly()).isEqualTo("internal");

		mvc.perform(get("/api/notes/" + id.longValue()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.reviewerOnly").doesNotExist());
	}

	@Test
	void aMissingNoteIsNotFound() throws Exception {
		mvc.perform(get("/api/notes/404"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.code").value("NOT_FOUND"))
				.andExpect(jsonPath("$.detail").value("Note was not found"));
	}
}
