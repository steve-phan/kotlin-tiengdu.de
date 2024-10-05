package de.tiengduc.exception

import com.fasterxml.jackson.databind.JsonMappingException
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest


@ControllerAdvice
class GlobalExceptionHandler {


    // Handle the custom UsernameAlreadyExistsException
    @ExceptionHandler(UsernameAlreadyExistsException::class)
    fun handleUsernameAlreadyExistsException(ex: UsernameAlreadyExistsException): ResponseEntity<Map<String, String>> {
        val errorResponse = mapOf(
            "error" to ex.message.toString(),
            "status" to HttpStatus.BAD_REQUEST.name
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(
        ex: HttpMessageNotReadableException,
        request: WebRequest
    ): ResponseEntity<Map<String, Map<String, String>>> {
        val cause = ex.cause
        var fieldName = "Unknown field"
        var errorMessage = "Bad request: One or more fields are not readable or have invalid types."

        if (cause is JsonMappingException) {
            val path = cause.path
            if (path.isNotEmpty()) {
                fieldName = path[0].fieldName ?: fieldName
            }
            errorMessage = "Field ${fieldName} has invalid data type. Please provide the correct type."

        }
        return ResponseEntity(
            mapOf(
                "error" to mapOf(fieldName to errorMessage),
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    // Handle unsupported request method
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(
        ex: HttpRequestMethodNotSupportedException,
        request: WebRequest
    ): ResponseEntity<String> {
        return ResponseEntity("Method not supported: ${ex.method}", HttpStatus.METHOD_NOT_ALLOWED)
    }


    // DTO validation Errors
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException):
            ResponseEntity<Map<String, Map<String, String>>> {
        val errors: MutableMap<String, String> = HashMap()

        ex.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.defaultMessage ?: "Invalid value"
            errors[fieldName] = errorMessage
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("errors" to errors))
    }

    // Anywhere, outside controllers
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(
        ex: ConstraintViolationException,
        request: WebRequest
    ): ResponseEntity<Map<String, String>> {
        val errors: MutableMap<String, String> = HashMap()
        ex.constraintViolations.forEach { violation ->
            val fieldName = violation.propertyPath.toString()
            val errorMessage = violation.message
            errors[fieldName] = errorMessage
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors)
    }

    // Handle any other exceptions that haven't been caught
    @ExceptionHandler(Exception::class)
    fun handleGlobalException(ex: Exception, request: WebRequest): ResponseEntity<String> {
        println(ex)
        return ResponseEntity("An unexpected error occurred: ${ex.message}", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}