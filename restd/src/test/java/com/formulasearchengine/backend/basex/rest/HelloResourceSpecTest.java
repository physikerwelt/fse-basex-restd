package com.formulasearchengine.backend.basex.rest;

import org.junit.runner.RunWith;
import restx.tests.FindSpecsIn;
import restx.tests.RestxSpecTestsRunner;

@RunWith(RestxSpecTestsRunner.class)
@FindSpecsIn("specs/hello")
public class HelloResourceSpecTest {

	/**
	 * Useless, thanks to both @RunWith(RestxSpecTestsRunner.class) & @FindSpecsIn()
	 *
	 * @Rule
	 * public RestxSpecRule rule = new RestxSpecRule();
	 *
	 * @Test
	 * public void test_spec() throws Exception {
	 *     rule.runTest(specTestPath);
	 * }
	 */
}
