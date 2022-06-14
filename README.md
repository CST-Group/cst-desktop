# CST-Desktop - A Version of CST (Cognitive Systems Toolkit) for Java Desktop
![](https://github.com/CST-Group/cst-utils/actions/workflows/java-ci.yml/badge.svg)
[![Maintainability](https://api.codeclimate.com/v1/badges/d235a7ebd7e2fb36554c/maintainability)](https://codeclimate.com/github/CST-Group/cst-utils/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/d235a7ebd7e2fb36554c/test_coverage)](https://codeclimate.com/github/CST-Group/cst-utils/test_coverage)
[![](https://jitpack.io/v/CST-Group/cst-desktop.svg)](https://jitpack.io/#CST-Group/cst-desktop)

# Remarks and relation to the CST package

The CST (Cognitive Systems Toolkit) was refactored in order to be 100% compatible to run in Android environment, at the same time it runs also on standard Java Desktop. This project contains all the old classes and functionalities which were exclusive of Java Desktop. It still maintains a dependency on the [cst project](https://github.com/CST-Group/cst) and uses cst for the standard classes, just appending other classes, which are exclusive for Desktop use. Users of old versions of CST (before 1.0.0), which are using it on the desktop, are encouraged to use cst-desktop instead of cst, as it already includes a dependency on cst. 

# Welcome to the CST Toolkit pages.

The [CST Toolkit](http://cst.fee.unicamp.br) is a Java-based toolkit to allow the construction of Cognitive Architectures. It has been developed at the [University of Campinas](http://www.dca.fee.unicamp.br) by a group of researchers in the field of Cognitive Architectures led by Prof. [Ricardo Gudwin](http://faculty.dca.fee.unicamp.br/gudwin). 

Note: This library is still under development, and some concepts or features might not be available yet. [Feedback/bug report](https://github.com/CST-Group/cst/issues) and [Pull Requests](https://github.com/CST-Group/cst/pulls) are most welcome!

## Installation

### Gradle

- Step 1. Add the JitPack repository to your build file. Add it in your root build.gradle at the end of repositories:

```
	repositories {
			...
			maven { url 'https://jitpack.io' }
	}
```

- Step 2. Add the dependency

```
	dependencies {
            ...
            implementation 'com.github.CST-Group:cst-desktop:1.0.4'
	}
```

Sometimes, the version number (tag) in this README gets out of date, as maintainers might forget to change it when releasing. Always check the release badge [![](https://jitpack.io/v/CST-Group/cst-desktop.svg)](https://jitpack.io/#CST-Group/cst-desktop) to see the actual current version number that should be provided in the dependencies in Step 2 above.

### Maven

- Step 1. Add the JitPack repository to your build file.

```
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```

- Step 2. Add the dependency

```
	<dependency>
	    <groupId>com.github.CST-Group</groupId>
	    <artifactId>cst-desktop</artifactId>
	    <version>1.0.4</version>
	</dependency>
```

Sometimes, the version number (tag) in this README gets out of date, as maintainers might forget to change it when releasing. Always check the release badge [![](https://jitpack.io/v/CST-Group/cst-desktop.svg)](https://jitpack.io/#CST-Group/cst-desktop) to see the actual current version number that should be provided in the dependencies in Step 2 above.

### Manual

Download the latest [release](https://github.com/CST-Group/cst/releases) and set it as a dependency in your project.

## CST packages

There are four CST repos:

- [CST](https://github.com/CST-Group/cst)
- [CST-Bindings](https://github.com/CST-Group/cst-bindings)
- [CST-Desktop](https://github.com/CST-Group/cst-desktop) (this repo)
- [CST-Legacy](https://github.com/CST-Group/cst-legacy)

The original [CST](https://github.com/CST-Group/cst) repo contains all core functionalities and some additional packages. It has no *Swing* or other graphical entities, being suitable to both desktop and mobile environments. [CST-Bindings](https://github.com/CST-Group/cst-bindings) contains the needed classes to communicate with [ROS](https://www.ros.org/) and [SOAR Cognitive Architecture](https://soar.eecs.umich.edu/). [CST-Desktop](https://github.com/CST-Group/cst-desktop) contains some utility classes as well. It is also the complete toolkit, as it has a chain dependency with the other three repos. Finally, all classes that were once important but became obsolete as CST evolved are in [CST-Legacy](https://github.com/CST-Group/cst-legacy). 



## Origins of the CST Toolkit
The CST toolkit has been used successfully in a number of MS and PhD thesis in our group, either for building a cognitive architecture to be used in a particular experiment or also to foster further improvements in the own CST code due to the applications requirements.

## Requirements

CST requires at minimum Java 8.

### Authors and Contributors
The main contributors of this project are: 
* [Ricardo Ribeiro Gudwin](https://github.com/rgudwin) 
* [Klaus Raizer](https://github.com/KRaizer)
* [André Luis Ogando Paraense](https://github.com/andre-paraense)
* [Suelen Mapa de Paula](https://github.com/suelenmapa)
* Vera Aparecida de Figueiredo
* [Elisa Calhau de Castro](https://github.com/ecalhau)
* [Eduardo de Moraes Fróes](https://github.com/eduardofroes)
* [Wandemberg Santana Pharaoh Gibaut](https://github.com/wandergibaut)

License
--------

    Copyright 2016 CST-Group

    Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.gnu.org/licenses/lgpl-3.0.html

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    
    
