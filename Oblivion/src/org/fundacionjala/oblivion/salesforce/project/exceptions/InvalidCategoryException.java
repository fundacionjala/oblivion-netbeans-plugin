/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.salesforce.project.exceptions;

import org.fundacionjala.oblivion.salesforce.project.exceptions.Bundle;
import org.openide.util.NbBundle;

/**
 * Exception to be thrown when there is an invalid category on the project properties window.
 * @author Marcelo Garnica
 */
@NbBundle.Messages("InvalidCategory=The selected category is invalid.")
public class InvalidCategoryException extends RuntimeException {

    public InvalidCategoryException() {
        super(Bundle.InvalidCategory());
    }
}
