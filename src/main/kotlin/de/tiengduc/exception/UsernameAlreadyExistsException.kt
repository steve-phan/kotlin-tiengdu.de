package de.tiengduc.exception

class UsernameAlreadyExistsException(message: String = "This userName is already exists") : RuntimeException(message) {
}