package main.java;

public class ErrorHandler {
    // This method logs a generic error message and the specific exception message
    public static void handleException(String errorMessage, Exception e) {
        System.out.println(errorMessage);
        System.out.println("Exception: " + e.getMessage());
    }

    // This method handles the specific case of a file opening error
    public static void handleFileOpeningError(String filePath, Exception e) {
        String errorMessage = "Error opening file at: " + filePath;
        handleException(errorMessage, e);
    }

    // This method handles the specific case of a file creation error
    public static void handleFileCreationError(String filePath, Exception e) {
        String errorMessage = "Error creating file at: " + filePath;
        handleException(errorMessage, e);
    }

    // This method handles the specific case of a directory creation error
    public static void handleDirectoryCreationError(String directoryPath, Exception e) {
        String errorMessage = "Error creating directory at: " + directoryPath;
        handleException(errorMessage, e);
    }
}
