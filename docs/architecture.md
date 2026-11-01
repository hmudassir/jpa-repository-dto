# Architecture

**Patterns:** Repository, and Data Transfer Object. The request and response records are the DTOs. `NoteRepository` is the repository.

## Boundaries

```text
POST /api/notes          CreateNoteRequest
GET  /api/notes/{id}     NoteResponse
        |
        v
NoteController                 HTTP only
        |
        v
NoteService                    one transaction
        |-- maps request -> Note
        |-- NoteRepository     the only JPA access
        +-- maps Note -> NoteResponse
                |
                v
              notes table
              title, body, reviewer_only
```

`reviewer_only` stays on the entity. `NoteResponse` has `id`, `title`, and `body`. Open-in-view is off, so the mapping happens inside the service transaction. The controller does not touch the repository.

Flyway owns the table. Hibernate validates the entity against that table and does not create it.

## Request path

1. Bean Validation checks the request record before the service runs.
2. The service trims the text, stores the entity, and sets `reviewerOnly` to a value the API does not accept from the caller.
3. The service copies the public fields into `NoteResponse` and returns that.
4. A missing id throws `NoteNotFoundException`. `ApiExceptionHandler` turns it into problem JSON. The controller stays free of status codes for that case.

## Failure

| Condition | Result |
| --- | --- |
| Blank title or blank body | `400` |
| Unknown id | `404`, `"code":"NOT_FOUND"`, no row contents |
| Saved note | `201` and a body without `reviewerOnly` |

## What this sample leaves out

One entity and one repository. A second feature would have its own repository and would be called through its service. That boundary is the module-boundary sample.
