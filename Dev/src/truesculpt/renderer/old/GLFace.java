/*
 * Copyright (C) 2008 The Android Open Source Project
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

package truesculpt.renderer.old;

import java.nio.IntBuffer;
import java.util.ArrayList;

import android.util.Log;

public class GLFace {

	private GLColor mColor;

	private ArrayList<GLVertex> mVertexList = new ArrayList<GLVertex>();

	public GLFace() {

	}

	// for triangles
	public GLFace(GLVertex v1, GLVertex v2, GLVertex v3) {
		addVertex(v1);
		addVertex(v2);
		addVertex(v3);
	}

	// for quadrilaterals
	public GLFace(GLVertex v1, GLVertex v2, GLVertex v3, GLVertex v4) {
		addVertex(v1);
		addVertex(v2);
		addVertex(v3);
		addVertex(v4);
	}

	public void addVertex(GLVertex v) {
		mVertexList.add(v);
	}

	public int getIndexCount() {
		return (mVertexList.size() - 2) * 3;
	}

	public void putIndices(IntBuffer buffer) {
		int last = mVertexList.size() - 1;

		GLVertex v0 = mVertexList.get(0);
		GLVertex vn = mVertexList.get(last);

		// push triangles into the buffer
		for (int i = 1; i < last; i++) {
			GLVertex v1 = mVertexList.get(i);
			buffer.put(v0.index);
			buffer.put(v1.index);
			buffer.put(vn.index);
			v0 = v1;
		}
	}

	// must be called after all vertices are added
	public void setColor(GLColor c) {

		int last = mVertexList.size() - 1;
		if (last < 2) {
			Log.e("GLFace", "not enough vertices in setColor()");
		} else {
			GLVertex vertex = mVertexList.get(last);

			// only need to do this if the color has never been set
			if (mColor == null) {
				while (vertex.color != null) {
					mVertexList.add(0, vertex);
					mVertexList.remove(last + 1);
					vertex = mVertexList.get(last);
				}
			}

			vertex.color = c;
		}

		mColor = c;
	}
}