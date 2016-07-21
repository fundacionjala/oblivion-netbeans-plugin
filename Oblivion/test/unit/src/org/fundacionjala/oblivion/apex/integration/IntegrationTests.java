/*
 * Copyright (c) Fundacion Jala. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */ 
package org.fundacionjala.oblivion.apex.integration;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * This class run all integration tests
 *
 * @author Amir Aranibar
 */
@RunWith(Categories.class)
@IncludeCategory(IntegrationTests.class)
@SuiteClasses({
    OneFileGrammarIntegrationTest.class
})
public final class IntegrationTests {
}
