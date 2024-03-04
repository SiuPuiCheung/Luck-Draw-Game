# Luck-Draw-Game

# Lucky Draw Game Project

## Overview
This project implements a lucky draw game system in Java, designed to simulate various types of competitions such as number entry games, lucky number draws, and random pick competitions. The system manages members, entries, and competitions, providing a robust platform for conducting and tracking different competition types.

## Features
- **Member Management:** Handles member registration, including information storage and retrieval.
- **Competition Management:** Supports different types of competitions, including auto number entries, lucky numbers, and random picks.
- **Entry Submission:** Allows members to submit entries for competitions, with support for auto-generated and manual entries.
- **Winner Determination:** Implements logic to determine winners based on competition rules.

## Project Structure
- `AutoNumbersEntry.java`: Defines auto-generated number entries for competitions.
- `Bill.java`: Manages billing and payment information for competition entries.
- `Competition.java`: Abstract class for creating different types of competitions.
- `DataAccessException.java`: Handles exceptions related to data access errors.
- `DataFormatException.java`: Manages exceptions for data format issues.
- `DataProvider.java`: Interface for data provision and manipulation.
- `Entry.java`: Abstract class for competition entries.
- `LuckyNumbersCompetition.java`: Implements a lucky numbers competition.
- `Member.java`: Represents a member participating in competitions.
- `NumbersEntry.java`: Defines manual number entries for competitions.
- `RandomPickCompetition.java`: Implements a competition where winners are randomly picked.
- `SimpleCompetitions.java`: Main class for managing competitions and interactions.

## Getting Started
To run the project, compile and execute the `SimpleCompetitions.java` as the main entry point. Ensure all classes are correctly linked in your development environment.

## Contribution
Contributions are welcome. Please fork the repository and submit a pull request for any enhancements, bug fixes, or improvements.

## License
This project is open source and available under the [MIT License](LICENSE).

