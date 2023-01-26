[kotlin-badge]: https://img.shields.io/badge/Kotlin-0095D5?logo=kotlin&logoColor=white
[gradle-badge]: https://img.shields.io/badge/gradle-02303A?logo=gradle&logoColor=white
## MOSSUP
![kotlin-badge]
![gradle-badge]


MOSSUP is a cross-platform GUI application for MOSS that aims to simplify interaction with the service. 
It is primarily being developed within the computer science department at University of Primorska for internal use.

### Features
* Bulk import Assignments for testing. The import assumes the following structure, which is tailored to a Moodle export. 

  ```
  Assignments
    |--> Student1
    |   |--> File
    |   |--> Folder
    |--> Student2
    |--> .
    |--> .
    |--> Studentn
  ```
MOSSUP assumes the subfolders in Assignments as the Student's name. It recursively searchers for source files within each student's folder.

* Include/exclude assignments before submitting
* Submit to Moss
* Parse global similarity results
![image](https://user-images.githubusercontent.com/2729743/214973499-47da3e05-cf80-463f-b160-babf712f6d0b.png)
* Display code comparison between two assignments with highlighted code blocks
![image](https://user-images.githubusercontent.com/2729743/214973157-d793951b-bc0c-4cd1-9637-d9d529c5b434.png)
* Export results to xlsx
* Moss options for changing the key, and selecting target language.

### Planed
* Graph based visualisation 
* Local DB of previously submitted checks
* Load from MOSS url
* Support direct download from Moddle assginment via URL (credentials required)
* Support manual addition of individual assignments
* Better code comparison visualisation (row indexed)
* Releases

### Prerequisites

* Gradle
* Java 9 or greater


## Authors

* **Aleksandar Tošić** - *Initial Work* - [Dormage](https://github.com/Dormage)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

