public class NBody {
    public static void main(String args[]) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];

        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);
        int planetsSize = planets.length;

        StdDraw.setScale(-radius, radius);
		StdDraw.clear();
        StdDraw.picture(0, 0, "images/starfield.jpg");

        for (Planet p: planets) {
            p.draw();
        }

        /** animation part */
        StdDraw.enableDoubleBuffering();
        double curTime = 0;
        
        while (curTime < T) {
            double xForces[] = new double[planetsSize];
            double yForces[] = new double[planetsSize];

            for (int i = 0; i < planetsSize; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }

            /** redrawing universe and planets */
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (int i = 0; i < planetsSize; i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
                planets[i].draw();
            }
        	StdDraw.show();

            StdDraw.pause(10);

            curTime += dt;
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                        planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                        planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
        }
    }

    public static double readRadius(String filename) {
        In in = new In(filename);

        int n = in.readInt();
        double r = in.readDouble();

        return r;
    }

    public static Planet[] readPlanets(String filename) {
        In in = new In(filename);

        int n = in.readInt();
        double r = in.readDouble();

        Planet[] ans = new Planet[n];
        for (int i = 0; i < n; i++) {
            double xPos = in.readDouble();
            double yPos = in.readDouble();
            double xVel = in.readDouble();
            double yVel = in.readDouble();
            double m = in.readDouble();
            String imgFileName = in.readString();

            Planet p = new Planet(xPos, yPos, xVel, yVel, m, imgFileName);
            ans[i] = p;
        }

        return ans;
    }
}