# WebX
<div align="center">
    <a href="https://plugins.jetbrains.com/plugin/16053-webx">
        <img src="./src/main/resources/META-INF/pluginIcon.svg" width="220" height="220" alt="logo"/>
    </a>
</div>

![Build](https://github.com/aborn/WebX/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/16053-webx.svg)](https://plugins.jetbrains.com/plugin/16053-webx)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/16053-webx.svg)](https://plugins.jetbrains.com/plugin/16053-webx)

## WebX (Web development extensions) 
<!-- Plugin description -->
WebX is a powerful Intellij IDEA plugin for develop web services.
<!-- Plugin description end -->

### Functions
* *ONE STEP* quick navigation to URL mapping declarations.
( use: `Ctrl + \` or `Ctrl + Alt + N` ) 
* Show RESTful service structure.

### Supported frameworks
* Spring (Spring MVC / Spring Boot)   
* JAX-RS 
 
## Template ToDo list
- [x] Create a new [IntelliJ Platform Plugin Template][template] project.
- [x] Verify the [pluginGroup](/gradle.properties), [plugin ID](/src/main/resources/META-INF/plugin.xml) and [sources package](/src/main/kotlin).
- [x] Review the [Legal Agreements](https://plugins.jetbrains.com/docs/marketplace/legal-agreements.html).
- [x] [Publish a plugin manually](https://plugins.jetbrains.com/docs/intellij/publishing-plugin.html?from=IJPluginTemplate) for the first time.
- [x] Set the Plugin ID in the above README badges.
- [x] Set the [Deployment Token](https://plugins.jetbrains.com/docs/marketplace/plugin-upload.html).
- [x] Click the <kbd>Watch</kbd> button on the top of the [IntelliJ Platform Plugin Template][template] to be notified about releases containing new features and fixes.

## Installation

- Using IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "WebX"</kbd> >
  <kbd>Install Plugin</kbd>
  
- Manually:

  Download the [latest release](https://github.com/aborn/WebX/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

- Note: IDEA Version >= 2019.3

## Demo

* Search
![](src/main/resources/images/webx_search.png)

* Tree
![](src/main/resources/images/webx_tree.png)

---

### WebX（强大的Web开发扩展插件）
WebX是一套强大的Web开发扩展插件，源于[RestfulToolkit](https://github.com/mrmanzhaow/RestfulToolkit) 并在其基础上对最新版本的IDEA做了支持。

### 功能
* 根据 URL 直接导航到对应的方法定义 ( `Ctrl \` or `Ctrl Alt N` )。
* 提供服务树的显示窗口。

### 支持的框架
* Spring (Spring MVC / Spring Boot)   
* JAX-RS 

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
