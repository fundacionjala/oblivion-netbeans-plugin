/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 

package org.fundacionjala.oblivion.gradle;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Class that will contain the output of a gradle execution.
 * 
 * @author Marcelo Garnica
 */
public class GradleOutputStream extends OutputStream {
    
    private final StringBuilder gradleOutput;
    
    public GradleOutputStream() {
        this.gradleOutput = new StringBuilder();
    }
    
    public StringBuilder getOutput() {
        return gradleOutput;
    }

    @Override
    public void write(int b) throws IOException {
        gradleOutput.append(new String(new int[]{ b }, 0, 1));
    }
    
    @Override
    public String toString() {
        return gradleOutput.toString();
    }
}
