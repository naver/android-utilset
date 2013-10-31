# Android Utilset

### Introduction
Utilset is a collection of useful functions to save your valuable time.
Because there are a ton of different ways to implement a method,
we often spend our valuable time to search and test those ways.
Some may work, but some may not work.
As such, we collected, tested and refined methods so as to prevent seeking code snippet.

-------
### Overview
Utilset includes:
  * Utilset
    ** checks if device is rooted
    ** counts the number of processors(more specifically the number of cores)
    ** determines if the phone has SMS capability
    ** etc...     
  
  * ActivityUtils
    ** checks if a package user specified is installed
    ** gets information of base activity package name and class name
  
  * NetworkUtils
    ** provides listeners which notify when network state changes, device has phone call, and connection is made
    ** checks if WiFi is enabled and if WiFi is connected
    ** checks if Mobile Network (3G/4G) is connected
    ** obtains IP Address either v4 or v6 form
    ** etc...
  
  * DiskUtils
    ** obtains external storage path for caching
    ** obtains external storage path for temp
    ** obtains MicroSD Card path
  
  * KeyboardUtils
    ** Shows, hides, and toggles Software keyboards

-------  
### Usage
  Download utilset.jar or add utilset dependency in pom.xml if using Maven.

-------
### Download
The latest version can be downloaded in zip(link needed here) and referenced as a library.

You can also configure pom.xml if using Maven :
```xml
<dependency>
	<groupId>com.navercorp.utilset</groupId>
	<artifactId>utilset</artifactId>
	<version>insert latest version</version>
</dependency>
```

-------
## License

    Copyright 2011, 2012 Navercorp

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.