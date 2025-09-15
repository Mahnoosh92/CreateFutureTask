# Project Requirements
**Before running the app please put Authorisation key in local.properties file like API_KEY=YOUR_KEY**

# Project Overview ⚡️
This project is a Jetpack Compose application that displays a list of characters from the popular series, Game of Thrones. Users can search for specific characters and view detailed information for each one on a separate screen. The app is built with a modern Android tech stack, leveraging dependency injection, a modular architecture, and network communication for data fetching.

# Features
Character List: Displays a scrollable list of Game of Thrones characters.
Search Functionality: Allows users to filter the list of characters by name.
Detail View: Navigating to a character's profile provides a detailed overview, including their culture, birth and death dates, and titles.
Modular Architecture: The app is structured with separate modules for core functionalities and features, promoting code reusability and maintainability.

# Architecture
The application follows a clean architecture and a modular project structure, separating the codebase into distinct modules based on functionality. This approach enhances scalability, testability, and team collaboration.

# Modularization
app: The main application module that orchestrates the other modules.
core: Contains foundational code shared across the application, including:
common: Utility classes and shared resources.
designsystem: Reusable UI components for consistent theming.
network: Handles all API interactions using Retrofit and OkHttp.
testing: Test utilities and shared testing dependencies.
threading: Manages coroutine dispatchers.
feature: Houses feature-specific code, with each feature being self-contained.
home: Manages the character list and search screen.
detail: Manages the character detail screen.

# Core Libraries
Jetpack Compose: For a declarative UI.
Hilt: For dependency injection, simplifying the management of dependencies.
Retrofit: A type-safe HTTP client for making API calls.
Kotlinx Serialization: A powerful serialization library for parsing JSON data from the network.
Coroutines: For asynchronous programming and managing background tasks.
Jetpack Navigation: To manage in-app navigation between screens.