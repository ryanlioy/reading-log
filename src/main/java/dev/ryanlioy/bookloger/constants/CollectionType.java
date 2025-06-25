package dev.ryanlioy.bookloger.constants;

public enum CollectionType {
    CURRENTLY_READING,
    FINISHED,
    FAVORITES,
    READ_LIST;

    public static CollectionType convertFromCamelCase(String type) {
        return switch (type) {
            case "favorites" -> CollectionType.FAVORITES;
            case "readList" -> CollectionType.READ_LIST;
            case "currentlyReading" -> CollectionType.CURRENTLY_READING;
            case "finished" -> CollectionType.FINISHED;
            default -> throw new IllegalArgumentException(String.format("Failed converting camel case collection type string to CollectionType: type cannot be %s",  type));
        };
    }
}
