/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nosan.embedded.cassandra.process;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Tests for {@link FileCustomizers}.
 *
 * @author Dmytro Nosan
 */
public class FileCustomizersTests {

	private final FileCustomizers customizers = new FileCustomizers();

	@Test
	@SuppressWarnings("unchecked")
	public void addCustomizer() {
		final boolean[] invoked = {false};
		FileCustomizer fileCustomizer = (file, context) -> invoked[0] = true;
		this.customizers.addCustomizer(fileCustomizer);
		this.customizers.customize(new TestContext());
		Assertions.assertThat(invoked[0]).describedAs("Should be invoked").isTrue();

	}

}
