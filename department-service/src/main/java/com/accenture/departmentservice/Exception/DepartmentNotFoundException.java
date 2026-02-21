package com.accenture.departmentservice.Exception;


// Custom exception for handling cases when a department is not found
// Extends RuntimeException to create an unchecked exception

public class DepartmentNotFoundException extends RuntimeException {

    public DepartmentNotFoundException(String message) {
        super(message);
    }
}


// Why Checked Exceptions Fail in Enterprise Applications (or why we use Unchecked Exceptions for custom exceptions)
//1. Method Signature Pollution: Checked exceptions must be declared in method signatures, leading to cluttered and less readable code.
//2. User less catch and throw: Developers often catch checked exceptions only to rethrow them as unchecked exceptions, defeating their purpose.
//3. Transaction rollback issue: spring rolls back only for checked exceptions by default.


//Where Checked Exceptions Are Still Valid
//1. Infrastructure or boundary layers : FileInputStream fis = new FileInputStream("file.txt");

