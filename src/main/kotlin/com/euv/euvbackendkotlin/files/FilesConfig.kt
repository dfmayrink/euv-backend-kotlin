package com.euv.euvbackendkotlin.files

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "file")
class FilesConfig (var uploadDir: String = "")