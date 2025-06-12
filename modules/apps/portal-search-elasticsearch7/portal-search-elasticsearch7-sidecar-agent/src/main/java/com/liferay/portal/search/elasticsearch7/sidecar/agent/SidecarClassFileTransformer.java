/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.sidecar.agent;

import java.io.IOException;
import java.io.InputStream;

import java.lang.instrument.ClassFileTransformer;

import java.security.ProtectionDomain;

/**
 * @author Dante Wang
 */
public class SidecarClassFileTransformer implements ClassFileTransformer {

	@Override
	public byte[] transform(
		ClassLoader loader, String className, Class<?> classBeingRedefined,
		ProtectionDomain protectionDomain, byte[] classFileBuffer) {

		if (!className.equals(_CONSTANT_A) && !className.equals(_CONSTANT_B)) {
			return null;
		}

		Class<?> clazz = SidecarClassFileTransformer.class;

		try (InputStream inputStream = clazz.getResourceAsStream(
				"/patches/" + className + ".class.bytes")) {

			if (inputStream == null) {
				return null;
			}

			return inputStream.readAllBytes();
		}
		catch (IOException ioException) {
			ioException.printStackTrace(System.err);
		}

		return null;
	}

	private static final String _CONSTANT_A =
		"org/elasticsearch/bootstrap/Elasticsearch";

	private static final String _CONSTANT_B =
		"org/elasticsearch/common/settings/KeyStoreWrapper";

}