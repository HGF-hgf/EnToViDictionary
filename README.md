# English-Vietnamese-Dictionary
A dictionary for English-Vietnamese translation written in Java

## Table of Contents
- [Authors](#authors)
- [Features](#features)
- [Preview](#preview)
- [Installation](#installation)
- [Requirements](#requirements)
- [Project Structure](#project-structure)

## Authors
- [Nguyễn Viết Hoàng](https://github.com/HGF-hgf) - 22028122
- [Lê Thế Phương Minh](https://github.com/evergard3n) - 22028089
- [Quản Xuân Trường](https://github.com/quanxuantruong) - 22028031
- [Nguyễn Đình Tú](https://github.com/dinhtu2714) - 22028159

## Features

- An English-Vietnamese dictionary with a full database of words and their meanings 
and Google Translate API

- Other features:
    - Search for a word and its meaning (the meaning will be displayed in Vietnamese)
    - Add a new word and its meaning to the database
    - Edit a word and its meaning in the database
    - Delete a word and its meaning in the database
    - Translate sentences from English to Vietnamese (using Google Translate API)
    - Translate sentences from Vietnamese to English (using Google Translate API)
    - Google Translate TTS (Text to Speech) for English and Vietnamese
    - Connect to Sqlite database for storing data
    - Games for learning English vocabulary

## Preview

![Search for a word](https://github.com/HGF-hgf/EnToViDictionary/blob/master/src/main/resources/com/example/dictionary/images/search.png)

![Add a new word](https://github.com/HGF-hgf/EnToViDictionary/blob/master/src/main/resources/com/example/dictionary/images/add.png)

![Google Translate](https://github.com/HGF-hgf/EnToViDictionary/blob/master/src/main/resources/com/example/dictionary/images/api.png)

![Games](https://github.com/HGF-hgf/EnToViDictionary/blob/master/src/main/resources/com/example/dictionary/images/game.png)

## Installation

1. Clone the repo
   ```sh
   git clone https://github.com/HGF-hgf/EnToViDictionary.git
   ``` 
2. Open the project in IntelliJ IDEA
3. Run the project
4. Enjoy!

## Requirements
- JDK 17 or above (for Windows) user: Download [here](https://www.oracle.com/java/technologies/downloads/#java17)
to make sure you have the suitable version of JDK)
- Maven (Apache Maven)

## Project Structure
- The project is written in Java, using JavaFX for the GUI
- The project uses Google Translate API for translating sentences from English to Vietnamese
- The project uses Sqlite database for storing data
- The project uses Maven for building and managing the project