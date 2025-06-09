# Application Documentation

## Introduction

This application is a modular Java framework that implements a basic application lifecycle with configuration management
and multilingual support.

## Architecture

The application follows a three-phase lifecycle:

1. **Initialization** (`init`)
2. **Processing** (`process`)
3. **Cleanup** (`dispose`)

## Main Features

### 1. Multilingual Support

The application includes multilingual support via `ResourceBundle`:

```
java ResourceBundle messages = ResourceBundle.getBundle("i18n/messages");
``` 

Messages are stored in the `i18n/` folder and can be localized for different languages.

### 2. Configuration Management

The application offers two configuration methods:

#### a. Configuration File

- Default: `/config.properties`
- Path can be modified via command-line arguments
- Loaded at application startup

#### b. Command Line Arguments

Supported format: `-parameter=value`
Available parameters:

- `-debug` or `-d`: Debug level (integer)
- `-configFile` or `-cf`: Path to configuration file

### 3. Logging System

An integrated logging system that provides:

- Timestamp (with timezone)
- Source class name
- Log level (INFO, ERROR)
- Formatted message

Example log format:

```
2024-03-21T10:30:00+01:00;tutorials.core.App;INFO;Message
``` 

## Usage

### Starting the Application

```bash
java -jar app.jar -debug=1 -configFile=/path/to/config.properties
```

### Lifecycle

1. **Initialization Phase**:
    - Loading command-line parameters
    - Loading configuration file
    - Setting up system parameters

2. **Processing Phase**:
    - Execution of the main business logic
    - This phase can be extended as needed

3. **Cleanup Phase**:
    - Resource release
    - Operation finalization

## Extensibility

The application is designed to be easily extended:

- Adding new configuration parameters via the method `extractConfigValue`
- Customizing the processing workflow in the `process` method
- Extending the logging system as needed

## Best Practices

- Messages are externalized for easier maintenance and translation
- Configuration is flexible and can be modified without recompilation
- The integrated logging system enables precise execution tracking
