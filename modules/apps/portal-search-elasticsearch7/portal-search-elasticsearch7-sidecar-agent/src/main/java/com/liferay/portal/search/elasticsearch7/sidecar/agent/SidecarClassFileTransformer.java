/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.sidecar.agent;

import java.io.IOException;

import java.lang.instrument.ClassFileTransformer;

import java.nio.file.Files;
import java.nio.file.Path;

import java.security.ProtectionDomain;

/**
 * @author Dante Wang
 */
public class SidecarClassFileTransformer implements ClassFileTransformer {

	public SidecarClassFileTransformer(String pathRootPath) {
		_patchRootPath = Path.of(pathRootPath);
	}

	@Override
	public byte[] transform(
		ClassLoader loader, String className, Class<?> classBeingRedefined,
		ProtectionDomain protectionDomain, byte[] classFileBuffer) {

		Path patchFilePath = _patchRootPath.resolve(className + ".class");

		if (Files.exists(patchFilePath)) {
			try {
				return Files.readAllBytes(patchFilePath);
			}
			catch (IOException ioException) {
				ioException.printStackTrace(System.err);
			}
		}

		return null;
	}

	private final Path _patchRootPath;

}