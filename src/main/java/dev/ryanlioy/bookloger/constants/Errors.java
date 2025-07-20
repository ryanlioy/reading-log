package dev.ryanlioy.bookloger.constants;

public class Errors {
    public static final String ROLE_DOES_NOT_EXIST = "Role does not exist";
    public static final String USER_DOES_NOT_EXIST = "User does not exist";
    public static final String SAVE_COLLECTION_MULTIPLE_USERS = "Cannot save collections to different users in the same request";
    public static final String ADD_BOOKS_COLLECTION_NO_BOOK_IDS = "Cannot add books to collection as no book IDs were provided";
    public static final String COLLECTION_DOES_NOT_EXIST = "Collection does not exist";
    public static final String MISSING_BOOK_IDS = "Cannot save books to collection as no books were provided";
    public static final String BOOKS_DO_NOT_EXIST = "Books with IDs=%s do not exist";
    public static final String DELETE_DEFAULT_COLLECTION = "Cannot delete a default collection";
    public static final String AUTHOR_ALREADY_EXISTS = "Author with provided name already exists";
}
