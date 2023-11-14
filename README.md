# AES_DES_3DES_Image_Encryption_Decryption

[![License MIT](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/GentritIbishi/AES_DES_3DES_Image_Encryption_Decyption/blob/master/LICENSE.txt)

## Version 2.0
This application provides a secure way to encrypt and decrypt image files using industry-standard algorithms and cryptographic techniques.

### University Information
- **University:** University of Prishtina "Hasan Prishtina"
- **Faculty:** Faculty of Electrical and Computer Engineering
- **Program:** Computer and Software Engineering

### About the Author
- **Name:** Gentrit Ibishi
- **Email:** gentritibishi@gmail.com
- **Date:** November 13, 2023

## Overview
This GitHub repository contains the source code and documentation for an image encryption and decryption program. The application supports various cryptographic algorithms and modes, offering flexibility and security.

### Features
- **Key Sizes:**
  - DES:
    - 56-bit key: 8 bytes (including parity)
  - AES:
    - 128-bit key: 16 bytes
    - 192-bit key: 24 bytes
    - 256-bit key: 32 bytes
  - 3DES:
    - 56-bit key: 8 bytes (including parity) Not supported (Library Bouncy Castle)
    - 112-bit key: 16 bytes (including parity)
    - 168-bit key: 24 bytes (including parity)
- **Java Cryptographic Extension (JCE):** Utilizes the Java Cryptographic Extension for robust cryptographic operations.
- **Bouncy Castle API:** Incorporates the lightweight cryptography API for Java, Bouncy Castle, available in the `org.bouncycastle.crypto` package.

## Getting Started
To use the program, follow the instructions provided in the [documentation](https://github.com/GentritIbishi/AES_DES_3DES_Image_Encryption_Decyption/blob/master/documentation.pdf). Ensure that you have Java Cryptographic Extension and Bouncy Castle API installed.

## Preview
![Application overview](https://github.com/GentritIbishi/AES_DES_3DES_Image_Encryption_Decyption/blob/master/screenshots/1.png)
![JCrypt overview](https://github.com/GentritIbishi/AES_DES_3DES_Image_Encryption_Decyption/blob/master/screenshots/2.png)
![JCrypt overview set key](https://github.com/GentritIbishi/AES_DES_3DES_Image_Encryption_Decyption/blob/master/screenshots/3.png)
![Key overview](https://github.com/GentritIbishi/AES_DES_3DES_Image_Encryption_Decyption/blob/master/screenshots/4.png)

## Video Preview
[![Watch the Video](https://github.com/GentritIbishi/AES_DES_3DES_Image_Encryption_Decyption/blob/master/screenshots/thumbnail.png)](https://github.com/GentritIbishi/AES_DES_3DES_Image_Encryption_Decyption/blob/master/screenshots/Application%20video.wmv)

## Installation
To install the required dependencies, run the following commands:

```bash
# Install Java Cryptographic Extension
sudo apt-get install openjdk-8-jdk

# Install Bouncy Castle API see the documentation to configure.

