
package com.fikafusik;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import java.util.Calendar;

public class ClockRenderer implements GLEventListener {

    public ClockRenderer() {

    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glPushMatrix();
        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_LINE);
        gl.glOrtho(-2, 3, -3, 2, -20, 20);

        gl.glTranslated(0.5, -0.5, 0.0);
        gl.glRotated(20.0, 1.0, 0.0, 0.0);
        gl.glRotated(-30.0, 0.0, 1.0, 0.0);
        gl.glLineWidth(2);

        drawAxis(gl);
        // drawBox(gl);
        drawRisks(gl);
        drawClockHands(gl);

        gl.glPopMatrix();
    }

    private void drawAxis(GL2 gl) {
        double x = 2.0;
        double y = 2.0;
        double z = 3.0;

        gl.glBegin(GL.GL_LINES);
        gl.glColor3d(0.0, 0.0, 1.0);
        gl.glVertex3d(0.0, 0.0, 0.0);
        gl.glVertex3d( x, 0.0, 0.0);
        gl.glColor3d(1.0, 0.0, 0.0);
        gl.glVertex3d(0.0, 0.0, 0.0);
        gl.glVertex3d(0.0, y, 0.0);
        gl.glColor3d(0.0, 0.8f, 0.0);
        gl.glVertex3d(0.0, 0.0, 0.0);
        gl.glVertex3d(0.0, 0.0, z);
        gl.glEnd();

        gl.glLineStipple(2,(short)7239);
        gl.glEnable(GL2.GL_LINE_STIPPLE);

        gl.glBegin(GL2.GL_LINES);
        gl.glColor3d(0.0f, 0.0, 1.0);
        gl.glVertex3d( 0.0, 0.0, 0.0);
        gl.glVertex3d(-x, 0.0, 0.0);
        gl.glColor3d(1.0, 0.0, 0.0);
        gl.glVertex3d(0.0, 0.0, 0.0);
        gl.glVertex3d(0.0, -y, 0.0);
        gl.glColor3d(0.0, 0.8f, 0.0);
        gl.glVertex3d(0.0, 0.0, 0.0);
        gl.glVertex3d(0.0, 0.0, -z) ;
        gl.glEnd();

        gl.glDisable(GL2.GL_LINE_STIPPLE);
    }

    private void drawBox(GL2 gl) {

        gl.glBegin(GL2.GL_QUAD_STRIP);

        gl.glColor3d(0.0, 0.0, 0.0);
        gl.glVertex3d(-0.5, 0.5, 1.5);
        gl.glVertex3d(-0.5, -0.5, 1.5);
        gl.glVertex3d(0.5, 0.5, 1.5);
        gl.glVertex3d(0.5, -0.5, 1.5);
        gl.glVertex3d(0.5,0.5, -2.5);
        gl.glVertex3d(0.5, -0.5, -2.5);
        gl.glVertex3d(-0.5, 0.5, -2.5);
        gl.glVertex3d(-0.5, -0.5, -2.5);
        gl.glVertex3d(-0.5, 0.5, 1.5);
        gl.glVertex3d(-0.5, -0.5, 1.5);

        gl.glEnd();
    }

    private void drawRisks(GL2 gl) {
        gl.glPushMatrix();
        gl.glColor3d(0.0, 0.0, 0.0);

        for (int i = 0; i < 12; ++i) {
            gl.glRotated(30, 0.0, 0.0, 1.0);
            gl.glBegin(GL.GL_LINES);
            gl.glVertex3d(0.0, 0.8, 0.0);
            gl.glVertex3d(0.0, 1.0, 0.0);
            gl.glEnd();
        }

        gl.glPopMatrix();
    }

    private void drawClockHands(GL2 gl) {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        System.out.println(hours);
        System.out.println(minutes);
        System.out.println(seconds);

        gl.glPushMatrix();

        gl.glRotated(-6.0 * seconds, 0.0, 0.0, 1.0);

        gl.glBegin(GL.GL_LINES);
        gl.glVertex2d(0.0, 0.0);
        gl.glVertex2d(0.0, 1.0);
        gl.glEnd();

        gl.glPopMatrix();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {

    }
}
