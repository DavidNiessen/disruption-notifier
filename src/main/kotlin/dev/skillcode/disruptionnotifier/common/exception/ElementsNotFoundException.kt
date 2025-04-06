package dev.skillcode.disruptionnotifier.common.exception

class ElementsNotFoundException(vararg elementsNotFound: String) :
    Exception("One ore more elements could not be found: ${elementsNotFound.joinToString(",")}") {
}
