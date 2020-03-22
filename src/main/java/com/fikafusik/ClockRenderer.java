
package com.fikafusik;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import java.util.Calendar;

public class ClockRenderer implements GLEventListener {

    private Projection projection = Projection.ORTHO;

    private double rotationAngleXAxis = 30.0;
    private double rotationAngleYAxis = 20.0;
    private double rotationAngleZAxis = 0.0;

    private int triangulationDegree = 32;

    private boolean needToDrawAxis = true;

    public void setTriangulationDegree(int triangulationDegree) {
        this.triangulationDegree = triangulationDegree;
    }

    public void setRotationAngleXAxis(double rotationAngleXAxis) {
        this.rotationAngleXAxis = rotationAngleXAxis;
    }

    public void setRotationAngleYAxis(double rotationAngleYAxis) {
        this.rotationAngleYAxis = rotationAngleYAxis;
    }

    public void setRotationAngleZAxis(double rotationAngleZAxis) {
        this.rotationAngleZAxis = rotationAngleZAxis;
    }

    public void setNeedToDrawAxis(boolean needToDrawAxis) {
        this.needToDrawAxis = needToDrawAxis;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
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

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        if (projection == Projection.ORTHO) {
            gl.glOrtho(-2, 2, -2, 2, -20, 20);
            gl.glTranslated(0.0, 0.0, 0.0);
        } else {
            gl.glFrustum(-2, 2, -2, 2, 4.0, 25.0);
            gl.glTranslated(0.0, 0.0, -9.6);
            gl.glScaled(2.3, 2.3, 2.3);
        }

        gl.glMatrixMode(GL2.GL_MODELVIEW);

        gl.glRotated(rotationAngleXAxis, 1.0, 0.0, 0.0);
        gl.glRotated(rotationAngleYAxis, 0.0, 1.0, 0.0);
        gl.glRotated(rotationAngleZAxis, 0.0, 0.0, 1.0);

        gl.glLineWidth(2);

        if (needToDrawAxis) {
            drawAxis(gl);
        }

        drawBox(gl);

        drawArc1(gl, 0.42, 0.9, -50.0, 230.0, 2 * triangulationDegree);
        drawArc1(gl, 0.50, 0.9, -50.0, 230.0, 2 * triangulationDegree);
        drawArc1(gl, 0.42, 0.9, 230.0, 310.0, 1);
        drawArc1(gl, 0.50, 0.9, 230.0, 310.0, 1);
        drawArc2(gl, 0.46, 0.9, -50.0, 230.0, 2 * triangulationDegree, 0.08);

        drawTop(gl);
        drawBell(gl);
        drawRisks(gl);
        drawClockHands(gl);
        drawButton(gl);

        gl.glPopMatrix();
    }

    private void drawBell(GL2 gl) {
        drawArc1(gl, -0.5, 1.0, 75.0, 105.0, triangulationDegree / 2);
        drawArc1(gl, -0.1, 1.0, 75.0, 105.0, triangulationDegree / 2);
        drawArc1(gl, -0.5, 1.15, 75.0, 105.0, triangulationDegree / 2);
        drawArc1(gl, -0.1, 1.15, 75.0, 105.0, triangulationDegree / 2);
        drawArc2(gl, -0.30, 1.15, 75.0, 105.0, triangulationDegree / 4, 0.4);
        drawArc2(gl, -0.30, 1.0, 75.0, 75.0, 1, 0.4);
        drawArc2(gl, -0.30, 1.0, 105.0, 105.0, 1, 0.4);
        drawVertLine(gl, -0.5, 75.0);
        drawVertLine(gl, -0.1, 75.0);
        drawVertLine(gl, -0.5, 105.0);
        drawVertLine(gl, -0.1, 105.0);
    }

    private void drawVertLine(GL2 gl, double z, double angle) {
        gl.glPushMatrix();
        gl.glRotated(-angle, 0.0, 0.0, 1.0);

        gl.glBegin(GL.GL_LINES);
        gl.glColor3d(0.0, 0.0, 0.0);
        gl.glVertex3d(-1.0, 0.0, z);
        gl.glVertex3d(-1.15, 0.0, z);
        gl.glEnd();

        gl.glPopMatrix();
    }

