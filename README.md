# Audio File Analyzer

This Java application identifies and analyzes audio file formats (WAV and FLAC) by examining their file signatures and metadata headers. It can detect file types and display detailed technical information about the audio files.

## Features
-Detects WAV and FLAC audio formats
-Displays comprehensive header information for each format
-Supports reading and interpreting binary metadata
-Converts byte data to human-readable formats
-Simple command-line interface

## Supported Formats
| Format | Magic Number (Hex) | Identifier |
|--------|--------------------|------------|
| WAV    | `52 49 46 46`      | "RIFF" + "WAVE" |
| FLAC   | `66 4c 61 43`      | "fLaC" |


## How to Use
1. Compile the program:
   ```bash
   javac MultimediaProjectSound.java
   ```
2. Run the detector:
   ```bash
   java MultimediaProjectSound
   ```
3. To check other files, modify the file path in the `main()` method before compiling

## Requirements
- Java Runtime Environment (JRE) 8 or later

## Code Overview

The program consists of three main components:

### File Type Detection (`whichType` method)
- Reads file signatures to identify WAV or FLAC formats
- Returns the file type or "not supported" message

### WAV Header Parser (`displayHeaderWave` method)
- Extracts and displays all standard WAV header information
- Shows technical details like sample rate, bit depth, and channels

### FLAC Metadata Reader (`displayHeaderFlac` method)
- Parses FLAC metadata blocks
- Displays STREAMINFO block containing audio specifications
- Shows MD5 signature for file verification

## Technical Details
- Uses low-level byte reading for accurate format detection
- Implements custom byte-to-integer conversion methods
- Handles both big-endian and little-endian data
- Includes hexadecimal conversion utilities

# Example Output

For a FLAC file:

This is Flac file  
The header of this file  
STREAMINFO block found  
Minimum block size: 4096 samples  
Maximum block size: 4096 samples  
Minimum frame size: 14 bytes  
Maximum frame size: 18731 bytes  
Sample rate: 44100 Hz  
Number of channels: 2  
Bits per sample: 16  
Total samples: 12648960  
MD5 signature: Baiaseyd... [truncated]

## Limitations
- Currently supports only WAV and FLAC formats
- Requires exact file paths (no drag-and-drop)
- Limited error handling for corrupt files

## Future Enhancements
- Add support for MP3, AAC, and other formats
- Implement audio visualization
- Add batch processing capability
- Create GUI interface
