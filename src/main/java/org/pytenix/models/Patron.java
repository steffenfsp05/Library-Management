package org.pytenix.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Patron implements Serializable {

    private int id;
    private String name;
    private Set<String> booksCheckedOut;
}
