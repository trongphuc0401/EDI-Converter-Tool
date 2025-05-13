# EDI Converter Tool

A Java-based command-line application for converting **EDIFACT** messages to **XML** and vice versa, designed for B2B integration. The tool supports multiple message types (e.g., INVOIC, ORDERS), validates EDIFACT syntax, and uses XSLT for transformations.

---

## Features
- 🔄 **Bidirectional conversion**: EDIFACT ↔ XML  
- 🧩 **Supports multiple message types** via JSON configuration  
- ✅ **Validates EDIFACT syntax** (mandatory segments, reference numbers, counts)  
- 📂 **Efficient parsing of large files** using SAX parser  
- 📋 **Detailed logging** with Log4j2  
- 🧪 **Unit tests** with JUnit 5  
- 🔧 **Extensible** for additional message types and formats  

---

## Tech Stack
- **Java 17**: Core programming language  
- **Maven**: Dependency management and build tool  
- **XSLT 2.0 (Saxon-HE)**: Data transformation  
- **SAX Parser**: Efficient EDIFACT parsing  
- **Log4j2**: Structured logging  
- **Jackson**: JSON configuration parsing  
- **JUnit 5**: Unit testing  
- **Smooks** (optional): Advanced EDI parsing  

---

## Prerequisites
- Java 17 or higher  
- Maven 3.6+  
- Git  

---

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/edi-converter-tool.git
2. Navigate to the project directory:
   ```bash
   cd edi-converter-tool
3. Build the project:
## Usage
Run the CLI tool with:
   ```bash
   java -jar target/edi-converter-tool-1.0-SNAPSHOT.jar <input.edi> <output.xml> <messageType>
   ```
Example:
   ```bash
   java -jar target/edi-converter-tool-1.0-SNAPSHOT.jar input.edi output.xml INVOIC
   ```

## Project Structure
  ```bash
  edi-converter-tool/
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── com/example/edi/      # Core Java code
    │   │   ├── resources/
    │   │   │   ├── xslt/                # XSLT stylesheets
    │   │   │   ├── config.json          # Message type configuration
    │   │   │   └── log4j2.xml           # Log4j configuration
    │   ├── test/                       # Unit tests
    ├── pom.xml                         # Maven configuration
    ├── README.md                       # Project documentation

  ```
## Example Input
input.edi:
   ```vbnet
  UNA:+.? '
  UNB+UNOC:3+1234567890123:14+9876543210987:14+250513:1430+INV001++1'
  UNH+000000001+INVOIC:D:96A:UN'
  BGM+380+INV12345+9'
  UNT+3+000000001'
UNZ+1+INV001'
  ```
output.xml:
  ```xml
  <Interchange>
    <Header>
        <SyntaxIdentifier>UNOC:3</SyntaxIdentifier>
        <Sender>1234567890123:14</Sender>
        <Receiver>9876543210987:14</Receiver>
    </Header>
    <Messages>
        <Message>
            <Header>
                <ReferenceNumber>000000001</ReferenceNumber>
                <Type>INVOIC:D:96A:UN</Type>
            </Header>
            <Document>
                <NameCode>380</NameCode>
                <Number>INV12345</Number>
            </Document>
            <Trailer>
                <SegmentCount>3</SegmentCount>
            </Trailer>
        </Message>
    </Messages>
    <Trailer>
        <MessageCount>1</MessageCount>
    </Trailer>
</Interchange>
  ```
## Running Tests
Execute unit tests with:
  ```bash
    Execute unit tests with:
  ```

## Future Enhancements
- Add Spring Boot for a web-based interface with REST API
- Implement XSD validation for XML output
- Support additional EDIFACT versions (e.g., D.01B)
- Deploy as a serverless application on AWS Lambda
## Contact
Nguyen Trong Phuc - phucsk098@gmail.com



