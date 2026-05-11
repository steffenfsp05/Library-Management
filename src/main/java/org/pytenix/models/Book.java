package org.pytenix.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


/**
 * Represents a book entity within the library system.
 * Keeps track of its current status and the ID of the patron holding it (if any).
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Serializable {

    private int id;
    private String title, author;
    private int publicationYear;
    private BookStatus bookStatus;
    private Integer inheritedUser;

}
