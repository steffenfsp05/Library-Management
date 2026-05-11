package org.pytenix.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;


/**
 * Represents a registered library user (patron).
 * Maintains a set of book IDs representing the books currently checked out.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Patron implements Serializable {

    private int id;
    private String name;
    private Set<Integer> booksCheckedOut;
}
