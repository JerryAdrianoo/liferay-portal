/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.server.admin.web.internal.scripting.util;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.lang.reflect.Method;

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
	public void testGetErrorMessageIncludesScriptLinesAndException() {
		RuntimeException runtimeException = new RuntimeException("Test error");

		String script = "first line\nsecond line";

		String message = _invokeGetErrorMessage(runtimeException, script);

		Assert.assertNotNull(message);

		Assert.assertTrue(message.contains("Unable to execute script:"));

		Assert.assertTrue(message.contains(runtimeException.toString()));
	}

	private String _invokeGetErrorMessage(Exception exception1, String script) {
		try {
			Method method = ServerScriptingUtil.class.getDeclaredMethod(
				"_getErrorMessage", Exception.class, String.class);

			method.setAccessible(true);

			return (String)method.invoke(null, exception1, script);
		}
		catch (Exception exception2) {
			throw new RuntimeException(exception2);
		}
	}

}