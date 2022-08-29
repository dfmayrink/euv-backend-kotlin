package com.euv.euvbackendkotlin.files

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.FilePart
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ServerWebExchange


@RestController
@RequestMapping("/files")
class FileController {

    @Autowired
    lateinit var filesService: FilesService

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    fun uploadFile(@RequestPart file: FilePart, @RequestPart directoryName: String) : FileDto {
        return filesService.storeFile(file, directoryName)
    }

    @GetMapping("{directory}/{fileName}", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun getFile(@PathVariable directory: String, @PathVariable fileName: String): Resource {
        return filesService.loadFileAsResource("$directory/$fileName")
    }
}