    private void drawButton(GL2 gl) {
        gl.glPushMatrix();
        gl.glBegin(GL2.GL_QUAD_STRIP);

        gl.glColor3d(0.0, 0.0, 0.0);

        gl.glVertex3d(0.9, -0.9, 0.5);
        gl.glVertex3d(0.9, -0.9, 0.57);
        gl.glVertex3d(0.6, -0.9, 0.5);
        gl.glVertex3d(0.6, -0.9, 0.57);
        gl.glVertex3d(0.9, -0.6, 0.5);
        gl.glVertex3d(0.9, -0.6, 0.57);
        gl.glVertex3d(0.9, -0.9, 0.5);
        gl.glVertex3d(0.9, -0.9, 0.57);

        gl.glEnd();

        gl.glPopMatrix();
    }

    private void drawArc1(GL2 gl, double z, double R, double angle1, double angle2, int triangulationDegree) {
        double angle = (angle2 - angle1);
        double segmentAngle = angle / triangulationDegree;
        double segmentHalfLength = R * Math.sin(Math.toRadians(segmentAngle) / 2.0);
        double r = Math.sqrt(R * R - segmentHalfLength * segmentHalfLength);

        gl.glPushMatrix();
        gl.glColor3d(0.0, 0.0, 0.0);

        gl.glRotated(-angle1 + segmentAngle / 2.0, 0.0, 0.0, 1.0);
        for (int i = 0; i < triangulationDegree; ++i) {
            gl.glRotated(-segmentAngle, 0.0, 0.0, 1.0);
            gl.glBegin(GL.GL_LINES);
            gl.glVertex3d(-r, -segmentHalfLength, z);
            gl.glVertex3d(-r, segmentHalfLength, z);
            gl.glEnd();
        }

        gl.glPopMatrix();
    }

    private void drawArc2(GL2 gl, double z, double r, double angle1, double angle2, int triangulationDegree, double width) {

        double angle = (angle2 - angle1);
        double segmentAngle = angle / triangulationDegree;
        double segmentHalfLength = r * Math.sin(Math.toRadians(segmentAngle) / 2.0);

        gl.glPushMatrix();
        gl.glColor3d(0.0, 0.0, 0.0);

        gl.glRotated(-angle1 + segmentAngle / 2.0, 0.0, 0.0, 1.0);
        for (int i = 0; i < triangulationDegree + 1; ++i) {
            gl.glRotated(-segmentAngle, 0.0, 0.0, 1.0);
            gl.glBegin(GL.GL_LINES);
            gl.glVertex3d(-r, -segmentHalfLength, z - width / 2.0);
            gl.glVertex3d(-r, -segmentHalfLength, z + width / 2.0);
            gl.glEnd();
        }

        gl.glPopMatrix();
    }

    private void drawAxis(GL2 gl) {
        gl.glBegin(GL.GL_LINES);
        gl.glColor3d(0.0, 0.0, 2.0);
        gl.glVertex3d(0.0, 0.0, 0.0);
        gl.glVertex3d( 2.0, 0.0, 0.0);
        gl.glColor3d(2.0, 0.0, 0.0);
        gl.glVertex3d(0.0, 0.0, 0.0);
        gl.glVertex3d(0.0, 2.0, 0.0);
        gl.glColor3d(0.0, 0.8f, 0.0);
        gl.glVertex3d(0.0, 0.0, 0.0);
        gl.glVertex3d(0.0, 0.0, 2.0);
        gl.glEnd();

        gl.glLineStipple(2,(short)7239);
        gl.glEnable(GL2.GL_LINE_STIPPLE);

        gl.glBegin(GL2.GL_LINES);

        gl.glColor3d(0.0f, 0.0, 2.0);
        gl.glVertex3d( 0.0, 0.0, 0.0);
        gl.glVertex3d(-2.0, 0.0, 0.0);
        gl.glColor3d(2.0, 0.0, 0.0);
        gl.glVertex3d(0.0, 0.0, 0.0);
        gl.glVertex3d(0.0, -2.0, 0.0);
        gl.glColor3d(0.0, 0.8f, 0.0);
        gl.glVertex3d(0.0, 0.0, 0.0);
        gl.glVertex3d(0.0, 0.0, -2.0);

        gl.glEnd();

        gl.glDisable(GL2.GL_LINE_STIPPLE);
    }

