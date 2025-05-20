# Wolf DSL

A Domain Specific Language (DSL) definition for API Orchestration and Data Transformation using the .flow extension. The name 'wolf' (derived by reversing 'flow') represents a Workflow Orchestration Language Framework (WOLF) for APIs.

## Overview

Wolf DSL provides a specialized syntax for defining API orchestration flows and data transformations. It aims to simplify the creation and maintenance of complex API interactions through a dedicated language format.


## Features

- Rich syntax highlighting and content assistance.
- Specialized language constructs for REST & GraphQL orchestration.
- Native GraphQL Support by importing the GraphQL Language.
- Integrated development environment support.
- Custom validations.
- Asynchronous by default. 
- Expressions & Functions for data transformation with extensions to include custom functions.

## Getting Started

### Using flow files

#### Install the Eclipse Plugin
1. Download and install [Eclipse](https://www.eclipse.org/downloads/)
2. Download the Wolf DSL Eclipse plugin from the releases section
3. In Eclipse click `Help -> Install New Software -> Add -> Archive` and select the downloaded plugin zip file
4. Restart Eclipse when prompted. After restart, all .flow files will have syntax highlighting and content-assist

### How to write flow files
See the documentation in the `docs` directory for detailed information on writing flow files.

## Development

### Prerequisites
1. Eclipse IDE
2. Xtext SDK

### Setting up the Development Environment
1. Download and install [Eclipse](https://www.eclipse.org/downloads/)
2. Install Xtext SDK in Eclipse:  
   a. In Eclipse, click Help -> Install New Software  
   b. Add repository with location: [http://download.eclipse.org/modeling/tmf/xtext/updates/composite/releases/](http://download.eclipse.org/modeling/tmf/xtext/updates/composite/releases/)  
   c. Select the repository in 'Work with' dropdown  
   d. Select and install Xtext Complete SDK (2.30.0)  
3. Restart Eclipse

### Setting up the Project
1. Clone the required repositories:
   ```
   git clone https://github.com/graph-quilt/graphql-xtext.git
   git clone https://github.com/wolf-DSL/wolf-language.git
   ```
2. Import both projects in Eclipse: File -> Import Project -> Maven -> Existing Maven Project
3. Under `com.intuit.graphql`, right click on `GenerateGraphQL.mwe2` -> Run As -> 1 MWE2 Workflow
4. Under `com.intuit.dsl.flow`, right click on `GenerateFlow.mwe2` -> Run As -> 1 MWE2 Workflow
5. Edit the Grammar as needed and run step 4 again to regenerate

### Testing the language
1. Create a new "Run Configuration" as an "Eclipse Application" and name it `flowEditor`
2. Run this configuration to open a new Eclipse window where you can create and edit flow files

### Building the Eclipse plugin
1. Select File -> Export -> Plug-in Development -> Deployable features
2. Select the flow feature and specify an archive file as the destination
3. The generated zip file can be distributed and used to add flow file editing capabilities to Eclipse

## Contributing
Please read the [Contribution guide](./CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

## License
This project is licensed under the [LICENSE_NAME] - see the LICENSE file for details.

## Support
Please create an issue in the [GitHub repository](https://github.com/wolf-DSL/wolf-language) for support requests.