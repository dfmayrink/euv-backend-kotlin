package com.euv.euvbackendkotlin.files

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpStatus
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.server.ResponseStatusException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.name

@Service
class FilesService @Autowired constructor(filesConfig: FilesConfig) {

    private val fileStorageLocation: Path

    init {
        fileStorageLocation = Paths.get(filesConfig.uploadDir).normalize()
        try {
            Files.createDirectories(fileStorageLocation)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create the directory where the uploaded files will be stored")
        }
    }

    fun storeFile(file: FilePart, directoryName: String): FileDto {
        val fileName = StringUtils.cleanPath(file.filename())
        val directory = StringUtils.cleanPath(directoryName)
        return try {
            if (fileName.contains(".."))
                throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Sorry! Filename contains invalid path sequence $fileName")
            val directoryPath = fileStorageLocation.resolve(directory)
            Files.createDirectories(directoryPath)

            val targetLocation = fileStorageLocation.resolve("$directory/$fileName")
            file.transferTo(targetLocation).subscribe()
            FileDto(fileName, "/$directory/$fileName")
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not store file $fileName. Please try again!", e)
        }
    }

    fun loadFileAsResource(fileName: String): Resource {
        return try {
            val filePath = fileStorageLocation.resolve(fileName).normalize()
            val resource: Resource = UrlResource(filePath.toUri())
            if (resource.exists()) resource
            else throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File not found $fileName!")
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File not found $fileName!", e)
        }
    }
}