    private void drawBox(GL2 gl) {

        gl.glPushMatrix();
        gl.glTranslated(0.0, -1.0, 0.0);
        gl.glBegin(GL2.GL_QUAD_STRIP);

        gl.glColor3d(0.0, 0.0, 0.0);

        gl.glVertex3d(-1.0, 1.0, -0.5);
        gl.glVertex3d(-1.0, 1.0, 0.5);
        gl.glVertex3d(-1.0, 0.0, -0.5);
        gl.glVertex3d(-1.0, 0.0, 0.5);
        gl.glVertex3d(1.0, 0.0, -0.5);
        gl.glVertex3d(1.0, 0.0, 0.5);
        gl.glVertex3d(1.0, 1.0, -0.5);
        gl.glVertex3d(1.0, 1.0, 0.5);

        gl.glEnd();

        gl.glPopMatrix();
    }

    private void drawTop(GL2 gl) {
        drawArc1(gl, -0.5, 1.0, 0.0, 180.0, triangulationDegree);
        drawArc1(gl, +0.5, 1.0, 0.0, 180.0, triangulationDegree);
        drawArc2(gl, +0.0, 1.0, 0.0, 180.0, triangulationDegree, 1.0);
    }

    private void drawRisks(GL2 gl) {
        gl.glPushMatrix();
        gl.glColor3d(0.0, 0.0, 0.0);
        gl.glTranslated(0.0, 0.0, 0.45);

        for (int i = 0; i < 12; ++i) {
            gl.glRotated(30, 0.0, 0.0, 1.0);
            gl.glBegin(GL.GL_LINES);
            gl.glVertex2d(0.0, 0.65);
            gl.glVertex2d(0.0, 0.57);
            gl.glEnd();
        }

        gl.glPopMatrix();
    }


    private void drawClockHands(GL2 gl) {
        Calendar calendar = Calendar.getInstance();
        double seconds = calendar.get(Calendar.SECOND);
        double minutes = calendar.get(Calendar.MINUTE) + seconds / 60.0;
        double hours = calendar.get(Calendar.HOUR) + minutes / 60.0;

        gl.glPushMatrix();

        drawHourHand(gl, hours);
        drawMinuteHand(gl, minutes);
        drawSecondHand(gl, seconds);

        gl.glPopMatrix();
    }

    private void drawHourHand(GL2 gl, double hours) {
        gl.glPushMatrix();

        gl.glRotated(-30.0 * hours, 0.0, 0.0, 1.0);
        gl.glTranslated(0.0, -0.1, 0.45);

        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex2d(0.0, 0.0);
        gl.glVertex2d(0.07, 0.07);
        gl.glVertex2d(0.07, 0.43);
        gl.glVertex2d(0.0, 0.5);
        gl.glVertex2d(-0.07, 0.43);
        gl.glVertex2d(-0.07, 0.07);
        gl.glEnd();

        gl.glPopMatrix();
    }

    private void drawMinuteHand(GL2 gl, double minutes) {
        gl.glPushMatrix();

        gl.glRotated(-6.0 * minutes, 0.0, 0.0, 1.0);
        gl.glTranslated(0.0, -0.1, 0.45);

        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex2d(0.0, 0.0);
        gl.glVertex2d(0.07, 0.07);
        gl.glVertex2d(0.07, 0.53);
        gl.glVertex2d(0.0, 0.6);
        gl.glVertex2d(-0.07, 0.53);
        gl.glVertex2d(-0.07, 0.07);
        gl.glEnd();

        gl.glPopMatrix();
    }

    private void drawSecondHand(GL2 gl, double seconds) {
        gl.glPushMatrix();

        gl.glRotated(-6.0 * seconds, 0.0, 0.0, 1.0);
        gl.glTranslated(0.0, -0.1, 0.45);

        gl.glBegin(GL.GL_LINES);
        gl.glVertex2d(0.0, 0.0);
        gl.glVertex2d(0.0, 0.71);
        gl.glEnd();

        gl.glPopMatrix();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {

    }
}
