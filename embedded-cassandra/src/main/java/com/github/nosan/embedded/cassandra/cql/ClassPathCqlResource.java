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


package com.github.nosan.embedded.cassandra.cql;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Objects;


/**
 * {@link CqlResource} implementation for class path resources. Uses a
 * given {@link ClassLoader} or a given {@link Class} for loading resources.
 *
 * @author Dmytro Nosan
 */
public class ClassPathCqlResource extends AbstractCqlResource {

	private final String location;

	private final ClassLoader classLoader;

	private final Class<?> contextClass;

	public ClassPathCqlResource(String location) {
		this(location, null, null, null);
	}

	public ClassPathCqlResource(String location, Charset charset) {
		this(location, null, null, charset);
	}

	public ClassPathCqlResource(String location, ClassLoader classLoader) {
		this(location, classLoader, null);
	}

	public ClassPathCqlResource(String location, ClassLoader classLoader, Charset charset) {
		this(location, classLoader, null, charset);
	}

	public ClassPathCqlResource(String location, Class<?> contextClass) {
		this(location, null, contextClass, null);
	}


	public ClassPathCqlResource(String location, Class<?> contextClass, Charset charset) {
		this(location, null, contextClass, charset);
	}


	private ClassPathCqlResource(String location, ClassLoader classLoader, Class<?> contextClass, Charset charset) {
		super(charset);
		Objects.requireNonNull(location, "Location must not be null");
		this.location = location.startsWith("/") ? location.substring(1) : location;
		this.classLoader = (classLoader != null ? classLoader : ClassLoaderUtils.getClassLoader());
		this.contextClass = contextClass;
	}

	@Override
	public String getName() {
		return this.location;
	}

	@Override
	public InputStream getInputStream() throws FileNotFoundException {
		InputStream stream;
		if (this.contextClass != null) {
			stream = this.contextClass.getResourceAsStream(this.location);
			if (stream != null) {
				return stream;
			}
		}
		if (this.classLoader != null) {
			stream = this.classLoader.getResourceAsStream(this.location);
			if (stream != null) {
				return stream;
			}
		}
		stream = ClassLoader.getSystemResourceAsStream(this.location);
		if (stream == null) {
			throw new FileNotFoundException(String.format("'%s' doesn't exist", this.location));
		}
		return stream;
	}
}