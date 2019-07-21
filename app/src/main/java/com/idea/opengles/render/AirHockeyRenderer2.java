package com.idea.opengles.render;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.idea.opengles.R;
import com.idea.opengles.help.ShaderHelper;
import com.idea.opengles.help.TextResourceReader;
import com.idea.opengles.util.LoggerConfig;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

public class AirHockeyRenderer2 implements GLSurfaceView.Renderer {


    private static final int POSITION_COMPONET_COUNT = 2;
    private static final int BYTE_PER_FLOAT = 4;
    private final FloatBuffer mVertexData;
    private final Context mContext;
    private int mProram;
    private static final String U_COLOR = "u_Color";
    private int uColorLocation;
    private static final String A_POSITION = "a_Position";
    private int aPositionLocation;

    public AirHockeyRenderer2(Context context) {
        mVertexData = initTriangles();
        mContext = context;

    }

    private void initShader(Context context) {
        String vertexShaderResource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_vertex_shader);
        String fragShaderResource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader);
        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderResource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragShaderResource);
        mProram = ShaderHelper.linkProgram(vertexShader, fragmentShader);
        if (LoggerConfig.ON) {
            ShaderHelper.validateProgram(mProram);
        }
        glUseProgram(mProram);
        uColorLocation = glGetUniformLocation(mProram, U_COLOR);
        aPositionLocation = glGetAttribLocation(mProram,A_POSITION);

        mVertexData.position(0);
        glVertexAttribPointer(aPositionLocation,POSITION_COMPONET_COUNT,GL_FLOAT,false,0,mVertexData);
        glEnableVertexAttribArray(aPositionLocation);
    }

    private FloatBuffer initTriangles() {
        FloatBuffer mVertexData;
        float tableVerticesWithTriangles[] = {

                //triangle fan
                0f, 0f,1f,1f,1f,
                -0.5f, -0.5f,0.7f,0.7f,0.7f,
                0.5f, -0.5f,0.7f,0.7f,0.7f,
                0.5f, 0.5f,0.7f,0.7f,0.7f,
                -0.5f, -0.5f,0.7f,0.7f,0.7f,

                //center line
                -0.5f, 0f,1f,0f,0f,
                0.5f, 0f,1f,0f,0f,

                //Mallets
                0f, -0.25f,0f,0f,1f,
                0f, 0.25f,1f,0f,0f



        };
        mVertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.length * BYTE_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        mVertexData.put(tableVerticesWithTriangles);
        return mVertexData;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        initShader(mContext);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);
        glUniform4f(uColorLocation,1f,1f,1f,1f);
        glDrawArrays(GL_TRIANGLE_FAN,0,6);

        glUniform4f(uColorLocation,1f,0f,0f,1f);
        glDrawArrays(GL_LINES,6,2);

        //Draw the first mallet blue
        glUniform4f(uColorLocation,0f,0f,1f,1f);
        glDrawArrays(GL_POINTS,8,1);

        //Draw the second mallet red
        glUniform4f(uColorLocation,1f,0f,0f,1f);
        glDrawArrays(GL_POINTS,9,1);


    }
}
