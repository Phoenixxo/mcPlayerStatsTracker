# mcPlayerStatsTracker (Minecraft Plugin)

This is a custom Minecraft plugin that tracks player statistics and sends them to a REST API for storage and analysis.

> This plugin is built to work with the following api:
👉 [mcplayer-stats-api](https://github.com/Phoenixxo/mcplayer-stats-api)

## Features
- Tracks:
    - Player deaths
    - Player kills
    - Block breaks
    - Login/logout events
- Sends data to a Spring Boot REST API over HTTP
- Uses Java's `HttpClient` for async, non-blocking communication

## Requirements
- Spigot or Paper Minecraft server
- Java 17+
- Spring Boot REST API running at `http://localhost:8080`

## Build Instructions
1. Clone the repo
2. Run:
   ```bash
   mvn clean package
