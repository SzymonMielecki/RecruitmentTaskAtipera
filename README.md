# GitHub Repository Lister

A Spring Boot application that lists GitHub repositories for a given user, excluding forks, and provides branch
information with commit SHAs.

## Features

- List all non-fork repositories for a GitHub user
- Get branch information including commit SHAs
- Proper error handling with 404 responses for non-existent users
- Support for GitHub API authentication (optional)

## API Endpoints

### GET /?username={username}

Returns all non-fork repositories for the specified GitHub user.

**Parameters:**

- `username`: GitHub username

**Response:**

```json
{
  "name": "username",
  "repositories": [
    {
      "name": "repo-name",
      "branches": [
        {
          "name": "main",
          "hash": "abc123..."
        }
      ]
    }
  ]
}
```

**Error Response (404):**

```json
{
  "status": 404,
  "message": "User not found: username"
}
```

## Configuration

### GitHub Token Setup

For the app to function, you need to provide your own api token

**Environment Variable:**

   ```bash
   export GITHUB_API_TOKEN=your_github_token_here
   ```

## Running the Application

1. Clone the repository
2. Set up your GitHub token
3. Run the application:
   ```bash
   ./gradlew bootRun
   ```

4. The application will be available at `http://localhost:8080`

## Example Usage

```bash
# Using environment variable for token
curl http://localhost:8080?username=SzymonMielecki
```

## Error Handling

The application handles various error scenarios:

- 404: User not found
- Proper error messages with status codes

## Testing

```bash
./gradlew test
``` 