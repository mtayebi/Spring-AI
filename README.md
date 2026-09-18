# Spring AI Banking Assistant with MCP

This project demonstrates a possible future for banking websites: a simple conversational interface where users can describe what they want, while Spring AI and MCP-enabled services interpret the request and perform the appropriate action.

Instead of navigating through many menus and forms, users interact with the banking system through a chat interface.

For example, a user could write:

> Show me my recent transactions.

The AI analyzes the request, determines which banking operation is needed, and sends the appropriate request to the MCP server. The MCP server communicates with the database and returns the result.

---

## Project Architecture

This project consists of two separate Spring Boot applications:

### `demo.client`

The client application is responsible for:

- Receiving user messages
- Analyzing requests using Spring AI
- Understanding the user's intended action
- Selecting the appropriate MCP operation
- Sending requests to the MCP server
- Returning the result to the user

### `demo.server`

The server application is responsible for:

- Providing MCP tools and operations
- Connecting to the database
- Executing banking-related actions
- Retrieving and updating banking data
- Returning results to the client application

---

## Request Flow

```text
User
  |
  v
Chat Interface
  |
  v
demo.client
  |
  | Analyze request with Spring AI
  |
  v
MCP Request
  |
  v
demo.server
  |
  | Execute MCP tool
  |
  v
Database
  |
  v
Result returned to the user

---

## Technologies

- Java 21
- Spring Boot
- Spring AI
- Model Context Protocol (MCP)
- Maven
- Database integration
- REST or MCP communication
- Git and GitHub

---

