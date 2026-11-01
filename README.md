# JPA, repository, and DTO

An HTTP request uses one model. The database row uses another. The repository is the only door to the row.

`POST /api/notes` accepts a request record. `NoteService` saves a JPA entity through `NoteRepository` and returns a response record. The entity has a `reviewerOnly` column. That column is not on the response record, so it cannot appear in JSON.

A missing note becomes `404` with `"code":"NOT_FOUND"`. The controller does not catch that. One exception handler does.

## Run

Java 21.

```sh
./mvnw spring-boot:run
```

```sh
curl -sS -H 'Content-Type: application/json' \
  -d '{"title":"Hello","body":"A saved note"}' \
  http://127.0.0.1:8080/api/notes
```

## Tests

```sh
./mvnw test
```

## What this is

A portfolio proof of concept. The architecture and the pattern names are in [docs/architecture.md](docs/architecture.md).
