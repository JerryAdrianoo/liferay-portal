/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.server.admin.web.internal.scripting.util;

import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Jerry Adriano
 */
public class ServerScriptingUtilTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		new LiferayUnitTestRule();

	@Test
	public void testExecuteGroovyScriptWithException() {
		try {
			ServerScriptingUtil.execute(
				Collections.emptyMap(), "groovy",
				"throw new UnsupportedOperationException();");

			Assert.fail();
		}
		catch (ScriptingException scriptingException) {
			String message = scriptingException.getMessage();

			Assert.assertTrue(message.contains("Unable to execute script:"));
			Assert.assertTrue(
				message.contains(
					"Line 1: throw new UnsupportedOperationException();"));
		}
	}

}