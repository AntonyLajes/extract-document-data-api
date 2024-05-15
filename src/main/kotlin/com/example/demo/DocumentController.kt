package com.example.demo

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream

@RestController("/api/document")
class DocumentController {

    @PostMapping("/extract-content")
    fun extractText(@RequestParam("document") file: MultipartFile): ResponseEntity<String> {
        return try {
            val document = PDDocument.load(ByteArrayInputStream(file.bytes))
            val stripper = PDFTextStripper()
            val text = stripper.getText(document)
            document.close()
            ResponseEntity.ok(text)
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar o documento PDF.")
        }
    }

}