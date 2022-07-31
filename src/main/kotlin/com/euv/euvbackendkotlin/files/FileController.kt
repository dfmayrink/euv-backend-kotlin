package com.euv.euvbackendkotlin.files

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ServerWebExchange


@RestController
@RequestMapping("/files")
class FileController {

    @Autowired
    lateinit var filesService: FilesService

    @PostMapping
    fun uploadFile(@RequestPart file: FilePart ) : Any {
        return filesService.storeFile(file)
    }

    @GetMapping("{fileName}", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun read(@PathVariable fileName: String?, exchange: ServerWebExchange): Resource {
        return filesService.loadFileAsResource(fileName!!)
    }
}