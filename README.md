### descala

Scala decompilation preprocessor

Makes the following changes to the bytecode
* Remove Scala signature annotations
* Renames anonymous types
* Scala accessors to Java Bean accessors
* Scala collections to Java collections
* Removes case class bloat
* Merges Scala object class content to main class
* Scala un/boxing code to Java equivalents
* Lombok getters/setters for JPA entities

