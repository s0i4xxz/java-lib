# SatoshiDevLib

A production-ready Java SDK for the Satoshi API. Clean, modular, and developer-friendly.

## Installation

### Maven

```xml
<dependency>
  <groupId>s0i4x</groupId>
  <artifactId>satoshidevlib</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Gradle

```gradle
implementation 's0i4x:satoshidevlib:1.0.0'
```

### JitPack

Add the JitPack repository:

**Maven:**

```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependency>
  <groupId>com.github.s0i4x</groupId>
  <artifactId>satoshidevlib</artifactId>
  <version>1.0.0</version>
</dependency>
```

**Gradle:**

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.s0i4x:satoshidevlib:1.0.0'
}
```

## Quick Start

```java
import s0i4x.satoshidevlib.client.SatoshiClient;
import s0i4x.satoshidevlib.models.ChatResponse;

SatoshiClient client = SatoshiClient.builder()
    .apiKey("sk-your-api-key")
    .build();

ChatResponse response = client.chat().sendMessage("Hello world!");
System.out.println(response.getContent());
```

Or use the convenience facade:

```java
import s0i4x.satoshidevlib.*;
import s0i4x.satoshidevlib.client.SatoshiClient;

SatoshiClient client = Satoshi.client("sk-your-api-key");
```

## Configuration

```java
import java.time.Duration;

SatoshiClient client = SatoshiClient.builder()
    .apiKey("sk-your-api-key")
    .baseUrl("https://custom-api.example.com")  // Custom base URL
    .timeout(Duration.ofSeconds(60))              // Request timeout
    .maxRetries(5)                                // Max retry attempts
    .proxy("proxy.example.com", 8080)             // HTTP proxy
    .debugMode(true)                              // Enable debug logging
    .build();
```

## Usage

### Simple Chat

```java
ChatResponse response = client.chat().sendMessage("What is Bitcoin?");
System.out.println(response.getContent());
```

### Chat with Model Selection

```java
ChatResponse response = client.chat().sendMessage("gpt-4", "Explain blockchain.");
System.out.println(response.getContent());
```

### Advanced Chat Request

```java
import s0i4x.satoshidevlib.models.ChatRequest;
import java.util.Arrays;

ChatRequest request = ChatRequest.builder()
    .model("gpt-4")
    .messages(Arrays.asList(
        ChatRequest.Message.system("You are a helpful assistant."),
        ChatRequest.Message.user("What is a hash function?")
    ))
    .maxTokens(1024)
    .temperature(0.7)
    .build();

ChatResponse response = client.chat().create(request);
System.out.println(response.getContent());
System.out.println("Tokens used: " + response.getUsage().getTotalTokens());
```

### Multi-turn Conversation

```java
import java.util.Arrays;

ChatResponse response = client.chat().create(Arrays.asList(
    ChatRequest.Message.system("You are a crypto expert."),
    ChatRequest.Message.user("What is Bitcoin?"),
    ChatRequest.Message.assistant("Bitcoin is a decentralized digital currency."),
    ChatRequest.Message.user("How does mining work?")
));
```

## Error Handling

```java
import s0i4x.satoshidevlib.exceptions.*;

try {
    ChatResponse response = client.chat().sendMessage("Hello");
} catch (AuthenticationException e) {
    System.err.println("Invalid API key: " + e.getMessage());
} catch (RateLimitException e) {
    System.err.println("Rate limited. Retry after: " + e.getRetryAfterMs() + "ms");
} catch (ClientException e) {
    System.err.println("Client error (" + e.getStatusCode() + "): " + e.getMessage());
} catch (ServerException e) {
    System.err.println("Server error (" + e.getStatusCode() + "): " + e.getMessage());
} catch (NetworkException e) {
    System.err.println("Network failure: " + e.getMessage());
} catch (SatoshiException e) {
    System.err.println("SDK error: " + e.getMessage());
}
```

## API Reference

### `SatoshiClient`

| Method | Description |
|--------|-------------|
| `chat()` | Access the Chat resource |
| `getConfig()` | Get the client configuration |

### `ChatResource`

| Method | Description |
|--------|-------------|
| `sendMessage(String message)` | Send a simple user message |
| `sendMessage(String model, String message)` | Send a message with a specific model |
| `create(ChatRequest request)` | Send a fully configured chat request |
| `create(List<Message> messages)` | Send a multi-turn conversation |

### Exception Hierarchy

```
SatoshiException (base)
├── ClientException (4xx)
│   ├── AuthenticationException (401/403)
│   └── RateLimitException (429)
├── ServerException (5xx)
└── NetworkException (timeouts, connection failures)
```

### MystVale Namespace

```java
import s0i4x.satoshidevlib.mystvale.MystVale;

// Auto-executes a GET request on class load
MystVale.ready();

// Optionally retrieve the response
String data = MystVale.getLastResponse();
```

## Architecture

```
s0i4x.satoshidevlib
├── client/          → SatoshiClient (main entry point)
├── config/          → SatoshiConfig (immutable configuration)
├── http/            → SatoshiHttpClient (HTTP layer with retries)
├── resources/       → ChatResource (API endpoints)
├── models/          → ChatRequest, ChatResponse, ErrorResponse
├── exceptions/      → Exception hierarchy
├── mystvale/        → MystVale (auto-exec namespace)
└── Satoshi.java     → Convenience facade
```

## Features

- **Builder pattern** for flexible client configuration
- **Automatic retries** with exponential backoff on 429/5xx errors
- **Thread-safe** — share a single client across threads
- **Debug logging** — enable with `.debugMode(true)`
- **Proxy support** — route traffic through an HTTP proxy
- **Configurable base URL** — target different API regions/environments
- **Jackson JSON** serialization with lenient deserialization
- **Java 11+** compatible (uses `java.net.HttpURLConnection` — no external HTTP deps)

## Building from Source

```bash
mvn clean install
```

## License

MIT
